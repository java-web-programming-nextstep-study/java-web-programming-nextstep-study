package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateJdbcTemplate {

    public void update(User user) throws SQLException {
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

    String createQueryForUpdate() {
        return null;
    }

    void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
    }
}
