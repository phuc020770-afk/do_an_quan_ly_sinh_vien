package com.qlsv.dao;

import com.qlsv.model.GiangVien;

import java.sql.SQLException;
import java.util.List;

public class GiangVienDAO {
    private static final String SELECT =
            "SELECT gv.MaGV, gv.HoTenGV, gv.Email, gv.MaKhoa, k.TenKhoa "
          + "FROM GiangVien gv JOIN Khoa k ON gv.MaKhoa = k.MaKhoa ";
    private static final DaoUtil.RowMapper<GiangVien> MAP = rs -> new GiangVien(rs.getString("MaGV"),
            rs.getString("HoTenGV"), rs.getString("Email"), rs.getString("MaKhoa"), rs.getString("TenKhoa"));

    public List<GiangVien> getAll() throws SQLException {
        return DaoUtil.query(SELECT + "ORDER BY gv.MaGV", MAP);
    }

    public List<GiangVien> search(String kw) throws SQLException {
        String like = "%" + kw + "%";
        return DaoUtil.query(SELECT + "WHERE gv.MaGV LIKE ? OR gv.HoTenGV LIKE ? OR gv.Email LIKE ? OR k.TenKhoa LIKE ? "
                + "ORDER BY gv.MaGV", MAP, like, like, like, like);
    }

    public int insert(GiangVien g) throws SQLException {
        return DaoUtil.update("INSERT INTO GiangVien (MaGV, HoTenGV, Email, MaKhoa) VALUES (?, ?, ?, ?)",
                g.getMaGV(), g.getHoTenGV(), g.getEmail(), g.getMaKhoa());
    }

    public int update(GiangVien g) throws SQLException {
        return DaoUtil.update("UPDATE GiangVien SET HoTenGV = ?, Email = ?, MaKhoa = ? WHERE MaGV = ?",
                g.getHoTenGV(), g.getEmail(), g.getMaKhoa(), g.getMaGV());
    }

    public int delete(String maGV) throws SQLException {
        return DaoUtil.update("DELETE FROM GiangVien WHERE MaGV = ?", maGV);
    }
}