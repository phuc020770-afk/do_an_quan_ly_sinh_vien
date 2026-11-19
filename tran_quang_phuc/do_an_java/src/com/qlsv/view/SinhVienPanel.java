package com.qlsv.view;

import com.qlsv.dao.KhoaDAO;
import com.qlsv.dao.SinhVienDAO;
import com.qlsv.model.Khoa;
import com.qlsv.model.SinhVien;

import javax.swing.*;
import java.awt.GridLayout;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class SinhVienPanel extends CrudPanel<SinhVien> {
    private static final DateTimeFormatter DATE =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final String KHONG_CHON = "(Không chọn)";

    private final SinhVienDAO dao = new SinhVienDAO();
    private final KhoaDAO khoaDao = new KhoaDAO();
    private final JTextField txtMa = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtNgaySinh = new JTextField();
    private final JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{KHONG_CHON, "Nam", "Nữ", "Khác"});
    private final JTextField txtDiaChi = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JComboBox<Khoa> cboKhoa = new JComboBox<>();

    public SinhVienPanel() {
        super("Thông tin sinh viên", "Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Email", "Khoa", "Điểm TB");
        khoiTao();
    }

    @Override
    protected JPanel taoForm() {
        txtNgaySinh.setToolTipText("Định dạng dd/MM/yyyy, ví dụ 15/03/2006 (có thể để trống)");
        JPanel p = new JPanel(new GridLayout(4, 4, 12, 8));
        p.add(new JLabel("Mã SV:"));                  p.add(txtMa);
        p.add(new JLabel("Họ tên:"));                 p.add(txtHoTen);
        p.add(new JLabel("Ngày sinh (dd/MM/yyyy):")); p.add(txtNgaySinh);
        p.add(new JLabel("Giới tính:"));              p.add(cboGioiTinh);
        p.add(new JLabel("Địa chỉ:"));                p.add(txtDiaChi);
        p.add(new JLabel("Email:"));                  p.add(txtEmail);
        p.add(new JLabel("Khoa:"));                   p.add(cboKhoa);
        p.add(new JLabel());                          p.add(new JLabel());
        return p;
    }

    @Override
    protected void taiDanhMuc() {
        try {
            cboKhoa.removeAllItems();
            for (Khoa k : khoaDao.getAll()) cboKhoa.addItem(k);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    @Override
    protected List<SinhVien> layDuLieu(String kw) throws SQLException {
        return kw.isEmpty() ? dao.getAll() : dao.search(kw);
    }

    @Override
    protected Object[] toRow(SinhVien s) {
        return new Object[]{s.getMaSV(), s.getHoTen(), s.getNgaySinh() == null ? "" : s.getNgaySinh().format(DATE),
                nz(s.getGioiTinh()), nz(s.getDiaChi()), s.getEmail(), s.getTenKhoa(),
                s.getDiemTB() == null ? "" : s.getDiemTB().setScale(2, RoundingMode.HALF_UP).toPlainString()};
    }

    @Override
    protected void doVaoForm(SinhVien s) {
        txtMa.setText(s.getMaSV());
        txtHoTen.setText(s.getHoTen());
        txtNgaySinh.setText(s.getNgaySinh() == null ? "" : s.getNgaySinh().format(DATE));
        cboGioiTinh.setSelectedItem(s.getGioiTinh() == null ? KHONG_CHON : s.getGioiTinh());
        txtDiaChi.setText(nz(s.getDiaChi()));
        txtEmail.setText(nz(s.getEmail()));
        chon(cboKhoa, k -> k.getMaKhoa().equals(s.getMaKhoa()));
    }

    @Override
    protected void xoaForm() {
        txtMa.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtEmail.setText("");
        if (cboKhoa.getItemCount() > 0) cboKhoa.setSelectedIndex(0);
    }

    @Override
    protected void khoaKhoaChinh(boolean khoa) { txtMa.setEditable(!khoa); }

    @Override
    protected SinhVien docForm() {
        String ma = txtMa.getText().trim(), hoTen = txtHoTen.getText().trim(), ns = txtNgaySinh.getText().trim();
        String diaChi = txtDiaChi.getText().trim(), email = txtEmail.getText().trim();
        Khoa khoa = (Khoa) cboKhoa.getSelectedItem();

        if (!ma.matches("[A-Za-z0-9_\\-]{1,15}")) {
            canhBao("Mã SV bắt buộc: chữ không dấu hoặc số, tối đa 15 ký tự!");
            return null;
        }
        if (hoTen.isEmpty() || hoTen.length() > 100) {
            canhBao("Họ tên bắt buộc, tối đa 100 ký tự!");
            return null;
        }
        LocalDate ngaySinh = null;
        if (!ns.isEmpty()) {
            try {
                ngaySinh = LocalDate.parse(ns, DATE);
            } catch (DateTimeParseException ex) {
                canhBao("Ngày sinh không hợp lệ! Nhập theo dd/MM/yyyy, ví dụ 15/03/2006.");
                return null;
            }
            if (ngaySinh.isAfter(LocalDate.now())) {
                canhBao("Ngày sinh không được ở tương lai!");
                return null;
            }
        }
        if (diaChi.length() > 200) {
            canhBao("Địa chỉ tối đa 200 ký tự!");
            return null;
        }
        // Cột Email của CSDL là UNIQUE: SQL Server chỉ cho đúng 1 dòng để trống, nên bắt buộc nhập email
        if (email.length() > 100 || !email.matches("[\\w.+\\-]+@[\\w\\-]+(\\.[\\w\\-]+)+")) {
            canhBao("Email bắt buộc và phải đúng định dạng (ví dụ an@example.com)!");
            return null;
        }
        if (khoa == null) {
            canhBao("Chưa có khoa nào. Hãy thêm khoa trước!");
            return null;
        }
        String gt = (String) cboGioiTinh.getSelectedItem();
        return new SinhVien(ma, hoTen, ngaySinh, KHONG_CHON.equals(gt) ? null : gt, hoacNull(diaChi), email,
                khoa.getMaKhoa(), khoa.getTenKhoa(), null);
    }

    @Override
    protected void themMoi(SinhVien s) throws SQLException { dao.insert(s); }

    @Override
    protected int capNhat(SinhVien s) throws SQLException { return dao.update(s); }

    @Override
    protected int xoaBanGhi(SinhVien s) throws SQLException { return dao.delete(s.getMaSV()); }
}