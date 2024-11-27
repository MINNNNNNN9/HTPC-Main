package com.wulab.htpc.html;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OnlineHtmlConverter {

    // 提取原 main 方法中的邏輯為靜態方法，供 HTPC 呼叫
    public static void convertOnlineHtmlToPdf(String htmlPath, String pdfFilePath, String fontPath, String cssPath, String downloadDir) throws Exception {
        try {
            String htmlContent = loadHtmlToLocal(htmlPath, downloadDir);
            htmlContent = downloadAndEmbedImagesAsBase64(htmlContent, htmlPath, downloadDir);

            // 去除 Bootstrap 引用，避免 OpenHTMLToPDF 解析錯誤
            htmlContent = htmlContent.replaceAll("<link[^>]*bootstrap[^>]*>", "");

            // 加載自定義的 CSS 文件內容
            File cssFile = new File(cssPath);
            if (cssFile.exists()) {
                String cssContent = new String(Files.readAllBytes(cssFile.toPath()), StandardCharsets.UTF_8);
                // 將 CSS 插入到 HTML 的 <head> 中
                htmlContent = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");
            } else {
                System.err.println("警告: 找不到 CSS 文件 " + cssPath + "，將跳過 CSS 加載。");
            }

            Document doc = Jsoup.parse(htmlContent, htmlPath);
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml); // 確保輸出為 XHTML

            // 使用 JTidy 清理 HTML
            Tidy tidy = new Tidy();
            tidy.setXHTML(true);
            tidy.setInputEncoding("UTF-8");
            tidy.setOutputEncoding("UTF-8");
            tidy.setQuiet(true);
            tidy.setShowWarnings(false);

            // 將 HTML 進行 JTidy 清理
            ByteArrayInputStream htmlInputStream = new ByteArrayInputStream(doc.outerHtml().getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream tidyOutputStream = new ByteArrayOutputStream();
            tidy.parse(htmlInputStream, tidyOutputStream);
            String xhtml = tidyOutputStream.toString("UTF-8");

            convertHtmlToPdf(xhtml, htmlPath, pdfFilePath, fontPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("HTML 轉換失敗: " + e.getMessage());
        }
    }


    public static void convertHtmlToPdf(String xhtml, String baseUri, String pdfFilePath, String fontPath) throws Exception {
        try (OutputStream os = new FileOutputStream(pdfFilePath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(xhtml, baseUri);
            builder.toStream(os);
            builder.useFont(new File(fontPath), "Microsoft JhengHei");
            builder.run();
            System.out.println("HTML 轉 PDF 生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("HTML 轉換失敗: " + e.getMessage());
        }
    }

    private static String loadHtmlToLocal(String urlString, String saveDir) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder htmlContentBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            htmlContentBuilder.append(inputLine).append("\n");
        }
        in.close();
        String htmlContent = htmlContentBuilder.toString();

        String localHtmlPath = Paths.get(saveDir, "downloaded_online.html").toString();
        Files.write(Paths.get(localHtmlPath), htmlContent.getBytes(StandardCharsets.UTF_8));
        System.out.println("HTML 已下載並保存為本地文件: " + localHtmlPath);
        return htmlContent;
    }

    private static String downloadAndEmbedImagesAsBase64(String htmlContent, String baseUri, String saveDir) {
        try {
            Document doc = Jsoup.parse(htmlContent, baseUri);
            Elements imgElements = doc.select("img");

            for (Element img : imgElements) {
                String imgUrl = img.attr("src");
                if (imgUrl == null || imgUrl.isEmpty()) {
                    continue;
                }

                try {
                    URL url = new URL(new URL(baseUri), imgUrl);
                    System.out.println("正在下載圖片: " + url);

                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    InputStream inputStream = connection.getInputStream();
                    byte[] imageBytes = inputStream.readAllBytes();
                    inputStream.close();

                    // 將太極和五力圖轉為 Base64 編碼
                    if (imgUrl.contains("gettaichi") || imgUrl.contains("getfiveplot")) {
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        String imageMimeType = getMimeType(imgUrl);
                        String dataUri = "data:" + imageMimeType + ";base64," + base64Image;

                        // 修改 img 的 src 屬性為 Base64 數據 URI，並且添加顯示高度和寬度
                        img.attr("src", dataUri);
                        img.attr("height", "275px"); // 設定高度

                        System.out.println("太極或五力圖引用已修改為 Base64: " + imgUrl);
                    }
                } catch (Exception e) {
                    System.err.println("下載或嵌入圖片失敗: " + imgUrl);
                    e.printStackTrace();
                }
            }

            return doc.outerHtml();
        } catch (Exception e) {
            e.printStackTrace();
            return htmlContent;
        }
    }

    private static String getMimeType(String url) {
        if (url.endsWith(".png")) {
            return "image/png";
        } else if (url.endsWith(".jpg") || url.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (url.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}