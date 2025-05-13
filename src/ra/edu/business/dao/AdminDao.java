package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class AdminDao {

    public static boolean checkLogin(String username, String password) {

        try (Connection conn = DatabaseConnect.getConnection()) {
            // Sử dụng CallableStatement để gọi stored procedure
            CallableStatement cs = conn.prepareCall("{CALL check_admin_login(?, ?)}");
            cs.setString(1, username);
            cs.setString(2, password);

            try (ResultSet rs = cs.executeQuery()) {
                // true nếu tồn tại admin với tên đăng nhập và mật khẩu
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
