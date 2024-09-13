package next.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        PreparedStatementSetter setter = (pstmt) -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };

        jdbcTemplate.update(sql, setter);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate updateJdbcTemplate = new JdbcTemplate() {};

        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE userId = ?";
        PreparedStatementSetter setter = (pstmt) -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserId());
        };

        updateJdbcTemplate.update(sql, setter);
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT * FROM USERS";
        RowMapper rowMapper = (rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        PreparedStatementSetter setter = (pstmt) -> {};

        List<Object> result = jdbcTemplate.query(sql, setter, rowMapper);

        return result.stream()
            .map(r -> (User) r)
            .collect(Collectors.toList());
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId= ?";
        RowMapper rowMapper = (rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
            rs.getString("email"));
        PreparedStatementSetter setter = (pstmt) -> pstmt.setString(1, userId);

        return (User) jdbcTemplate.queryForObject(sql, setter, rowMapper);
    }

}
