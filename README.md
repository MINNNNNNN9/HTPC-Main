# HTPC Project

這是一個用於生成 HRV 報告的 Java 專案，使用 OpenHTMLToPDF 將 HTML 轉換為 PDF 格式。

## 專案結構

.
├── online
│ └── downloaded_online.html
├── output
├── output.pdf
├── pom.xml
├── restructure.sh
├── src
│ └── main
│ ├── java
│ │ └── com
│ │ └── wulab
│ │ ├── htpc
│ │ │ ├── HTPC.java
│ │ │ ├── config
│ │ │ │ └── PathConfig.java
│ │ │ ├── data
│ │ │ │ ├── DataInitializer.java
│ │ │ │ └── DataProcessor.java
│ │ │ ├── html
│ │ │ │ ├── HtmlProcessor.java
│ │ │ │ └── OnlineHtmlConverter.java
│ │ │ ├── image
│ │ │ │ ├── HRVPlot.java
│ │ │ │ └── ImageHandler.java
│ │ │ ├── pdf
│ │ │ │ └── PdfConverter.java
│ │ │ └── utils
│ │ │ └── FileUtils.java
│ └── resources
│ ├── fonts
│ │ └── Microsoft JhengHei.ttf
│ ├── static
│ │ ├── css
│ │ │ ├── report.css
│ │ │ └── report_online.css
│ │ └── img
│ │ ├── five_power_plot.png
│ │ ├── report_id.jpg
│ │ ├── report_tw.jpg
│ │ └── taichi_plot.png
│ └── templates
│ └── report_tw.html
└── target
├── classes
├── generated-sources
├── maven-status
└── test-classes

## 主要功能

- **HTML 轉 PDF**：使用 `OnlineHtmlConverter` 將 HTML 文件轉換為 PDF。
- **圖片處理**：使用 `ImageHandler` 處理報告中的圖片。
- **數據處理**：使用 `DataProcessor` 處理 HRV 數據。

## 使用技術

- **Java**：主要開發語言。
- **OpenHTMLToPDF**：用於將 HTML 轉換為 PDF。
- **Jsoup**：用於解析和處理 HTML。
- **JTidy**：用於清理和格式化 HTML。

## 安裝與使用

1. **克隆專案**：
   ```bash
   git clone <repository-url>
   cd HTPC-main
   ```

2. **構建專案**：
   使用 Maven 構建專案：
   ```bash
   mvn clean install
   ```

3. **運行專案**：
   執行主類 `HTPC.java` 來生成報告：
   ```bash
   java -cp target/classes com.wulab.htpc.HTPC
   ```

## 資料夾說明

- `online`：存放下載的 HTML 文件。
- `output`：存放生成的 PDF 文件。
- `src/main/java`：Java 源碼。
- `src/main/resources`：資源文件，包括字體、CSS 和圖片。

## 貢獻
