package com.wulab.htpc.data;

import java.util.HashMap;
import java.util.Map;

public class DataProcessor {

    public Map<String, String> analyzeData(Map<String, Double> inputData) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Double> entry : inputData.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            result.put(key, analyzeValue(value));
        }
        return result;
    }

    private String analyzeValue(Double value) {
        if (value < 50) {
            return "偏低";
        } else if (value > 100) {
            return "偏高";
        } else {
            return "正常";
        }
    }

    public String analyzeBMI(double value) {
        if (value < 18.5) return "體重過輕";
        if (value <= 24.0) return "正常";
        if (value <= 27.0) return "過重";
        return "肥胖";
    }
    
    public String analyzeHeartRate(double value) {
        if (value < 60) return "偏慢";
        if (value <= 100) return "正常";
        return "偏快";
    }
    
    public String analyzeSDNN(double value) {
        if (value < 20) return "偏低";
        if (value <= 100) return "正常";
        return "偏高";
    }
    
    public String analyzeRMSSD(double value) {
        if (value < 20) return "偏低";
        return "正常";
    }
    
    public String analyzeANSAge(double ansAge, int realAge) {
        if (ansAge <= realAge) return "正常";
        return "偏高";
    }
    
    public String analyzeANS(double value) {
        if (value < 20) return "偏低";
        if (value <= 30) return "正常";
        return "偏高";
    }
    
    public String analyzeSYM(double value) {
        if (value < 65) return "偏低";
        if (value <= 85) return "正常";
        return "偏高";
    }
    
    public String analyzeVAG(double value) {
        if (value < 55) return "偏低";
        if (value <= 75) return "正常";
        return "偏高";
    }
    
    public String analyzeSYMModulation(double value) {
        if (value < 0.8) return "偏低";
        if (value <= 1.5) return "正常";
        return "偏高";
    }
    
    public String analyzeBalance(double value) {
        if (value < -1.5) return "副交感優勢";
        if (value <= 1.5) return "平衡";
        return "交感優勢";
    }
}
