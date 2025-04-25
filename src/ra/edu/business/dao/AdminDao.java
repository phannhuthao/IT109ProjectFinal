package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDao {
    public static boolean checkLogin(String username, String password) {
        try (Connection conn = DatabaseConnect.getConnection()) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true nếu tồn tại
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
