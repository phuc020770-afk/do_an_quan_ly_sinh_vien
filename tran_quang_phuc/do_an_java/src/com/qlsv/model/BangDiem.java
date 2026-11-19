package com.qlsv.model;

import java.math.BigDecimal;

public class BangDiem {
    private final String maSV, hoTen, maMH, tenMH, namHoc;
    private final int hocKy;
    private final BigDecimal diemQuaTrinh, diemThi, diemTongKet;

    public BangDiem(String maSV, String hoTen, String maMH, String tenMH, int hocKy, String namHoc,
                    BigDecimal diemQuaTrinh, BigDecimal diemThi, BigDecimal diemTongKet) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.diemQuaTrinh = diemQuaTrinh;
        this.diemThi = diemThi;
        this.diemTongKet = diemTongKet;
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public String getMaMH() { return maMH; }
    public String getTenMH() { return tenMH; }
    public int getHocKy() { return hocKy; }
    public String getNamHoc() { return namHoc; }
    public BigDecimal getDiemQuaTrinh() { return diemQuaTrinh; }
    public BigDecimal getDiemThi() { return diemThi; }
    public BigDecimal getDiemTongKet() { return diemTongKet; }
}