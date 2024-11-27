package com.wulab.htpc;

import org.jsoup.nodes.Document;
import java.util.Map;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;

import com.wulab.htpc.config.PathConfig;
import com.wulab.htpc.utils.FileUtils;
import com.wulab.htpc.data.DataInitializer;
import com.wulab.htpc.html.HtmlProcessor;
import com.wulab.htpc.html.OnlineHtmlConverter;
import com.wulab.htpc.image.ImageHandler;
import com.wulab.htpc.pdf.PdfConverter;

public class HTPC {
    private static final String ONLINE_API_URL = "https://example.com/api/generate-report"; // 替換為實際的API地址
    private static final String ONLINE_HTML_URL = "https://service.kylab906.com/getreportbyid?DataID=789BB267AB20240924152142";
    
    public static void main(String[] args) throws Exception {
        // 測試線上 HTML 轉換
        testOnlineHtmlConversion();
    }

    private static void testOnlineHtmlConversion() {
        try {
            FileUtils.createDirectoryIfNotExists(PathConfig.ONLINE_DIR_PATH);
            
            System.out.println("開始從線上獲取 HTML 並轉換為 PDF...");
            OnlineHtmlConverter.convertOnlineHtmlToPdf(
                ONLINE_HTML_URL,
                PathConfig.PDF_OUTPUT_PATH,
                PathConfig.FONT_PATH,
                PathConfig.ONLINE_CSS_PATH,
                PathConfig.ONLINE_DIR_PATH
            );
            
            System.out.println("線上 HTML 轉換 PDF 成功！");
        } catch (Exception e) {
            System.err.println("線上 HTML 轉換失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean tryPostToOnlineServer(Map<String, String> dataMap) {
        try {
            URL url = new URL(ONLINE_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // 將 dataMap 轉換為 JSON
            String jsonInputString = new Gson().toJson(dataMap);

            // 發送數據
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            
            // 檢查響應狀態
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 可以在這裡處理成功響應
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.err.println("服務器端點未找到 (404)");
                return false;
            } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                System.err.println("服務器內部錯誤 (500)");
                return false;
            } else {
                System.err.println("服務器返回未預期的響應碼: " + responseCode);
                return false;
            }

        } catch (IOException e) {
            System.err.println("連接服務器時發生錯誤: " + e.getMessage());
            return false;
        }
    }

    private static void processLocalHtml(Map<String, String> dataMap) throws Exception {
        try {
            ImageHandler imageHandler = new ImageHandler();
            imageHandler.generateAndEncodeImages(dataMap);

            HtmlProcessor htmlProcessor = new HtmlProcessor();
            Document doc = htmlProcessor.processHtml(
                PathConfig.HTML_TEMPLATE_PATH,
                dataMap,
                PathConfig.LOCAL_CSS_PATH
            );

            PdfConverter pdfConverter = new PdfConverter();
            pdfConverter.convertToPdf(
                doc,
                PathConfig.PDF_OUTPUT_PATH,
                PathConfig.FONT_PATH
            );
            
            System.out.println("本地 PDF 生成成功！");
        } catch (Exception e) {
            System.err.println("處理本地 HTML 時發生錯誤: " + e.getMessage());
            throw e;
        }
    }
}