package com.qlsv.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SinhVien {
    private final String maSV, hoTen, gioiTinh, diaChi, email, maKhoa, tenKhoa;
    private final LocalDate ngaySinh;
    private final BigDecimal diemTB; // điểm trung bình (null nếu chưa có điểm)

    public SinhVien(String maSV, String hoTen, LocalDate ngaySinh, String gioiTinh, String diaChi,
                    String email, String maKhoa, String tenKhoa, BigDecimal diemTB) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.diemTB = diemTB;
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDiaChi() { return diaChi; }
    public String getEmail() { return email; }
    public String getMaKhoa() { return maKhoa; }
    public String getTenKhoa() { return tenKhoa; }
    public BigDecimal getDiemTB() { return diemTB; }

    @Override
    public String toString() { return maSV + " - " + hoTen; }
}