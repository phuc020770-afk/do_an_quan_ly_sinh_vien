package com.qlsv.view;

import com.qlsv.dao.GiangVienDAO;
import com.qlsv.dao.KhoaDAO;
import com.qlsv.model.GiangVien;
import com.qlsv.model.Khoa;

import javax.swing.*;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

public class GiangVienPanel extends CrudPanel<GiangVien> {
    private final GiangVienDAO dao = new GiangVienDAO();
    private final KhoaDAO khoaDao = new KhoaDAO();
    private final JTextField txtMa = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JComboBox<Khoa> cboKhoa = new JComboBox<>();

    public GiangVienPanel() {
        super("Thông tin giảng viên", "Mã GV", "Họ tên", "Email", "Khoa");
        khoiTao();
    }

    @Override
    protected JPanel taoForm() {
        JPanel p = new JPanel(new GridLayout(2, 4, 12, 8));
        p.add(new JLabel("Mã GV:"));  p.add(txtMa);
        p.add(new JLabel("Họ tên:")); p.add(txtHoTen);
        p.add(new JLabel("Email:"));  p.add(txtEmail);
        p.add(new JLabel("Khoa:"));   p.add(cboKhoa);
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
    protected List<GiangVien> layDuLieu(String kw) throws SQLException {
        return kw.isEmpty() ? dao.getAll() : dao.search(kw);
    }

    @Override
    protected Object[] toRow(GiangVien g) {
        return new Object[]{g.getMaGV(), g.getHoTenGV(), nz(g.getEmail()), g.getTenKhoa()};
    }

    @Override
    protected void doVaoForm(GiangVien g) {
        txtMa.setText(g.getMaGV());
        txtHoTen.setText(g.getHoTenGV());
        txtEmail.setText(nz(g.getEmail()));
        chon(cboKhoa, k -> k.getMaKhoa().equals(g.getMaKhoa()));
    }

    @Override
    protected void xoaForm() {
        txtMa.setText("");
        txtHoTen.setText("");
        txtEmail.setText("");
        if (cboKhoa.getItemCount() > 0) cboKhoa.setSelectedIndex(0);
    }

    @Override
    protected void khoaKhoaChinh(boolean khoa) { txtMa.setEditable(!khoa); }

    @Override
    protected GiangVien docForm() {
        String ma = txtMa.getText().trim(), hoTen = txtHoTen.getText().trim(), email = txtEmail.getText().trim();
        Khoa khoa = (Khoa) cboKhoa.getSelectedItem();
        if (!ma.matches("[A-Za-z0-9_\\-]{1,15}")) {
            canhBao("Mã GV bắt buộc: chữ không dấu hoặc số, tối đa 15 ký tự!");
            return null;
        }
        if (hoTen.isEmpty() || hoTen.length() > 100) {
            canhBao("Họ tên bắt buộc, tối đa 100 ký tự!");
            return null;
        }
        // Email là UNIQUE nên SQL Server chỉ cho 1 dòng để trống -> bắt buộc nhập
        if (email.length() > 100 || !email.matches("[\\w.+\\-]+@[\\w\\-]+(\\.[\\w\\-]+)+")) {
            canhBao("Email bắt buộc và phải đúng định dạng (ví dụ tuan@example.com)!");
            return null;
        }
        if (khoa == null) {
            canhBao("Chưa có khoa nào. Hãy thêm khoa trước!");
            return null;
        }
        return new GiangVien(ma, hoTen, email, khoa.getMaKhoa(), khoa.getTenKhoa());
    }

    @Override
    protected void themMoi(GiangVien g) throws SQLException { dao.insert(g); }

    @Override
    protected int capNhat(GiangVien g) throws SQLException { return dao.update(g); }

    @Override
    protected int xoaBanGhi(GiangVien g) throws SQLException { return dao.delete(g.getMaGV()); }
}