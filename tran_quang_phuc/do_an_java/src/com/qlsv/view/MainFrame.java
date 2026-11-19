package com.qlsv.view;

import com.qlsv.model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** Trang tổng quan chính. Các mục vẫn mở đúng tab quản lý như phiên bản cũ. */
public class MainFrame extends JFrame {
    private final TaiKhoan taiKhoan;
    private QuanLyFrame quanLyFrame;

    public MainFrame(TaiKhoan tk) {
        this.taiKhoan = tk;
        setTitle("Campus Manager | Quản lý sinh viên");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 690));
        setSize(1220, 790);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BACKGROUND);
        root.add(taoSidebar(), BorderLayout.WEST);
        root.add(taoNoiDung(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel taoSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout(0, 22));
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBackground(UITheme.NAVY);
        sidebar.setBorder(new EmptyBorder(28, 18, 22, 18));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel logo = new JLabel("CAMPUS MANAGER");
        logo.setFont(new Font(UITheme.FONT, Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        JLabel tagline = new JLabel("STUDENT INFORMATION SYSTEM");
        tagline.setFont(new Font(UITheme.FONT, Font.PLAIN, 9));
        tagline.setForeground(new Color(169, 190, 222));
        brand.add(logo);
        brand.add(Box.createVerticalStrut(6));
        brand.add(tagline);

        JPanel account = new JPanel();
        account.setOpaque(true);
        account.setBackground(UITheme.NAVY_LIGHT);
        account.setBorder(new EmptyBorder(13, 12, 13, 12));
        account.setLayout(new BoxLayout(account, BoxLayout.Y_AXIS));
        JLabel user = new JLabel(taiKhoan.getHoTen());
        user.setFont(new Font(UITheme.FONT, Font.BOLD, 13));
        user.setForeground(Color.WHITE);
        JLabel role = new JLabel("Tài khoản: " + taiKhoan.getVaiTro());
        role.setFont(new Font(UITheme.FONT, Font.PLAIN, 11));
        role.setForeground(new Color(185, 204, 231));
        account.add(user);
        account.add(Box.createVerticalStrut(5));
        account.add(role);

        JPanel top = new JPanel(new BorderLayout(0, 22));
        top.setOpaque(false);
        top.add(brand, BorderLayout.NORTH);
        top.add(account, BorderLayout.CENTER);

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        JLabel navLabel = new JLabel("DANH MỤC QUẢN LÝ");
        navLabel.setFont(new Font(UITheme.FONT, Font.BOLD, 10));
        navLabel.setForeground(new Color(143, 166, 201));
        navLabel.setBorder(new EmptyBorder(0, 9, 10, 0));
        nav.add(navLabel);

        String[] items = {
                "Quản lý sinh viên",
                "Quản lý khoa",
                "Quản lý giảng viên",
                "Quản lý môn học",
                "Quản lý bảng điểm"
        };
        for (int i = 0; i < items.length; i++) {
            final int tab = i;
            JButton button = new JButton(items[i]);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFont(new Font(UITheme.FONT, Font.BOLD, 12));
            button.setForeground(new Color(224, 233, 247));
            button.setBackground(UITheme.NAVY);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setBorder(new EmptyBorder(12, 11, 12, 8));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            button.addActionListener(e -> moTrang(tab));
            button.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { button.setBackground(UITheme.NAVY_LIGHT); }
                @Override public void mouseExited(MouseEvent e) { button.setBackground(UITheme.NAVY); }
            });
            nav.add(button);
            nav.add(Box.createVerticalStrut(4));
        }

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        JButton logout = new JButton("Đăng xuất");
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        UITheme.styleButton(logout, "dark");
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        logout.addActionListener(e -> dangXuat());
        JButton exit = new JButton("Thoát ứng dụng");
        exit.setHorizontalAlignment(SwingConstants.LEFT);
        exit.setForeground(new Color(211, 220, 235));
        exit.setBackground(UITheme.NAVY);
        exit.setFocusPainted(false);
        exit.setBorderPainted(false);
        exit.setOpaque(true);
        exit.setBorder(new EmptyBorder(10, 14, 10, 10));
        exit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        exit.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thoát chương trình?", "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) System.exit(0);
        });
        bottom.add(logout);
        bottom.add(Box.createVerticalStrut(7));
        bottom.add(exit);

        sidebar.add(top, BorderLayout.NORTH);
        sidebar.add(nav, BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private JPanel taoNoiDung() {
        JPanel content = new JPanel(new BorderLayout(0, 22));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(28, 32, 28, 32));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel pageTitle = new JLabel("Tổng quan");
        pageTitle.setFont(new Font(UITheme.FONT, Font.BOLD, 25));
        pageTitle.setForeground(UITheme.TEXT);
        JLabel userInfo = new JLabel("Đang đăng nhập: " + taiKhoan.getTenDangNhap());
        userInfo.setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
        userInfo.setForeground(UITheme.MUTED);
        header.add(pageTitle, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(0, 20));
        center.setOpaque(false);
        center.add(taoTheChaoMung(), BorderLayout.NORTH);
        center.add(taoDanhSachChucNang(), BorderLayout.CENTER);

        content.add(header, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);
        return content;
    }

    private JPanel taoTheChaoMung() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UITheme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1, true),
                new EmptyBorder(24, 26, 24, 26)
        ));
        JLabel greeting = new JLabel("Xin chào, " + taiKhoan.getHoTen() + "!");
        greeting.setFont(new Font(UITheme.FONT, Font.BOLD, 23));
        greeting.setForeground(UITheme.TEXT);
        JLabel desc = new JLabel("Chọn một chức năng bên dưới để bắt đầu quản lý dữ liệu đào tạo.");
        desc.setFont(new Font(UITheme.FONT, Font.PLAIN, 13));
        desc.setForeground(UITheme.MUTED);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(greeting);
        text.add(Box.createVerticalStrut(8));
        text.add(desc);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private JPanel taoDanhSachChucNang() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 14));
        wrapper.setOpaque(false);
        JLabel title = new JLabel("CHỨC NĂNG HỆ THỐNG");
        title.setFont(new Font(UITheme.FONT, Font.BOLD, 12));
        title.setForeground(UITheme.MUTED);
        wrapper.add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 16, 16));
        grid.setOpaque(false);
        String[][] features = {
                {"01", "Quản lý sinh viên", "Hồ sơ, thông tin cá nhân và khoa của sinh viên."},
                {"02", "Quản lý khoa", "Danh sách khoa và thông tin liên hệ."},
                {"03", "Quản lý giảng viên", "Thông tin giảng viên và khoa phụ trách."},
                {"04", "Quản lý môn học", "Danh mục môn học, tín chỉ và giảng viên."},
                {"05", "Quản lý bảng điểm", "Nhập điểm quá trình, điểm thi và tổng kết."}
        };
        for (int i = 0; i < features.length; i++) {
            final int tab = i;
            JButton tile = new JButton("<html><div style='font-size:11px;color:#2A69DC;font-weight:bold'>"
                    + features[i][0] + " &nbsp; / &nbsp; QUẢN LÝ</div>"
                    + "<div style='font-size:16px;font-weight:bold;color:#232F42;margin-top:8px'>"
                    + features[i][1] + "</div>"
                    + "<div style='font-size:12px;color:#6F7C91;margin-top:6px'>"
                    + features[i][2] + "</div></html>");
            tile.setHorizontalAlignment(SwingConstants.LEFT);
            tile.setVerticalAlignment(SwingConstants.CENTER);
            tile.setBackground(UITheme.SURFACE);
            tile.setForeground(UITheme.TEXT);
            tile.setFont(new Font(UITheme.FONT, Font.PLAIN, 13));
            tile.setFocusPainted(false);
            tile.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UITheme.BORDER, 1, true),
                    new EmptyBorder(17, 18, 17, 18)
            ));
            tile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            tile.addActionListener(e -> moTrang(tab));
            tile.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { tile.setBackground(UITheme.SOFT_BLUE); }
                @Override public void mouseExited(MouseEvent e) { tile.setBackground(UITheme.SURFACE); }
            });
            grid.add(tile);
        }
        wrapper.add(grid, BorderLayout.CENTER);
        return wrapper;
    }

    private void dangXuat() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?", "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) return;
        if (quanLyFrame != null) quanLyFrame.dispose();
        new LoginFrame().setVisible(true);
        dispose();
    }

    private void moTrang(int tab) {
        if (quanLyFrame == null || !quanLyFrame.isDisplayable()) {
            quanLyFrame = new QuanLyFrame(tab);
        } else {
            quanLyFrame.chonTab(tab);
        }
        quanLyFrame.setVisible(true);
        quanLyFrame.toFront();
    }
}
