package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmtSetter)  {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmtSetter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Object> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmtSetter.values(pstmt);

            List<Object> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rowMapper.mapRow(rs));
                }

                return list;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmtSetter.values(pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                Object object = null;
                if (rs.next()) {
                    object = rowMapper.mapRow(rs);
                }

                return object;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
