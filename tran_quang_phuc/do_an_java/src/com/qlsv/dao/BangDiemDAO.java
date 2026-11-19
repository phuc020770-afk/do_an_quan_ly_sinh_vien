package com.qlsv.dao;

import com.qlsv.model.BangDiem;

import java.sql.SQLException;
import java.util.List;

public class BangDiemDAO {
    private static final String SELECT =
            "SELECT bd.MaSV, sv.HoTen, bd.MaMH, mh.TenMH, bd.HocKy, bd.NamHoc, bd.DiemQuaTrinh, bd.DiemThi, bd.DiemTongKet "
          + "FROM BangDiem bd JOIN SinhVien sv ON bd.MaSV = sv.MaSV JOIN MonHoc mh ON bd.MaMH = mh.MaMH ";
    private static final String ORDER = "ORDER BY bd.NamHoc, bd.HocKy, bd.MaSV, bd.MaMH";
    private static final DaoUtil.RowMapper<BangDiem> MAP = rs -> new BangDiem(rs.getString("MaSV"),
            rs.getString("HoTen"), rs.getString("MaMH"), rs.getString("TenMH"), rs.getInt("HocKy"),
            rs.getString("NamHoc"), rs.getBigDecimal("DiemQuaTrinh"), rs.getBigDecimal("DiemThi"),
            rs.getBigDecimal("DiemTongKet"));

    public List<BangDiem> getAll() throws SQLException {
        return DaoUtil.query(SELECT + ORDER, MAP);
    }

    public List<BangDiem> search(String kw) throws SQLException {
        String like = "%" + kw + "%";
        return DaoUtil.query(SELECT + "WHERE bd.MaSV LIKE ? OR sv.HoTen LIKE ? OR bd.MaMH LIKE ? OR mh.TenMH LIKE ? "
                + "OR bd.NamHoc LIKE ? " + ORDER, MAP, like, like, like, like, like);
    }

    public int insert(BangDiem b) throws SQLException {
        return DaoUtil.update("INSERT INTO BangDiem (MaSV, MaMH, HocKy, NamHoc, DiemQuaTrinh, DiemThi, DiemTongKet) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                b.getMaSV(), b.getMaMH(), b.getHocKy(), b.getNamHoc(),
                b.getDiemQuaTrinh(), b.getDiemThi(), b.getDiemTongKet());
    }

    /** Khóa chính gồm (MaSV, MaMH, HocKy, NamHoc) nên chỉ sửa 3 cột điểm. */
    public int update(BangDiem b) throws SQLException {
        return DaoUtil.update("UPDATE BangDiem SET DiemQuaTrinh = ?, DiemThi = ?, DiemTongKet = ? "
                + "WHERE MaSV = ? AND MaMH = ? AND HocKy = ? AND NamHoc = ?",
                b.getDiemQuaTrinh(), b.getDiemThi(), b.getDiemTongKet(),
                b.getMaSV(), b.getMaMH(), b.getHocKy(), b.getNamHoc());
    }

    public int delete(BangDiem b) throws SQLException {
        return DaoUtil.update("DELETE FROM BangDiem WHERE MaSV = ? AND MaMH = ? AND HocKy = ? AND NamHoc = ?",
                b.getMaSV(), b.getMaMH(), b.getHocKy(), b.getNamHoc());
    }
}