package com.qlsv.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Hàm dùng chung cho các DAO. Luôn dùng PreparedStatement để chống SQL Injection. */
public final class DaoUtil {
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    private DaoUtil() {}

    public static <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) throws SQLException {
        List<T> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            gan(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapper.map(rs));
            }
        }
        return list;
    }

    public static int update(String sql, Object... params) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            gan(ps, params);
            return ps.executeUpdate();
        }
    }

    public static LocalDate ngay(ResultSet rs, String cot) throws SQLException {
        Date d = rs.getDate(cot);
        return d == null ? null : d.toLocalDate();
    }

    private static void gan(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            if (p == null) ps.setNull(i + 1, Types.VARCHAR);
            else if (p instanceof LocalDate) ps.setDate(i + 1, Date.valueOf((LocalDate) p));
            else ps.setObject(i + 1, p);
        }
    }
}