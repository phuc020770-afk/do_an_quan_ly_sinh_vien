package com.qlsv.dao;

import com.qlsv.model.Khoa;

import java.sql.SQLException;
import java.util.List;

public class KhoaDAO {
    private static final String SELECT = "SELECT MaKhoa, TenKhoa, DienThoai FROM Khoa ";
    private static final DaoUtil.RowMapper<Khoa> MAP =
            rs -> new Khoa(rs.getString("MaKhoa"), rs.getString("TenKhoa"), rs.getString("DienThoai"));

    public List<Khoa> getAll() throws SQLException {
        return DaoUtil.query(SELECT + "ORDER BY MaKhoa", MAP);
    }

    public List<Khoa> search(String kw) throws SQLException {
        String like = "%" + kw + "%";
        return DaoUtil.query(SELECT + "WHERE MaKhoa LIKE ? OR TenKhoa LIKE ? OR DienThoai LIKE ? ORDER BY MaKhoa",
                MAP, like, like, like);
    }

    public int insert(Khoa k) throws SQLException {
        return DaoUtil.update("INSERT INTO Khoa (MaKhoa, TenKhoa, DienThoai) VALUES (?, ?, ?)",
                k.getMaKhoa(), k.getTenKhoa(), k.getDienThoai());
    }

    public int update(Khoa k) throws SQLException {
        return DaoUtil.update("UPDATE Khoa SET TenKhoa = ?, DienThoai = ? WHERE MaKhoa = ?",
                k.getTenKhoa(), k.getDienThoai(), k.getMaKhoa());
    }

    public int delete(String maKhoa) throws SQLException {
        return DaoUtil.update("DELETE FROM Khoa WHERE MaKhoa = ?", maKhoa);
    }
}