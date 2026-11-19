package com.qlsv.dao;

import com.qlsv.model.TaiKhoan;

import java.sql.SQLException;
import java.util.List;

public class TaiKhoanDAO {

    /** Trả về TaiKhoan nếu đúng thông tin và tài khoản đang hoạt động, ngược lại trả về null. */
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) throws SQLException {
        List<TaiKhoan> list = DaoUtil.query(
                "SELECT TenDangNhap, HoTen, VaiTro FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1",
                rs -> new TaiKhoan(rs.getString("TenDangNhap"), rs.getString("HoTen"), rs.getString("VaiTro")),
                tenDangNhap, matKhau);
        return list.isEmpty() ? null : list.get(0);
    }
}