package com.qlsv.view;

import com.qlsv.dao.BangDiemDAO;
import com.qlsv.dao.MonHocDAO;
import com.qlsv.dao.SinhVienDAO;
import com.qlsv.model.BangDiem;
import com.qlsv.model.MonHoc;
import com.qlsv.model.SinhVien;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class BangDiemPanel extends CrudPanel<BangDiem> {
    private final BangDiemDAO dao = new BangDiemDAO();
    private final SinhVienDAO svDao = new SinhVienDAO();
    private final MonHocDAO mhDao = new MonHocDAO();
    private final JComboBox<SinhVien> cboSV = new JComboBox<>();
    private final JComboBox<MonHoc> cboMH = new JComboBox<>();
    private final JComboBox<Integer> cboHocKy = new JComboBox<>(new Integer[]{1, 2, 3});
    private final JTextField txtNamHoc = new JTextField();
    private final JTextField txtQT = new JTextField();
    private final JTextField txtThi = new JTextField();
    private final JTextField txtTK = new JTextField();

    public BangDiemPanel() {
        super("Nhập điểm", "Mã SV", "Họ tên", "Môn học", "Học kỳ", "Năm học",
                "Điểm quá trình", "Điểm thi", "Điểm tổng kết");
        khoiTao();
    }

    @Override
    protected JPanel taoForm() {
        txtTK.setEditable(false);
        txtTK.setToolTipText("Tự tính: 40% điểm quá trình + 60% điểm thi");
        txtNamHoc.setToolTipText("Ví dụ 2026-2027");
        khiDoi(txtQT);
        khiDoi(txtThi);

        JPanel p = new JPanel(new GridLayout(4, 4, 12, 8));
        p.add(new JLabel("Sinh viên:"));                p.add(cboSV);
        p.add(new JLabel("Môn học:"));                  p.add(cboMH);
        p.add(new JLabel("Học kỳ:"));                   p.add(cboHocKy);
        p.add(new JLabel("Năm học (vd 2026-2027):"));   p.add(txtNamHoc);
        p.add(new JLabel("Điểm quá trình (0-10):"));    p.add(txtQT);
        p.add(new JLabel("Điểm thi (0-10):"));          p.add(txtThi);
        p.add(new JLabel("Điểm tổng kết (tự tính):"));  p.add(txtTK);
        p.add(new JLabel());                            p.add(new JLabel());
        return p;
    }

    @Override
    protected void taiDanhMuc() {
        try {
            cboSV.removeAllItems();
            for (SinhVien s : svDao.getAll()) cboSV.addItem(s);
            cboMH.removeAllItems();
            for (MonHoc m : mhDao.getAll()) cboMH.addItem(m);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    @Override
    protected List<BangDiem> layDuLieu(String kw) throws SQLException {
        return kw.isEmpty() ? dao.getAll() : dao.search(kw);
    }

    @Override
    protected Object[] toRow(BangDiem b) {
        return new Object[]{b.getMaSV(), b.getHoTen(), b.getMaMH() + " - " + b.getTenMH(), b.getHocKy(),
                b.getNamHoc(), b.getDiemQuaTrinh().toPlainString(), b.getDiemThi().toPlainString(),
                b.getDiemTongKet().toPlainString()};
    }

    @Override
    protected void doVaoForm(BangDiem b) {
        chon(cboSV, s -> s.getMaSV().equals(b.getMaSV()));
        chon(cboMH, m -> m.getMaMH().equals(b.getMaMH()));
        cboHocKy.setSelectedItem(b.getHocKy());
        txtNamHoc.setText(b.getNamHoc());
        txtQT.setText(b.getDiemQuaTrinh().toPlainString());
        txtThi.setText(b.getDiemThi().toPlainString());
        capNhatTongKet();
    }

    @Override
    protected void xoaForm() {
        if (cboSV.getItemCount() > 0) cboSV.setSelectedIndex(0);
        if (cboMH.getItemCount() > 0) cboMH.setSelectedIndex(0);
        cboHocKy.setSelectedIndex(0);
        txtNamHoc.setText("2026-2027");
        txtQT.setText("");
        txtThi.setText("");
        txtTK.setText("");
    }

    /** Khóa chính của bảng điểm là (MaSV, MaMH, HocKy, NamHoc) nên khóa cả 4 ô khi đang sửa. */
    @Override
    protected void khoaKhoaChinh(boolean khoa) {
        cboSV.setEnabled(!khoa);
        cboMH.setEnabled(!khoa);
        cboHocKy.setEnabled(!khoa);
        txtNamHoc.setEditable(!khoa);
    }

    @Override
    protected BangDiem docForm() {
        SinhVien sv = (SinhVien) cboSV.getSelectedItem();
        MonHoc mh = (MonHoc) cboMH.getSelectedItem();
        String namHoc = txtNamHoc.getText().trim();
        if (sv == null || mh == null) {
            canhBao("Cần có sinh viên và môn học trước khi nhập điểm!");
            return null;
        }
        if (!namHoc.matches("\\d{4}-\\d{4}")
                || Integer.parseInt(namHoc.substring(5)) != Integer.parseInt(namHoc.substring(0, 4)) + 1) {
            canhBao("Năm học phải có dạng 2026-2027 (hai năm liên tiếp)!");
            return null;
        }
        BigDecimal qt = parseDiem(txtQT.getText()), thi = parseDiem(txtThi.getText());
        if (qt == null || thi == null) {
            canhBao("Điểm quá trình và điểm thi phải là số từ 0 đến 10!");
            return null;
        }
        qt = qt.setScale(2, RoundingMode.HALF_UP);
        thi = thi.setScale(2, RoundingMode.HALF_UP);
        return new BangDiem(sv.getMaSV(), sv.getHoTen(), mh.getMaMH(), mh.getTenMH(),
                (Integer) cboHocKy.getSelectedItem(), namHoc, qt, thi, tinhTongKet(qt, thi));
    }

    @Override
    protected void themMoi(BangDiem b) throws SQLException { dao.insert(b); }

    @Override
    protected int capNhat(BangDiem b) throws SQLException { return dao.update(b); }

    @Override
    protected int xoaBanGhi(BangDiem b) throws SQLException { return dao.delete(b); }

    @Override
    protected String moTaLoi(SQLException ex) {
        if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601) {
            return "Sinh viên này đã có điểm môn này trong học kỳ / năm học đã chọn!";
        }
        return super.moTaLoi(ex);
    }

    // ---------- tính điểm tổng kết ----------
    private BigDecimal parseDiem(String s) {
        try {
            BigDecimal d = new BigDecimal(s.trim().replace(',', '.'));
            return d.signum() < 0 || d.compareTo(BigDecimal.TEN) > 0 ? null : d;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /** Công thức suy ra từ dữ liệu mẫu: 40% quá trình + 60% thi (có thể đổi hệ số ở đây). */
    private BigDecimal tinhTongKet(BigDecimal qt, BigDecimal thi) {
        return qt.multiply(new BigDecimal("0.4")).add(thi.multiply(new BigDecimal("0.6")))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void capNhatTongKet() {
        BigDecimal qt = parseDiem(txtQT.getText()), thi = parseDiem(txtThi.getText());
        txtTK.setText(qt == null || thi == null ? "" : tinhTongKet(qt, thi).toPlainString());
    }

    private void khiDoi(JTextField tf) {
        tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { capNhatTongKet(); }
            @Override public void removeUpdate(DocumentEvent e) { capNhatTongKet(); }
            @Override public void changedUpdate(DocumentEvent e) { capNhatTongKet(); }
        });
    }
}