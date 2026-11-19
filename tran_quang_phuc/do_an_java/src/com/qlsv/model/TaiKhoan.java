package com.qlsv.model;

public class TaiKhoan {
    private final String tenDangNhap, hoTen, vaiTro;

    public TaiKhoan(String tenDangNhap, String hoTen, String vaiTro) {
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public String getHoTen() { return hoTen; }
    public String getVaiTro() { return vaiTro; }
}