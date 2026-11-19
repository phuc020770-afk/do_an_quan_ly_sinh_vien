package com.qlsv.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/** Bộ màu và tiện ích giao diện dùng chung cho toàn bộ ứng dụng. */
public final class UITheme {
    public static final String FONT = "Segoe UI";

    public static final Color NAVY = new Color(22, 39, 68);
    public static final Color NAVY_LIGHT = new Color(32, 55, 91);
    public static final Color PRIMARY = new Color(42, 105, 220);
    public static final Color PRIMARY_DARK = new Color(29, 82, 184);
    public static final Color BACKGROUND = new Color(243, 246, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color TEXT = new Color(35, 47, 66);
    public static final Color MUTED = new Color(111, 124, 145);
    public static final Color BORDER = new Color(222, 229, 239);
    public static final Color SOFT_BLUE = new Color(235, 242, 255);
    public static final Color DANGER = new Color(205, 65, 74);
    public static final Color SUCCESS = new Color(35, 145, 105);

    private UITheme() {}

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Dùng Look & Feel mặc định nếu hệ điều hành không hỗ trợ.
        }

        Font defaultFont = new Font(FONT, Font.PLAIN, 13);
        UIManager.put("defaultFont", defaultFont);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("PasswordField.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("TableHeader.font", new Font(FONT, Font.BOLD, 12));
        UIManager.put("TabbedPane.font", new Font(FONT, Font.BOLD, 13));
        UIManager.put("OptionPane.messageFont", defaultFont);
        UIManager.put("OptionPane.buttonFont", defaultFont);
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("TabbedPane.background", BACKGROUND);
        UIManager.put("TabbedPane.selected", SURFACE);
        UIManager.put("Table.selectionBackground", SOFT_BLUE);
        UIManager.put("Table.selectionForeground", TEXT);
    }

    public static void styleButton(JButton button, String type) {
        Color bg;
        Color fg = Color.WHITE;
        switch (type) {
            case "primary" -> bg = PRIMARY;
            case "danger" -> bg = DANGER;
            case "success" -> bg = SUCCESS;
            case "dark" -> bg = NAVY_LIGHT;
            case "soft" -> {
                bg = SOFT_BLUE;
                fg = PRIMARY_DARK;
            }
            default -> {
                bg = SURFACE;
                fg = TEXT;
            }
        }
        button.setFont(new Font(FONT, Font.BOLD, 13));
        button.setForeground(fg);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    public static void styleTextField(JTextField field) {
        field.setFont(new Font(FONT, Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(SURFACE);
        field.setCaretColor(PRIMARY);
        field.setBorder(inputBorder());
        field.setPreferredSize(new Dimension(150, 38));
    }

    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font(FONT, Font.PLAIN, 13));
        combo.setForeground(TEXT);
        combo.setBackground(SURFACE);
        combo.setBorder(inputBorder());
        combo.setPreferredSize(new Dimension(150, 38));
    }

    public static Border inputBorder() {
        return new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(7, 10, 7, 10)
        );
    }

    public static JPanel card(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(SURFACE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(16, 18, 16, 18)
        ));
        return panel;
    }

    /** Áp dụng font và màu thống nhất cho các thành phần trong form do từng tab tạo ra. */
    public static void styleFormTree(Component component) {
        if (component instanceof JPanel panel) {
            panel.setBackground(SURFACE);
        }
        if (component instanceof JLabel label) {
            label.setFont(new Font(FONT, Font.BOLD, 12));
            label.setForeground(MUTED);
        } else if (component instanceof JTextField field) {
            styleTextField(field);
        } else if (component instanceof JComboBox<?> combo) {
            styleComboBox(combo);
        }
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                styleFormTree(child);
            }
        }
    }
}
