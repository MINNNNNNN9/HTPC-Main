package com.wulab.htpc.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

import com.wulab.htpc.config.PathConfig;
import com.wulab.htpc.utils.FileUtils;
import com.wulab.htpc.data.DataInitializer;

public class ImageHandler {
    private final HRVPlot plotGenerator;

    public ImageHandler() {
        this.plotGenerator = new HRVPlot();
    }

    public void generateAndEncodeImages(Map<String, String> dataMap) {
        try {
            FileUtils.createDirectoryIfNotExists(PathConfig.IMAGE_DIR_PATH);

            // 從 DataInitializer 獲取數據
            double[] fivePowerValues = DataInitializer.getFivePowerValues(dataMap);
            double taiChiRatio = DataInitializer.getTaiChiRatio(dataMap);
            int taiChiAge = DataInitializer.getTaiChiAge(dataMap);

            // 生成圖表
            plotGenerator.getFivePowerPlot(fivePowerValues, PathConfig.FIVE_POWER_IMAGE_PATH);
            plotGenerator.getTaiChiPlot(taiChiRatio, taiChiAge, PathConfig.TAICHI_IMAGE_PATH);

            // 編碼圖片為 Base64
            String taichiBase64 = encodeImageToBase64(PathConfig.TAICHI_IMAGE_PATH);
            String fivePowerBase64 = encodeImageToBase64(PathConfig.FIVE_POWER_IMAGE_PATH);

            // 將編碼後的圖片添加到數據映射中
            dataMap.put("taichiImage", "data:image/png;base64," + taichiBase64);
            dataMap.put("fivePowerImage", "data:image/png;base64," + fivePowerBase64);
        } catch (IOException e) {
            System.err.println("生成或編碼圖片時發生錯誤");
            e.printStackTrace();
        }
    }

    private String encodeImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new FileNotFoundException("找不到圖片文件: " + imagePath);
        }
        return Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
    }
}