package com.wulab.htpc.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Base64;

public class HtmlProcessor {
    public Document processHtml(String htmlPath, Map<String, String> dataMap, String cssPath) throws IOException {
        File htmlFile = new File(htmlPath);
        if (!htmlFile.exists()) {
            throw new IOException("錯誤: 找不到文件 " + htmlPath);
        }

        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlPath)), StandardCharsets.UTF_8);
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            htmlContent = htmlContent.replace(placeholder, entry.getValue());
        }

        Document doc = Jsoup.parse(htmlContent, htmlFile.getParentFile().toURI().toString());
        applyCssStyles(doc, cssPath);
        processImages(doc, dataMap);
        processStaticImages(doc);
        
        return doc;
    }

    private void processStaticImages(Document doc) throws IOException {
        Elements imgElements = doc.select("img[src^='../static/img/']");
        for (Element img : imgElements) {
            String imgSrc = img.attr("src");
            String imgPath = "src/main/resources/" + imgSrc.substring(3); // 移除 '../' 前綴
            
            File imgFile = new File(imgPath);
            if (!imgFile.exists()) {
                System.err.println("警告: 找不到圖片文件: " + imgPath);
                continue;
            }
            
            byte[] imageBytes = Files.readAllBytes(imgFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String mimeType = getMimeType(imgPath);
            img.attr("src", "data:" + mimeType + ";base64," + base64Image);
        }
    }

    private String getMimeType(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            default:
                return "application/octet-stream";
        }
    }

    public static void applyCssStyles(Document doc, String cssPath) {
        try {
            String cssContent = new String(Files.readAllBytes(Paths.get(cssPath)), StandardCharsets.UTF_8);
            Element head = doc.head();
            if (head == null) {
                head = doc.createElement("head");
                doc.prependChild(head);
            }
            head.appendElement("style").attr("type", "text/css").text(cssContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processImages(Document doc, Map<String, String> dataMap) {
        Elements imgElements = doc.select("img");
        for (Element img : imgElements) {
            String imgSrc = img.attr("src");
            if (imgSrc.contains("data:image/png;base64,")) {
                if (imgSrc.equals(dataMap.get("taichiImage")) || 
                    imgSrc.equals(dataMap.get("fivePowerImage"))) {
                    img.attr("height", "300");
                }
            }
        }
    }
}