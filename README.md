# HTPC Project

這是一個用於生成 HRV 報告的 Java 專案，使用 OpenHTMLToPDF 將 HTML 轉換為 PDF 格式。

## 專案結構說明

```
.
├── online
│   └── downloaded_online.html      # 下載的在線 HTML 報告暫存檔
├── output                         # 輸出目錄
├── output.pdf                     # 生成的最終 PDF 報告
├── pom.xml                        # Maven 專案配置文件，管理依賴和構建設置
├── restructure.sh                 # 專案重構腳本
├── src
│   └── main
│       ├── java/com/wulab/htpc
│       │   ├── HTPC.java         # 主程式入口，協調整個轉換流程
│       │   ├── config
│       │   │   └── PathConfig.java    # 路徑配置管理，處理各種文件路徑
│       │   ├── data
│       │   │   ├── DataInitializer.java    # 數據初始化，設置初始參數
│       │   │   └── DataProcessor.java      # 數據處理，處理 HRV 相關數據
│       │   ├── html
│       │   │   ├── HtmlProcessor.java      # HTML 處理器，處理本地 HTML
│       │   │   └── OnlineHtmlConverter.java # 在線 HTML 轉換器，處理在線報告
│       │   ├── image
│       │   │   ├── HRVPlot.java           # HRV 圖表生成
│       │   │   └── ImageHandler.java       # 圖片處理，包括下載和轉換
│       │   ├── pdf
│       │   │   └── PdfConverter.java       # PDF 轉換器，將 HTML 轉為 PDF
│       │   └── utils
│       │       └── FileUtils.java          # 文件工具類，處理文件操作
│       └── resources
│           ├── fonts
│           │   └── Microsoft JhengHei.ttf  # 微軟正黑體字型檔
│           ├── static
│           │   ├── css
│           │   │   ├── report.css          # 本地報告樣式表
│           │   │   └── report_online.css   # 在線報告樣式表
│           │   └── img                     # 靜態圖片資源目錄
│           │       ├── five_power_plot.png # 五力圖範本
│           │       ├── report_id.jpg       # 報告 ID 頁面背景
│           │       ├── report_tw.jpg       # 報告台灣版背景
│           │       └── taichi_plot.png     # 太極圖範本
│           └── templates
│               └── report_tw.html          # 報告 HTML 範本
└── target                        # Maven 構建輸出目錄
    ├── classes                   # 編譯後的類文件
    ├── generated-sources         # 生成的源代碼
    ├── maven-status              # Maven 構建狀態
    └── test-classes             # 測試類編譯輸出
```

## 主要功能模組說明

1. **配置模組 (config)**
   - `PathConfig.java`: 統一管理所有文件路徑，確保路徑一致性

2. **數據處理模組 (data)**
   - `DataInitializer.java`: 初始化數據和參數設置
   - `DataProcessor.java`: 處理和轉換 HRV 相關數據

3. **HTML 處理模組 (html)**
   - `HtmlProcessor.java`: 處理本地 HTML 模板
   - `OnlineHtmlConverter.java`: 處理在線 HTML 轉換

4. **圖片處理模組 (image)**
   - `HRVPlot.java`: 生成 HRV 相關圖表
   - `ImageHandler.java`: 處理圖片下載和轉換

5. **PDF 轉換模組 (pdf)**
   - `PdfConverter.java`: 將處理後的 HTML 轉換為 PDF

6. **工具模組 (utils)**
   - `FileUtils.java`: 提供通用文件操作功能

## 資源文件說明

1. **字體資源**
   - `Microsoft JhengHei.ttf`: 用於確保中文字體正確顯示

2. **樣式表**
   - `report.css`: 本地報告的樣式定義
   - `report_online.css`: 在線報告的樣式定義

3. **圖片資源**
   - 包含報告所需的各種背景圖片和圖表範本

4. **HTML 範本**
   - `report_tw.html`: 台灣版報告的 HTML 範本

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
