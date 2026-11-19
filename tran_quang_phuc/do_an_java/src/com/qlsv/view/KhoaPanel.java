package com.qlsv.view;

import com.qlsv.dao.KhoaDAO;
import com.qlsv.model.Khoa;

import javax.swing.*;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

public class KhoaPanel extends CrudPanel<Khoa> {
    private final KhoaDAO dao = new KhoaDAO();
    private final JTextField txtMa = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtDienThoai = new JTextField();

    public KhoaPanel() {
        super("Thông tin khoa", "Mã khoa", "Tên khoa", "Điện thoại");
        khoiTao();
    }

    @Override
    protected JPanel taoForm() {
        JPanel p = new JPanel(new GridLayout(2, 4, 12, 8));
        p.add(new JLabel("Mã khoa:"));    p.add(txtMa);
        p.add(new JLabel("Tên khoa:"));   p.add(txtTen);
        p.add(new JLabel("Điện thoại:")); p.add(txtDienThoai);
        p.add(new JLabel());              p.add(new JLabel());
        return p;
    }

    @Override
    protected List<Khoa> layDuLieu(String kw) throws SQLException {
        return kw.isEmpty() ? dao.getAll() : dao.search(kw);
    }

    @Override
    protected Object[] toRow(Khoa k) {
        return new Object[]{k.getMaKhoa(), k.getTenKhoa(), nz(k.getDienThoai())};
    }

    @Override
    protected void doVaoForm(Khoa k) {
        txtMa.setText(k.getMaKhoa());
        txtTen.setText(k.getTenKhoa());
        txtDienThoai.setText(nz(k.getDienThoai()));
    }

    @Override
    protected void xoaForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtDienThoai.setText("");
    }

    @Override
    protected void khoaKhoaChinh(boolean khoa) { txtMa.setEditable(!khoa); }

    @Override
    protected Khoa docForm() {
        String ma = txtMa.getText().trim(), ten = txtTen.getText().trim(), dt = txtDienThoai.getText().trim();
        if (!ma.matches("[A-Za-z0-9_\\-]{1,10}")) {
            canhBao("Mã khoa bắt buộc: chữ không dấu hoặc số, tối đa 10 ký tự!");
            return null;
        }
        if (ten.isEmpty() || ten.length() > 100) {
            canhBao("Tên khoa bắt buộc, tối đa 100 ký tự!");
            return null;
        }
        if (!dt.isEmpty() && !dt.matches("\\d{1,15}")) {
            canhBao("Điện thoại chỉ gồm chữ số, tối đa 15 ký tự!");
            return null;
        }
        return new Khoa(ma, ten, hoacNull(dt));
    }

    @Override
    protected void themMoi(Khoa k) throws SQLException { dao.insert(k); }

    @Override
    protected int capNhat(Khoa k) throws SQLException { return dao.update(k); }

    @Override
    protected int xoaBanGhi(Khoa k) throws SQLException { return dao.delete(k.getMaKhoa()); }
}