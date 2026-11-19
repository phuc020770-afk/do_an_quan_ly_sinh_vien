package com.qlsv.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Cửa sổ quản lý các danh mục; giữ nguyên 5 tab và hành vi nạp lại dữ liệu. */
public class QuanLyFrame extends JFrame {
    private final JTabbedPane tabs = new JTabbedPane();
    private final List<CrudPanel<?>> panels = new ArrayList<>();

    public QuanLyFrame(int tabBanDau) {
        setTitle("Campus Manager | Quản lý dữ liệu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 700));
        setSize(1240, 800);
        setLocationRelativeTo(null);

        panels.add(new SinhVienPanel());
        panels.add(new KhoaPanel());
        panels.add(new GiangVienPanel());
        panels.add(new MonHocPanel());
        panels.add(new BangDiemPanel());

        String[] ten = {"Sinh viên", "Khoa", "Giảng viên", "Môn học", "Bảng điểm"};
        String[] kyHieu = {"01", "02", "03", "04", "05"};
        for (int i = 0; i < ten.length; i++) {
            tabs.addTab(kyHieu[i] + "  " + ten[i], panels.get(i));
        }
        tabs.setFont(new Font(UITheme.FONT, Font.BOLD, 12));
        tabs.setForeground(UITheme.TEXT);
        tabs.setBackground(UITheme.BACKGROUND);
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            if (index >= 0) panels.get(index).kichHoat();
        });

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(UITheme.BACKGROUND);
        root.add(taoHeader(), BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        setContentPane(root);

        int selected = Math.max(0, Math.min(tabBanDau, panels.size() - 1));
        tabs.setSelectedIndex(selected);
        panels.get(selected).kichHoat();
    }

    private JPanel taoHeader() {
        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(UITheme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER),
                new EmptyBorder(17, 24, 17, 24)
        ));

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("QUẢN LÝ DỮ LIỆU ĐÀO TẠO");
        title.setFont(new Font(UITheme.FONT, Font.BOLD, 17));
        title.setForeground(UITheme.TEXT);
        JLabel subtitle = new JLabel("Quản lý tập trung sinh viên, khoa, giảng viên, môn học và bảng điểm");
        subtitle.setFont(new Font(UITheme.FONT, Font.PLAIN, 11));
        subtitle.setForeground(UITheme.MUTED);
        labels.add(title);
        labels.add(Box.createVerticalStrut(4));
        labels.add(subtitle);

        JButton btnDong = new JButton("Đóng cửa sổ");
        UITheme.styleButton(btnDong, "soft");
        btnDong.addActionListener(e -> dispose());

        header.add(labels, BorderLayout.CENTER);
        header.add(btnDong, BorderLayout.EAST);
        return header;
    }

    public void chonTab(int i) {
        if (i < 0 || i >= panels.size()) return;
        if (tabs.getSelectedIndex() == i) panels.get(i).kichHoat();
        else tabs.setSelectedIndex(i);
    }
}
