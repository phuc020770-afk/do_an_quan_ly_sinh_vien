package com.qlsv.dao;

import com.qlsv.model.MonHoc;

import java.sql.SQLException;
import java.util.List;

public class MonHocDAO {
    // LEFT JOIN: môn chưa phân công giảng viên (MaGV = null) vẫn hiện ra
    private static final String SELECT =
            "SELECT mh.MaMH, mh.TenMH, mh.SoTinChi, mh.MaGV, gv.HoTenGV "
          + "FROM MonHoc mh LEFT JOIN GiangVien gv ON mh.MaGV = gv.MaGV ";
    private static final DaoUtil.RowMapper<MonHoc> MAP = rs -> new MonHoc(rs.getString("MaMH"),
            rs.getString("TenMH"), rs.getInt("SoTinChi"), rs.getString("MaGV"), rs.getString("HoTenGV"));

    public List<MonHoc> getAll() throws SQLException {
        return DaoUtil.query(SELECT + "ORDER BY mh.MaMH", MAP);
    }

    public List<MonHoc> search(String kw) throws SQLException {
        String like = "%" + kw + "%";
        return DaoUtil.query(SELECT + "WHERE mh.MaMH LIKE ? OR mh.TenMH LIKE ? OR gv.HoTenGV LIKE ? ORDER BY mh.MaMH",
                MAP, like, like, like);
    }

    public int insert(MonHoc m) throws SQLException {
        return DaoUtil.update("INSERT INTO MonHoc (MaMH, TenMH, SoTinChi, MaGV) VALUES (?, ?, ?, ?)",
                m.getMaMH(), m.getTenMH(), m.getSoTinChi(), m.getMaGV());
    }

    public int update(MonHoc m) throws SQLException {
        return DaoUtil.update("UPDATE MonHoc SET TenMH = ?, SoTinChi = ?, MaGV = ? WHERE MaMH = ?",
                m.getTenMH(), m.getSoTinChi(), m.getMaGV(), m.getMaMH());
    }

    public int delete(String maMH) throws SQLException {
        return DaoUtil.update("DELETE FROM MonHoc WHERE MaMH = ?", maMH);
    }
}