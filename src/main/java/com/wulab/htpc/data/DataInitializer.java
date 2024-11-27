package com.wulab.htpc.data;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataInitializer {
    public static Map<String, String> initializeDataMap() {
        Map<String, String> dataMap = new HashMap<>();
        DataProcessor processor = new DataProcessor();
        
        // 獲取當前日期和時間
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        // 基本信息
        dataMap.put("UserName", "張三");
        dataMap.put("gender", "男");
        dataMap.put("Technician", "李四");
        dataMap.put("userID", "007F0055");
        dataMap.put("Birthdate", "1990/01/01");
        dataMap.put("datadate", now.format(dateFormatter));
        dataMap.put("datatime", now.format(timeFormatter));
        dataMap.put("Age", "34");
        dataMap.put("XID", "X123456789");
        
        // BMI 相關
        double bmiValue = 22.5;
        dataMap.put("bmi", String.format("%.1f", bmiValue));
        dataMap.put("bmi_d", processor.analyzeBMI(bmiValue));
        
        // 心率相關
        double hrValue = 91.8;
        dataMap.put("HR", String.format("%.1f", hrValue));
        dataMap.put("HR_d", processor.analyzeHeartRate(hrValue));
        
        // SDNN 相關
        double sdValue = 21.2;
        dataMap.put("SD", String.format("%.1f", sdValue));
        dataMap.put("SDNN_d", processor.analyzeSDNN(sdValue));
        
        // RMSSD 相關
        double rmssdValue = 30.5;
        dataMap.put("RMSSD", String.format("%.1f", rmssdValue));
        dataMap.put("RMSSD_d", processor.analyzeRMSSD(rmssdValue));
        
        // ANS 相關
        double ansAgeValue = 46.0;
        dataMap.put("ANSAGE", String.format("%.0f", ansAgeValue));
        dataMap.put("ANSAGE_d", processor.analyzeANSAge(ansAgeValue, 34)); // 34是實際年齡
        
        double ansAvgValue = 25.0;
        double ansSDValue = 5.0;
        dataMap.put("ANS_AVG", String.format("%.0f", ansAvgValue));
        dataMap.put("ANS_SD", String.format("%.0f", ansSDValue));
        dataMap.put("ANS_d", processor.analyzeANS(ansAvgValue));
        
        // 交感神經相關
        double symAvgValue = 75.0;
        double symSDValue = 10.0;
        dataMap.put("SYM_AVG", String.format("%.0f", symAvgValue));
        dataMap.put("SYM_SD", String.format("%.0f", symSDValue));
        dataMap.put("SYM_d", processor.analyzeSYM(symAvgValue));
        
        // 副交感神經相關
        double vagAvgValue = 65.0;
        double vagSDValue = 8.0;
        dataMap.put("VAG_AVG", String.format("%.0f", vagAvgValue));
        dataMap.put("VAG_SD", String.format("%.0f", vagSDValue));
        dataMap.put("VAG_d", processor.analyzeVAG(vagAvgValue));
        
        // 其他指標
        double symModValue = 1.0;
        dataMap.put("SYM_modulation", String.format("%.1f", symModValue));
        dataMap.put("SYM_modulation_d", processor.analyzeSYMModulation(symModValue));
        
        double balanceValue = 0.5;
        dataMap.put("Balance", String.format("%.1f", balanceValue));
        dataMap.put("Balance_d", processor.analyzeBalance(balanceValue));
        
        // HRV Chart 相關數據
        dataMap.put("TP", "1234.5");
        dataMap.put("VL", "456.7");
        dataMap.put("HF", "789.0");
        dataMap.put("LF", "321.0");
        
        // 五力圖和太極圖所需的數據
        dataMap.put("heart", "80");
        dataMap.put("health", "85");
        dataMap.put("sex", "75");
        dataMap.put("vital", "90");
        dataMap.put("fight", "70");
        
        return dataMap;
    }

    public static double[] getFivePowerValues(Map<String, String> dataMap) {
        return new double[] {
            Double.parseDouble(dataMap.get("heart")),
            Double.parseDouble(dataMap.get("health")),
            Double.parseDouble(dataMap.get("sex")),
            Double.parseDouble(dataMap.get("vital")),
            Double.parseDouble(dataMap.get("fight"))
        };
    }

    public static double getTaiChiRatio(Map<String, String> dataMap) {
        return Double.parseDouble(dataMap.get("Balance"));
    }

    public static int getTaiChiAge(Map<String, String> dataMap) {
        return Integer.parseInt(dataMap.get("Age"));
    }
}
