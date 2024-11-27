package com.wulab.htpc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class HRVGui extends JFrame {
    // 縮小圖標
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public HRVGui() {
        setTitle("Xenon_HRV");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 左側面板
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // 用戶參數
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBorder(BorderFactory.createTitledBorder("User Parameter"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // 允許水平和垂直擴展
        gbc.insets = new Insets(2, 2, 2, 2); // 間距
        gbc.anchor = GridBagConstraints.NORTHWEST; // 靠左上對齊

        Border blackBorder = new LineBorder(Color.BLACK, 1); // 黑色邊框，寬度為1

        // UserID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // 允許水平擴展
        gbc.weighty = 0.1; // 允許垂直擴展
        JLabel userIdLabel = new JLabel("UserID");
        userPanel.add(userIdLabel, gbc);
        gbc.gridy = 1;
        JTextField userIdField = new JTextField(10);
        userIdField.setBorder(blackBorder); // 添加黑色邊框
        userIdField.setPreferredSize(new Dimension(userIdField.getPreferredSize().width, 30)); // 設置高度
        userPanel.add(userIdField, gbc);

        // Name
        gbc.gridy = 2;
        JLabel nameLabel = new JLabel("Name");
        userPanel.add(nameLabel, gbc);
        gbc.gridy = 3;
        JTextField nameField = new JTextField(10);
        nameField.setBorder(blackBorder); // 添加黑色邊框
        nameField.setPreferredSize(new Dimension(nameField.getPreferredSize().width, 30)); // 設置高度
        userPanel.add(nameField, gbc);

        // Measure Time
        gbc.gridy = 4;
        JLabel measureTimeLabel = new JLabel("Measure Time");
        userPanel.add(measureTimeLabel, gbc);
        gbc.gridy = 5;
        JTextField timeField = new JTextField(10);
        timeField.setEditable(false);
        timeField.setBackground(UIManager.getColor("TextField.inactiveBackground")); // 使用默認的不可編輯背景顏色
        timeField.setForeground(UIManager.getColor("TextField.inactiveForeground")); // 使用默認的不可編輯前景顏色
        timeField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // 白色邊框
        timeField.setPreferredSize(new Dimension(timeField.getPreferredSize().width, 30)); // 設置高度
        userPanel.add(timeField, gbc);

        // 使用 Timer 更新時間
        Timer timeUpdater = new Timer(1000, e -> {
            timeField.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        });
        timeUpdater.start();

        // Gender
        gbc.gridy = 6;
        JLabel genderLabel = new JLabel("Gender");
        userPanel.add(genderLabel, gbc);

        gbc.gridy = 7;
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBackground(Color.WHITE);
        genderComboBox.setPreferredSize(new Dimension(genderComboBox.getPreferredSize().width, 30)); // 設置高度
        userPanel.add(genderComboBox, gbc);

        // BirthDate
        gbc.gridy = 8;
        JLabel birthDateLabel = new JLabel("BirthDate");
        userPanel.add(birthDateLabel, gbc);

        gbc.gridy = 9;
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(new Dimension(dateSpinner.getPreferredSize().width, 30)); // 設置高度
        userPanel.add(dateSpinner, gbc);

        // Height
        gbc.gridy = 10;
        JPanel heightPanel = new JPanel(new BorderLayout());
        JLabel heightLabel = new JLabel("Height ");
        heightPanel.add(heightLabel, BorderLayout.WEST);
        JTextField heightField = new JTextField(10);
        heightField.setBorder(blackBorder);
        heightField.setPreferredSize(new Dimension(heightField.getPreferredSize().width, 30)); // 設置高度
        heightPanel.add(heightField, BorderLayout.CENTER);
        userPanel.add(heightPanel, gbc);

        // Batch
        gbc.gridy = 11;
        JPanel batchPanel = new JPanel(new BorderLayout());
        JLabel batchLabel = new JLabel("Batch   ");
        batchPanel.add(batchLabel, BorderLayout.WEST);
        JTextField batchField = new JTextField(10);
        batchField.setBorder(blackBorder);
        batchField.setPreferredSize(new Dimension(batchField.getPreferredSize().width, 30)); // 保持Batch的寬度
        batchPanel.add(batchField, BorderLayout.CENTER);
        userPanel.add(batchPanel, gbc);

        // Weight
        gbc.gridx = 1;
        gbc.gridy = 10;
        JPanel weightPanel = new JPanel(new BorderLayout());
        JLabel weightLabel = new JLabel("Weight       ");
        weightPanel.add(weightLabel, BorderLayout.WEST);
        JTextField weightField = new JTextField(10);
        weightField.setBorder(blackBorder);
        weightField.setPreferredSize(new Dimension(weightField.getPreferredSize().width, 30)); // 設置高度
        weightPanel.add(weightField, BorderLayout.CENTER);
        userPanel.add(weightPanel, gbc);

        // Technician
        gbc.gridy = 11;
        JPanel technicianPanel = new JPanel(new BorderLayout());
        JLabel technicianLabel = new JLabel("Technician ");
        technicianPanel.add(technicianLabel, BorderLayout.WEST);
        JTextField technicianField = new JTextField(10);
        technicianField.setBorder(blackBorder);
        technicianField.setPreferredSize(new Dimension(technicianField.getPreferredSize().width, 30)); // 保持Technician的寬度
        technicianPanel.add(technicianField, BorderLayout.CENTER);
        userPanel.add(technicianPanel, gbc);

        // Memo
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 10; // 讓 Memo 占據多行
        gbc.fill = GridBagConstraints.BOTH;
        JPanel memoPanel = new JPanel(new BorderLayout());
        JLabel memoLabel = new JLabel("Memo");
        memoPanel.add(memoLabel, BorderLayout.NORTH);
        JTextField memoField = new JTextField(10);
        memoField.setBorder(blackBorder); // 添加黑色邊框
        memoField.setPreferredSize(new Dimension(technicianField.getPreferredSize().width, memoField.getPreferredSize().height)); // 設置寬度與Technician對齊
        memoPanel.add(memoField, BorderLayout.CENTER);
        userPanel.add(memoPanel, gbc);

        leftPanel.add(userPanel, BorderLayout.NORTH); // 將 userPanel 放在上方

        // 設備狀態
        JPanel deviceStatusPanel = new JPanel();
        deviceStatusPanel.setLayout(new BorderLayout());
        deviceStatusPanel.setPreferredSize(new Dimension(300, 140)); // 確保面板大小足夠

        // 創建帶圖標的標題
        ImageIcon greenLightIcon = new ImageIcon("/Users/YM/Desktop/GUI/green_light.png");
        greenLightIcon = resizeIcon(greenLightIcon, 12, 12);
        JLabel titleLabel = new JLabel("Device Status", greenLightIcon, JLabel.LEFT);
        deviceStatusPanel.setBorder(BorderFactory.createTitledBorder(titleLabel.getText()));

        // 信號面板
        JPanel signalPanel = new JPanel();
        signalPanel.setLayout(new BoxLayout(signalPanel, BoxLayout.Y_AXIS));

        ImageIcon redLightIcon = new ImageIcon("/Users/YM/Desktop/GUI/red_light.png");
        redLightIcon = resizeIcon(redLightIcon, 12, 12);

        // 假設有個方法來檢查設備狀態
        boolean device1Connected = checkDeviceStatus("device1");
        boolean device2Connected = checkDeviceStatus("device2");
        boolean device3Connected = checkDeviceStatus("device3");

        JLabel signal1 = new JLabel("Device 1");
        signal1.setIcon(device1Connected ? greenLightIcon : redLightIcon);
        signalPanel.add(signal1);

        JLabel signal2 = new JLabel("Device 2");
        signal2.setIcon(device2Connected ? greenLightIcon : redLightIcon);
        signalPanel.add(signal2);

        JLabel signal3 = new JLabel("Device 3");
        signal3.setIcon(device3Connected ? greenLightIcon : redLightIcon);
        signalPanel.add(signal3);

        deviceStatusPanel.add(signalPanel, BorderLayout.NORTH);

        // 按鈕面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(new JButton("Configuration"));
        buttonPanel.add(new JButton("Stop"));

        deviceStatusPanel.add(buttonPanel, BorderLayout.SOUTH);

        leftPanel.add(deviceStatusPanel, BorderLayout.SOUTH); // 將 deviceStatusPanel 放在下方

        add(leftPanel, BorderLayout.WEST); // 左側面板

        // 中央面板
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // HRV 參數
        JPanel hrvPanel = new JPanel();
        hrvPanel.setLayout(new BorderLayout());
        hrvPanel.setBorder(BorderFactory.createTitledBorder("HRV Parameter"));

        // 進度條
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(90); // 1:30 分鐘
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(144, 238, 144)); // 設置為柔和的淺綠色

        // 自定義進度條 UI
        progressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected Dimension getPreferredInnerHorizontal() {
                return new Dimension(300, 25); // 設置進度條的寬度和厚度
            }
        });

        // 設置邊框
        progressBar.setBorder(new LineBorder(Color.BLACK, 2)); // 黑色邊框，寬度為2

        hrvPanel.add(progressBar, BorderLayout.NORTH);

        // 標籤面板
        JPanel labelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 70, 0)); // 設置水平間距為70
        labelsPanel.add(new JLabel("HR:"));
        labelsPanel.add(new JLabel("SD:"));
        labelsPanel.add(new JLabel("TP:"));
        labelsPanel.add(new JLabel("HF:"));
        labelsPanel.add(new JLabel("LF:"));
        labelsPanel.add(new JLabel("VL:"));

        hrvPanel.add(labelsPanel, BorderLayout.CENTER);

        // 二維碼
        ImageIcon qrCodeIcon = new ImageIcon("/Users/YM/Desktop/GUI/QRCORD.jpg");
        Image qrCodeImage = qrCodeIcon.getImage();
        // 調整縮放比例放大二維碼
        Image scaledQrCodeImage = qrCodeImage.getScaledInstance(500, 425, Image.SCALE_SMOOTH);
        JLabel qrCodeLabel = new JLabel(new ImageIcon(scaledQrCodeImage));

        hrvPanel.add(qrCodeLabel, BorderLayout.SOUTH);

        // 確保面板重新布局和重繪
        hrvPanel.revalidate();
        hrvPanel.repaint();

        centerPanel.add(hrvPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER); // 中央面板

        // 底部面板
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // IHR 面板
        JPanel ihrPanel = new JPanel();
        ihrPanel.setLayout(new BorderLayout());
        ihrPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加內邊距

        // IHR 標籤
        JLabel ihrLabel = new JLabel("IHR:");
        ihrLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ihrPanel.add(ihrLabel, BorderLayout.WEST);

        // 無框容面板
        JPanel transparentPanel = new JPanel(new BorderLayout());
        transparentPanel.setOpaque(false); // 設置為透明，無邊框

        // 心電圖面板（波形圖）
        JPanel ecgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 啟用抗鋸齒
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 設置繪圖區域的內邊距
                int padding = 40;
                int x0 = padding;
                int y0 = getHeight() - padding;
                int xMax = getWidth() - padding;
                int yMax = padding;

                // 繪製完整的邊框
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x0, yMax, xMax - x0, y0 - yMax);

                // 設置虛線樣式
                float[] dashPattern = {5, 5}; // 虛線的樣式：5像素實線，5像素空白

                // 繪製 Y 軸刻度和對應的虛線
                int yTicks = 5;
                int[] yValues = {0, 64, 128, 192, 256}; // 對應的刻度值
                for (int i = 0; i < yTicks; i++) {
                    int y = y0 - i * (y0 - yMax) / (yTicks - 1);
                    g2d.drawLine(x0 - 5, y, x0 + 5, y); // 主刻度線

                    // 設置虛線樣式並繪製虛線
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                    g2d.drawLine(x0, y, xMax, y); // 虛線
                    g2d.setStroke(new BasicStroke()); // 重置為實線

                    g2d.drawString(String.valueOf(yValues[i]), x0 - 30, y + 5); // 繪製刻度值

                    // 繪製小刻度線
                    if (i < yTicks - 1) {
                        int smallTicks = 4; // 小刻度數量為4，表示3條小刻度線
                        double smallTickSpacing = (double)(y0 - yMax) / (yTicks - 1) / smallTicks;
                        for (int j = 1; j < smallTicks; j++) {
                            int smallY = y - (int)(j * smallTickSpacing);
                            g2d.drawLine(x0-3, smallY, x0, smallY); // 小刻度線緊貼長方形
                        }
                    }
                }

                // 定義 xTicks 變數
                int xTicks = 10; // 根據需要調整刻度數量

                // 算主刻度之間距離
                double deltaX = (double)(xMax - x0) / xTicks;

                // 繪製 X 軸刻度和對應的虛線
                int[] xValues = {0, 125, 250, 375, 500, 625, 750, 875, 1000, 1125, 1250}; // 對應的刻度值
                for (int i = 0; i <= xTicks; i++) {
                    int x = x0 + (int)(i * deltaX);
                    // 繪製主刻度線
                    g2d.drawLine(x, y0 - 5, x, y0 + 5);

                    // 設置虛線樣式並繪製貫穿的虛線
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                    g2d.drawLine(x, yMax, x, y0);
                    g2d.setStroke(new BasicStroke()); // 重置為實線

                    // 繪製刻度值
                    g2d.drawString(String.valueOf(xValues[i]), x - 15, y0 + 20);

                    // 繪小刻度線
                    if (i < xTicks) {
                        int smallTicks = 4; // 小刻度數量為4，表示3條小刻度線
                        double smallTickSpacing = deltaX / smallTicks;
                        for (int j = 1; j < smallTicks; j++) {
                            int smallX = x + (int)(j * smallTickSpacing);
                            g2d.drawLine(smallX, y0, smallX, y0 + 3); // 小刻度線緊貼長方形
                        }
                    }
                }

                // 恢復默認的實線樣式
                g2d.setStroke(new BasicStroke());
            }
        };
        ecgPanel.setPreferredSize(new Dimension(600, 250)); // 設置大小
        // 設置白色立體邊框
        ecgPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY));

        // 將心電圖面板添加到透明面板
        transparentPanel.add(ecgPanel, BorderLayout.CENTER);

        // 將透明面板添加到 IHR 面板
        ihrPanel.add(transparentPanel, BorderLayout.CENTER);

        // 將 IHR 面板添加到底部面板
        bottomPanel.add(ihrPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH); // 底板

        // 確保 JFrame 允許調整大小
        setResizable(true);

        // 更新進度條的值
        Timer progressBarUpdater = new Timer(1000, e -> {
            int value = progressBar.getValue();
            if (value < progressBar.getMaximum()) {
                progressBar.setValue(value + 1);
            }
        });
        progressBarUpdater.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HRVGui gui = new HRVGui();
            gui.setVisible(true);
        });
    }

    // 假設的備狀態檢查方法
    private boolean checkDeviceStatus(String deviceName) {
        // 這裡可以實現實際的設備狀態檢查邏輯
        // 目前返回 false 作為示例
        return false;
    }
}