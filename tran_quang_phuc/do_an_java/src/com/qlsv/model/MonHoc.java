package com.qlsv.model;

public class MonHoc {
    private final String maMH, tenMH, maGV, tenGV;
    private final int soTinChi;

    public MonHoc(String maMH, String tenMH, int soTinChi, String maGV, String tenGV) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.maGV = maGV;
        this.tenGV = tenGV;
    }

    public String getMaMH() { return maMH; }
    public String getTenMH() { return tenMH; }
    public int getSoTinChi() { return soTinChi; }
    public String getMaGV() { return maGV; }
    public String getTenGV() { return tenGV; }

    @Override
    public String toString() { return maMH + " - " + tenMH; }
}