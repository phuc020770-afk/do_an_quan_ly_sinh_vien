package com.qlsv.view;

import com.qlsv.dao.TaiKhoanDAO;
import com.qlsv.model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

/** Màn hình đăng nhập được thiết kế lại, giữ nguyên DAO và quy trình xác thực cũ. */
public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final JButton btnLogin = new JButton("ĐĂNG NHẬP HỆ THỐNG");
    private final JLabel lblStatus = new JLabel(" ");
    private final JCheckBox chkHienMatKhau = new JCheckBox("Hiển thị mật khẩu");

    public LoginFrame() {
        setTitle("Đăng nhập | Hệ thống quản lý sinh viên");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 560));
        setSize(980, 620);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(UITheme.SURFACE);
        root.add(taoBangGioiThieu());
        root.add(taoBangDangNhap());
        setContentPane(root);

        getRootPane().setDefaultButton(btnLogin);
        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private JPanel taoBangGioiThieu() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, UITheme.NAVY,
                        getWidth(), getHeight(), new Color(42, 91, 158)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(getWidth() - 190, getHeight() - 210, 330, 330);
                g2.fillOval(getWidth() - 110, -120, 230, 230);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(54, 48, 42, 42));

        JLabel brand = new JLabel("CAMPUS MANAGER");
        brand.setFont(new Font(UITheme.FONT, Font.BOLD, 27));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandSub = new JLabel("STUDENT MANAGEMENT SYSTEM");
        brandSub.setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
        brandSub.setForeground(new Color(196, 215, 244));
        brandSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(brand);
        panel.add(Box.createVerticalStrut(8));
        panel.add(brandSub);
        panel.add(Box.createVerticalGlue());

        JLabel welcome = new JLabel("Quản lý sinh viên");
        welcome.setFont(new Font(UITheme.FONT, Font.BOLD, 30));
        welcome.setForeground(Color.WHITE);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html>Một nơi tập trung để quản lý thông tin<br>"
                + "sinh viên, khoa, giảng viên, môn học<br>và kết quả học tập.</html>");
        desc.setFont(new Font(UITheme.FONT, Font.PLAIN, 15));
        desc.setForeground(new Color(224, 234, 248));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(welcome);
        panel.add(Box.createVerticalStrut(16));
        panel.add(desc);
        panel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("© HỆ THỐNG QUẢN LÝ ĐÀO TẠO");
        footer.setFont(new Font(UITheme.FONT, Font.PLAIN, 10));
        footer.setForeground(new Color(181, 202, 231));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(footer);
        return panel;
    }

    private JPanel taoBangDangNhap() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UITheme.SURFACE);
        outer.setBorder(new EmptyBorder(36, 48, 36, 48));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("CỔNG THÔNG TIN NỘI BỘ");
        eyebrow.setFont(new Font(UITheme.FONT, Font.BOLD, 11));
        eyebrow.setForeground(UITheme.PRIMARY);
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Chào mừng trở lại");
        title.setFont(new Font(UITheme.FONT, Font.BOLD, 28));
        title.setForeground(UITheme.TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Đăng nhập để tiếp tục làm việc");
        subtitle.setFont(new Font(UITheme.FONT, Font.PLAIN, 13));
        subtitle.setForeground(UITheme.MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(eyebrow);
        form.add(Box.createVerticalStrut(12));
        form.add(title);
        form.add(Box.createVerticalStrut(8));
        form.add(subtitle);
        form.add(Box.createVerticalStrut(36));

        addField(form, "TÊN ĐĂNG NHẬP", txtUser);
        form.add(Box.createVerticalStrut(20));
        addField(form, "MẬT KHẨU", txtPass);

        chkHienMatKhau.setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
        chkHienMatKhau.setForeground(UITheme.MUTED);
        chkHienMatKhau.setOpaque(false);
        chkHienMatKhau.setFocusPainted(false);
        chkHienMatKhau.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkHienMatKhau.addActionListener(e -> txtPass.setEchoChar(chkHienMatKhau.isSelected() ? (char) 0 : '•'));
        form.add(Box.createVerticalStrut(10));
        form.add(chkHienMatKhau);

        lblStatus.setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
        lblStatus.setForeground(UITheme.DANGER);
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(Box.createVerticalStrut(12));
        form.add(lblStatus);

        UITheme.styleButton(btnLogin, "primary");
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btnLogin.setPreferredSize(new Dimension(340, 46));
        btnLogin.addActionListener(e -> dangNhap());
        form.add(Box.createVerticalStrut(8));
        form.add(btnLogin);

        JLabel hint = new JLabel("Nhấn Enter để đăng nhập · Esc để thoát");
        hint.setFont(new Font(UITheme.FONT, Font.PLAIN, 11));
        hint.setForeground(UITheme.MUTED);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(Box.createVerticalStrut(22));
        form.add(hint);

        outer.add(form, new GridBagConstraints());
        return outer;
    }

    private void addField(JPanel form, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(UITheme.FONT, Font.BOLD, 11));
        label.setForeground(UITheme.MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        UITheme.styleTextField(field);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(label);
        form.add(Box.createVerticalStrut(8));
        form.add(field);
    }

    private void dangNhap() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            if (user.isEmpty()) txtUser.requestFocus(); else txtPass.requestFocus();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("ĐANG KIỂM TRA...");
        lblStatus.setForeground(UITheme.MUTED);
        lblStatus.setText("Đang xác thực thông tin...");

        SwingWorker<TaiKhoan, Void> worker = new SwingWorker<>() {
            @Override
            protected TaiKhoan doInBackground() throws Exception {
                return taiKhoanDAO.dangNhap(user, pass);
            }

            @Override
            protected void done() {
                btnLogin.setEnabled(true);
                btnLogin.setText("ĐĂNG NHẬP HỆ THỐNG");
                try {
                    TaiKhoan tk = get();
                    if (tk == null) {
                        lblStatus.setForeground(UITheme.DANGER);
                        lblStatus.setText("Sai thông tin hoặc tài khoản đã bị khóa.");
                        txtPass.setText("");
                        txtPass.requestFocus();
                        return;
                    }
                    new MainFrame(tk).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Throwable root = ex.getCause() == null ? ex : ex.getCause();
                    lblStatus.setForeground(UITheme.DANGER);
                    lblStatus.setText("Không thể đăng nhập. Hãy kiểm tra kết nối cơ sở dữ liệu.");
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Không thể kết nối hoặc truy vấn cơ sở dữ liệu:\n" + root.getMessage(),
                            "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}
