package dao;

import model.User;
import java.sql.*;

public class UserDAO {
    Connection conn = DBConnection.getConnection();

    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(username, password, rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}