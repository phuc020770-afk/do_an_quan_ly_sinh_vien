package com.qlsv.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/** Khung dùng chung cho các màn hình CRUD, giữ nguyên các hàm DAO của từng panel. */
public abstract class CrudPanel<T> extends JPanel {
    private final String tieuDeForm;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField txtTimKiem = new JTextField(18);
    private final JLabel lblSoLuong = new JLabel("0 bản ghi");
    private List<T> data = new ArrayList<>();
    protected T dangChon;

    protected CrudPanel(String tieuDeForm, String... cot) {
        this.tieuDeForm = tieuDeForm;
        this.model = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        this.table = new JTable(model);
    }

    protected abstract JPanel taoForm();
    protected abstract List<T> layDuLieu(String tuKhoa) throws SQLException;
    protected abstract Object[] toRow(T t);
    protected abstract void doVaoForm(T t);
    protected abstract void xoaForm();
    protected abstract void khoaKhoaChinh(boolean khoa);
    protected abstract T docForm();
    protected abstract void themMoi(T t) throws SQLException;
    protected abstract int capNhat(T t) throws SQLException;
    protected abstract int xoaBanGhi(T t) throws SQLException;

    protected void taiDanhMuc() {}

    /** Gọi ở cuối constructor lớp con, sau khi đã khởi tạo đầy đủ các ô nhập. */
    protected final void khoiTao() {
        setLayout(new BorderLayout(0, 14));
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(18, 20, 18, 20));

        JPanel formContent = taoForm();
        UITheme.styleFormTree(formContent);
        formContent.setBorder(new EmptyBorder(5, 2, 2, 2));

        JPanel formCard = UITheme.card(new BorderLayout(0, 12));
        JLabel formTitle = new JLabel(tieuDeForm);
        formTitle.setFont(new Font(UITheme.FONT, Font.BOLD, 16));
        formTitle.setForeground(UITheme.TEXT);
        JLabel formHint = new JLabel("Nhập thông tin vào các trường bên dưới");
        formHint.setFont(new Font(UITheme.FONT, Font.PLAIN, 11));
        formHint.setForeground(UITheme.MUTED);
        JPanel formHeading = new JPanel();
        formHeading.setOpaque(false);
        formHeading.setLayout(new BoxLayout(formHeading, BoxLayout.Y_AXIS));
        formHeading.add(formTitle);
        formHeading.add(Box.createVerticalStrut(3));
        formHeading.add(formHint);
        formCard.add(formHeading, BorderLayout.NORTH);
        formCard.add(formContent, BorderLayout.CENTER);

