#!/bin/bash

# 創建目錄結構
echo "創建目錄結構..."
mkdir -p src/main/java/com/wulab/htpc/{config,data,image,html,pdf,utils}

# 移動文件
echo "移動文件到新位置..."
mv src/main/java/PathConfig.java src/main/java/com/wulab/htpc/config/ 2>/dev/null || echo "PathConfig.java 已經移動或不存在"
mv src/main/java/DataInitializer.java src/main/java/DataProcessor.java src/main/java/com/wulab/htpc/data/ 2>/dev/null || echo "Data*.java 已經移動或不存在"
mv src/main/java/HRVPlot.java src/main/java/ImageHandler.java src/main/java/com/wulab/htpc/image/ 2>/dev/null || echo "圖像相關文件已經移動或不存在"
mv src/main/java/HtmlGenerator.java src/main/java/HtmlProcessor.java src/main/java/OnlineHtmlConverter.java src/main/java/com/wulab/htpc/html/ 2>/dev/null || echo "HTML相關文件已經移動或不存在"
mv src/main/java/PdfConverter.java src/main/java/com/wulab/htpc/pdf/ 2>/dev/null || echo "PdfConverter.java 已經移動或不存在"
mv src/main/java/FileUtils.java src/main/java/com/wulab/htpc/utils/ 2>/dev/null || echo "FileUtils.java 已經移動或不存在"
mv src/main/java/HTPC.java src/main/java/com/wulab/htpc/ 2>/dev/null || echo "HTPC.java 已經移動或不存在"

echo "清理舊的 class 文件..."
rm -rf target/classes/*

echo "完成！"
echo "請記得更新所有 Java 文件中的 package 聲明和 import 語句"
