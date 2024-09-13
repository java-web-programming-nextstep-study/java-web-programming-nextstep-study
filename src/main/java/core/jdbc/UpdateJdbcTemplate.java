package core.jdbc;

import next.dao.UserDao;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateJdbcTemplate {

    public void update(User user, UserDao userDao) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            pstmt = con.prepareStatement(sql);
            setValuesForUpdate(user, pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                pstmt.close();
            }
        }
    }

    private String createQueryForUpdate() {
        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE userId = ?";
        return sql;
    }

    private void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getUserId());
    }
}
