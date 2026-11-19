package com.qlsv.dao;

import com.qlsv.model.SinhVien;

import java.sql.SQLException;
import java.util.List;

public class SinhVienDAO {
    // Điểm trung bình tính bằng AVG + LEFT JOIN (sinh viên chưa có điểm vẫn hiện, DiemTB = null)
    private static final String SELECT =
            "SELECT sv.MaSV, sv.HoTen, sv.NgaySinh, sv.GioiTinh, sv.DiaChi, sv.Email, sv.MaKhoa, k.TenKhoa, "
          + "AVG(bd.DiemTongKet) AS DiemTB "
          + "FROM SinhVien sv JOIN Khoa k ON sv.MaKhoa = k.MaKhoa "
          + "LEFT JOIN BangDiem bd ON bd.MaSV = sv.MaSV ";
    private static final String GROUP =
            "GROUP BY sv.MaSV, sv.HoTen, sv.NgaySinh, sv.GioiTinh, sv.DiaChi, sv.Email, sv.MaKhoa, k.TenKhoa "
          + "ORDER BY sv.MaSV";
    private static final DaoUtil.RowMapper<SinhVien> MAP = rs -> new SinhVien(
            rs.getString("MaSV"), rs.getString("HoTen"), DaoUtil.ngay(rs, "NgaySinh"), rs.getString("GioiTinh"),
            rs.getString("DiaChi"), rs.getString("Email"), rs.getString("MaKhoa"), rs.getString("TenKhoa"),
            rs.getBigDecimal("DiemTB"));

    public List<SinhVien> getAll() throws SQLException {
        return DaoUtil.query(SELECT + GROUP, MAP);
    }

    public List<SinhVien> search(String kw) throws SQLException {
        String like = "%" + kw + "%";
        return DaoUtil.query(SELECT
                + "WHERE sv.MaSV LIKE ? OR sv.HoTen LIKE ? OR sv.Email LIKE ? OR sv.DiaChi LIKE ? OR k.TenKhoa LIKE ? "
                + GROUP, MAP, like, like, like, like, like);
    }

    public int insert(SinhVien s) throws SQLException {
        return DaoUtil.update("INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, Email, MaKhoa) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                s.getMaSV(), s.getHoTen(), s.getNgaySinh(), s.getGioiTinh(), s.getDiaChi(), s.getEmail(), s.getMaKhoa());
    }

    public int update(SinhVien s) throws SQLException {
        return DaoUtil.update("UPDATE SinhVien SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, Email = ?, "
                + "MaKhoa = ? WHERE MaSV = ?",
                s.getHoTen(), s.getNgaySinh(), s.getGioiTinh(), s.getDiaChi(), s.getEmail(), s.getMaKhoa(), s.getMaSV());
    }

    public int delete(String maSV) throws SQLException {
        return DaoUtil.update("DELETE FROM SinhVien WHERE MaSV = ?", maSV);
    }
}