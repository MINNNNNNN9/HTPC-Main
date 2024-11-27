package com.wulab.htpc.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HRVPlot {

    /**
     * 繪製五力圖（雷達圖）並保存為 PNG 檔案
     */
    public void getFivePowerPlot(double[] values, String outputPath) {
        String[] labels = {
                "Heart " + (int) (values[0]),
                "Health " + (int) (values[1]),
                "Sex " + (int) (values[2]),
                "Vital " + (int) (values[3]),
                "Fight " + (int) (values[4])
        };

        // 將輸入的百分比值轉換為 0-1 的比例
        double[] normalizedValues = new double[5];
        for (int i = 0; i < 5; i++) {
            normalizedValues[i] = values[i] / 100;
        }

        int width = 800;
        int height = 800;
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = 320; // 縮小半徑以留出標籤空間

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // 開啟抗鋸齒
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景設為白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 維度數量
        int N = normalizedValues.length;
        double angleStep = 2 * Math.PI / N;

        // 繪製最外圈五邊形
        Polygon outerPolygon = new Polygon();
        for (int i = 0; i < N; i++) {
            double angle = i * angleStep - Math.PI / 2;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            outerPolygon.addPoint(x, y);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawPolygon(outerPolygon);

        // 繪製內部四個圓形網格
        for (int i = 1; i <= 4; i++) {
            double r = radius * i / 5;
            g.setColor(Color.LIGHT_GRAY);
            // 繪製圓形
            g.drawOval(centerX - (int) r, centerY - (int) r, (int) r * 2, (int) r * 2);
        }

        // 繪製軸線
        for (int i = 0; i < N; i++) {
            double angle = i * angleStep - Math.PI / 2;
            int x = centerX + (int) (radius * normalizedValues[i] * Math.cos(angle));
            int y = centerY + (int) (radius * normalizedValues[i] * Math.sin(angle));
            g.setColor(Color.GRAY);
            g.drawLine(centerX, centerY, x, y);
        }

        // 繪製標籤
        for (int i = 0; i < N; i++) {
            String label = labels[i];
            int fontSize = 20;
            g.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int labelWidth = fm.stringWidth(label);
            int labelHeight = fm.getAscent();

            double angle = i * angleStep - Math.PI / 2;
            double labelRadius = radius + 30; // 調整標籤距離中心的距離

            int labelX = centerX + (int) ((labelRadius) * Math.cos(angle));
            int labelY = centerY + (int) ((labelRadius) * Math.sin(angle));

            // 調整標籤位置，避免遮擋圖表和超出圖片範圍
            if (i == 1 || i == 2) { // 右側標籤（Health 和 Sex）
                labelX += 20; // 向右移動20像素
            } else if (i == 3 || i == 4) { // 左側標籤（Vital 和 Fight）
                labelX -= 20; // 向左移動20像素
            }

            if (labelX + labelWidth / 2 > width) {
                labelX = width - labelWidth - 5;
            } else if (labelX - labelWidth / 2 < 0) {
                labelX = 5;
            } else {
                labelX -= labelWidth / 2;
            }

            if (labelY + labelHeight / 2 > height) {
                labelY = height - 5;
            } else if (labelY - labelHeight / 2 < 0) {
                labelY = labelHeight;
            } else {
                labelY += labelHeight / 2;
            }

            g.drawString(label, labelX, labelY);
        }

        // 繪製數據區域
        Polygon dataPolygon = new Polygon();
        for (int i = 0; i < N; i++) {
            double value = normalizedValues[i];
            double angle = i * angleStep - Math.PI / 2;
            int x = centerX + (int) (radius * value * Math.cos(angle));
            int y = centerY + (int) (radius * value * Math.sin(angle));
            dataPolygon.addPoint(x, y);
        }
        // 設置填充顏色為半透明藍色
        g.setColor(new Color(31, 119, 180, 64));
        g.fillPolygon(dataPolygon);

        // 繪製數據邊框
        g.setColor(new Color(31, 119, 180));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(dataPolygon);

        // 保存圖像
        g.dispose();
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("五力圖已保存為 " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 繪製太極圖並保存為 PNG 檔案
     */
    public void getTaiChiPlot(double ratio, int age, String outputPath) {
        int width = 800;
        int height = 800;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // 開啟抗鋸齒
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = width / 2;
        int centerY = height / 2;

        // 根據年齡計算外圓半徑
        double outradius;
        if (age >= 80) {
            outradius = 50;
        } else if (age <= 18) {
            outradius = 400;
        } else {
            outradius = (7.0 / 60) * (79 - age) * 50;
        }

        double insideradius2 = outradius * ratio;
        double insideradius1 = outradius - insideradius2;

        // 繪製背景
        Color[] colormap = {
                new Color(0xf7, 0x79, 0x7d),
                new Color(0xF8, 0x97, 0x7f),
                new Color(0xFA, 0xA5, 0x80),
                new Color(0xFB, 0xD7, 0x86),
                new Color(0xE5, 0xD7, 0x86),
                new Color(0xC6, 0xFF, 0x86),
                new Color(0xC6, 0xFF, 0xB0),
                new Color(0xC6, 0xFF, 0xDD)
        };

        for (int j = colormap.length - 1; j >= 0; j--) {
            g.setColor(colormap[j]);
            int radius = (j + 1) * 50;
            g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }

        // 繪製太極圖
        g.setColor(Color.BLACK);
        g.fillOval((int) (centerX - outradius), (int) (centerY - outradius),
                (int) (outradius * 2), (int) (outradius * 2));

        // 上半白色半圓
        g.setColor(Color.WHITE);
        g.fillArc((int) (centerX - outradius), (int) (centerY - outradius),
                (int) (outradius * 2), (int) (outradius * 2), 90, 180);

        // 小白圓
        g.fillOval((int) (centerX - insideradius1), (int) (centerY - outradius),
                (int) (insideradius1 * 2), (int) (insideradius1 * 2));

        // 小黑圓
        g.setColor(Color.BLACK);
        g.fillOval((int) (centerX - insideradius2),
                (int) (centerY + outradius - insideradius2 * 2),
                (int) (insideradius2 * 2), (int) (insideradius2 * 2));

        // 外圓邊框
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawOval((int) (centerX - outradius), (int) (centerY - outradius),
                (int) (outradius * 2), (int) (outradius * 2));

        // 保存圖像
        g.dispose();
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("太極圖已保存為 " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
