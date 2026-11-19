package com.qlsv.view;

import com.qlsv.dao.GiangVienDAO;
import com.qlsv.dao.MonHocDAO;
import com.qlsv.model.GiangVien;
import com.qlsv.model.MonHoc;

import javax.swing.*;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MonHocPanel extends CrudPanel<MonHoc> {
    private final MonHocDAO dao = new MonHocDAO();
    private final GiangVienDAO gvDao = new GiangVienDAO();
    private final JTextField txtMa = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtTinChi = new JTextField();
    private final JComboBox<GiangVien> cboGV = new JComboBox<>();

    public MonHocPanel() {
        super("Thông tin môn học", "Mã môn", "Tên môn", "Số tín chỉ", "Giảng viên");
        khoiTao();
    }

    @Override
    protected JPanel taoForm() {
        JPanel p = new JPanel(new GridLayout(2, 4, 12, 8));
        p.add(new JLabel("Mã môn:"));     p.add(txtMa);
        p.add(new JLabel("Tên môn:"));    p.add(txtTen);
        p.add(new JLabel("Số tín chỉ:")); p.add(txtTinChi);
        p.add(new JLabel("Giảng viên:")); p.add(cboGV);
        return p;
    }

    @Override
    protected void taiDanhMuc() {
        try {
            cboGV.removeAllItems();
            cboGV.addItem(new GiangVien(null, "(Chưa phân công)", null, null, null)); // MaGV được phép null
            for (GiangVien g : gvDao.getAll()) cboGV.addItem(g);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    @Override
    protected List<MonHoc> layDuLieu(String kw) throws SQLException {
        return kw.isEmpty() ? dao.getAll() : dao.search(kw);
    }

    @Override
    protected Object[] toRow(MonHoc m) {
        return new Object[]{m.getMaMH(), m.getTenMH(), m.getSoTinChi(),
                m.getTenGV() == null ? "(Chưa phân công)" : m.getTenGV()};
    }

    @Override
    protected void doVaoForm(MonHoc m) {
        txtMa.setText(m.getMaMH());
        txtTen.setText(m.getTenMH());
        txtTinChi.setText(String.valueOf(m.getSoTinChi()));
        chon(cboGV, g -> Objects.equals(g.getMaGV(), m.getMaGV()));
    }

    @Override
    protected void xoaForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtTinChi.setText("");
        if (cboGV.getItemCount() > 0) cboGV.setSelectedIndex(0);
    }

    @Override
    protected void khoaKhoaChinh(boolean khoa) { txtMa.setEditable(!khoa); }

    @Override
    protected MonHoc docForm() {
        String ma = txtMa.getText().trim(), ten = txtTen.getText().trim();
        if (!ma.matches("[A-Za-z0-9_\\-]{1,15}")) {
            canhBao("Mã môn bắt buộc: chữ không dấu hoặc số, tối đa 15 ký tự!");
            return null;
        }
        if (ten.isEmpty() || ten.length() > 100) {
            canhBao("Tên môn bắt buộc, tối đa 100 ký tự!");
            return null;
        }
        int tinChi;
        try {
            tinChi = Integer.parseInt(txtTinChi.getText().trim());
            if (tinChi < 1 || tinChi > 10) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            canhBao("Số tín chỉ phải là số nguyên từ 1 đến 10!");
            return null;
        }
        GiangVien gv = (GiangVien) cboGV.getSelectedItem();
        return new MonHoc(ma, ten, tinChi, gv == null ? null : gv.getMaGV(), null);
    }

    @Override
    protected void themMoi(MonHoc m) throws SQLException { dao.insert(m); }

    @Override
    protected int capNhat(MonHoc m) throws SQLException { return dao.update(m); }

    @Override
    protected int xoaBanGhi(MonHoc m) throws SQLException { return dao.delete(m.getMaMH()); }
}