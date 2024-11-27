package com.wulab.htpc.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.nodes.Document;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PdfConverter {
    public void convertToPdf(Document doc, String pdfFilePath, String fontPath) throws Exception {
        try (OutputStream os = new FileOutputStream(pdfFilePath)) {
            String cleanedHtml = cleanHtml(doc.html());

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(cleanedHtml, new File(".").toURI().toString());
            builder.toStream(os);
            builder.useFont(new File(fontPath), "Microsoft JhengHei");
            builder.run();

            System.out.println("PDF 生成成功！");
        } catch (Exception e) {
            throw new Exception("PDF 轉換失敗: " + e.getMessage());
        }
    }

    private String cleanHtml(String html) throws Exception {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);

        ByteArrayInputStream input = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        tidy.parse(input, output);
        
        return output.toString("UTF-8");
    }
}