        JButton btnThem = new JButton("+ Thêm mới");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        JButton btnTim = new JButton("Tìm kiếm");
        UITheme.styleButton(btnThem, "primary");
        UITheme.styleButton(btnSua, "soft");
        UITheme.styleButton(btnXoa, "danger");
        UITheme.styleButton(btnLamMoi, "default");
        UITheme.styleButton(btnTim, "dark");

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnLamMoi.addActionListener(e -> lamMoi());
        btnTim.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());
        UITheme.styleTextField(txtTimKiem);
        txtTimKiem.setPreferredSize(new Dimension(210, 38));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnThem);
        actions.add(btnSua);
        actions.add(btnXoa);
        actions.add(btnLamMoi);

        JPanel search = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        search.setOpaque(false);
        JLabel searchLabel = new JLabel("Tìm nhanh");
        searchLabel.setFont(new Font(UITheme.FONT, Font.BOLD, 12));
        searchLabel.setForeground(UITheme.MUTED);
        search.add(searchLabel);
        search.add(txtTimKiem);
        search.add(btnTim);

        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);
        toolbar.add(actions, BorderLayout.WEST);
        toolbar.add(search, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(0, 13));
        top.setOpaque(false);
        top.add(formCard, BorderLayout.NORTH);
        top.add(toolbar, BorderLayout.SOUTH);

        JPanel tableCard = UITheme.card(new BorderLayout(0, 10));
        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setOpaque(false);
        JLabel tableTitle = new JLabel("DANH SÁCH DỮ LIỆU");
        tableTitle.setFont(new Font(UITheme.FONT, Font.BOLD, 13));
        tableTitle.setForeground(UITheme.TEXT);
        lblSoLuong.setFont(new Font(UITheme.FONT, Font.PLAIN, 11));
        lblSoLuong.setForeground(UITheme.MUTED);
        tableHeading.add(tableTitle, BorderLayout.WEST);
        tableHeading.add(lblSoLuong, BorderLayout.EAST);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(32);
        table.setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
        table.setForeground(UITheme.TEXT);
        table.setBackground(UITheme.SURFACE);
        table.setSelectionBackground(UITheme.SOFT_BLUE);
        table.setSelectionForeground(UITheme.TEXT);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(235, 239, 245));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setFont(new Font(UITheme.FONT, Font.BOLD, 12));
        table.getTableHeader().setForeground(UITheme.TEXT);
        table.getTableHeader().setBackground(new Color(238, 243, 250));
        table.getTableHeader().setBorder(new LineBorder(UITheme.BORDER, 1));
        table.setDefaultRenderer(Object.class, new TableCellRenderer());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) chonDong();
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(UITheme.BORDER, 1, true));
        scroll.getViewport().setBackground(UITheme.SURFACE);

        tableCard.add(tableHeading, BorderLayout.NORTH);
        tableCard.add(scroll, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
    }

    /** Renderer tạo màu xen kẽ cho các dòng và khoảng đệm dễ đọc. */
    private static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            setBorder(new EmptyBorder(0, 10, 0, 10));
            setFont(new Font(UITheme.FONT, Font.PLAIN, 12));
            if (isSelected) {
                c.setBackground(UITheme.SOFT_BLUE);
                c.setForeground(UITheme.TEXT);
            } else {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(249, 251, 254));
                c.setForeground(UITheme.TEXT);
            }
            return c;
        }
    }

    public final void kichHoat() {
        taiDanhMuc();
        lamMoi();
    }

    private void taiLai(String tuKhoa) {
        try {
            data = layDuLieu(tuKhoa);
            if (data == null) data = new ArrayList<>();
            model.setRowCount(0);
            for (T t : data) model.addRow(toRow(t));
            lblSoLuong.setText(data.size() + (data.size() == 1 ? " bản ghi" : " bản ghi"));
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void chonDong() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) return;
        int row = table.convertRowIndexToModel(viewRow);
        if (row < 0 || row >= data.size()) return;
        dangChon = data.get(row);
        doVaoForm(dangChon);
        khoaKhoaChinh(true);
    }

    private void them() {
        T t = docForm();
        if (t == null) return;
        try {
            themMoi(t);
            JOptionPane.showMessageDialog(this, "Thêm dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            lamMoi();
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void sua() {
        if (dangChon == null) {
            canhBao("Vui lòng chọn một dòng trong bảng để sửa!");
            return;
        }
        T t = docForm();
        if (t == null) return;
        try {
            if (capNhat(t) > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                lamMoi();
            } else {
                canhBao("Không tìm thấy bản ghi cần sửa!");
            }
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void xoa() {
        if (dangChon == null) {
            canhBao("Vui lòng chọn một dòng trong bảng để xóa!");
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa bản ghi đã chọn?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) return;
        try {
            if (xoaBanGhi(dangChon) > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa bản ghi!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                lamMoi();
            } else {
                canhBao("Không tìm thấy bản ghi cần xóa!");
            }
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void timKiem() {
        String kw = txtTimKiem.getText().trim();
        dangChon = null;
        khoaKhoaChinh(false);
        taiLai(kw);
        table.clearSelection();
        if (!kw.isEmpty() && data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp.", "Tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void lamMoi() {
        dangChon = null;
        xoaForm();
        khoaKhoaChinh(false);
        txtTimKiem.setText("");
        taiLai("");
        table.clearSelection();
    }

    protected void canhBao(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    protected void baoLoi(SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, moTaLoi(ex), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    protected String moTaLoi(SQLException ex) {
        String detail = String.valueOf(ex.getMessage());
        switch (ex.getErrorCode()) {
            case 2627:
            case 2601:
                return "Dữ liệu bị trùng (mã, tên hoặc email đã tồn tại)!";
            case 547:
                return detail.contains("DELETE")
                        ? "Không thể xóa: dữ liệu đang được dùng ở bảng khác (ví dụ khoa còn sinh viên, sinh viên đã có điểm...)."
                        : "Dữ liệu không hợp lệ hoặc vi phạm ràng buộc của CSDL.";
            case 208:
                return "Thiếu bảng trong CSDL. Hãy chạy đủ các script SQL!";
            default:
                return "Lỗi CSDL: " + detail;
        }
    }

    protected static String nz(String s) { return s == null ? "" : s; }
    protected static String hoacNull(String s) { return s == null || s.isEmpty() ? null : s; }

    protected static <E> void chon(JComboBox<E> cbo, Predicate<E> khop) {
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (khop.test(cbo.getItemAt(i))) {
                cbo.setSelectedIndex(i);
                return;
            }
        }
    }
}
