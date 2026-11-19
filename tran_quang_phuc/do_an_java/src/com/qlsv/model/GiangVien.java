package com.qlsv.model;

public class GiangVien {
    private final String maGV, hoTenGV, email, maKhoa, tenKhoa;

    public GiangVien(String maGV, String hoTenGV, String email, String maKhoa, String tenKhoa) {
        this.maGV = maGV;
        this.hoTenGV = hoTenGV;
        this.email = email;
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }

    public String getMaGV() { return maGV; }
    public String getHoTenGV() { return hoTenGV; }
    public String getEmail() { return email; }
    public String getMaKhoa() { return maKhoa; }
    public String getTenKhoa() { return tenKhoa; }

    @Override
    public String toString() { return maGV == null ? hoTenGV : maGV + " - " + hoTenGV; }
}