package com.qlsv.model;

public class Khoa {
    private final String maKhoa, tenKhoa, dienThoai;

    public Khoa(String maKhoa, String tenKhoa, String dienThoai) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.dienThoai = dienThoai;
    }

    public String getMaKhoa() { return maKhoa; }
    public String getTenKhoa() { return tenKhoa; }
    public String getDienThoai() { return dienThoai; }

    @Override
    public String toString() { return maKhoa + " - " + tenKhoa; }
}