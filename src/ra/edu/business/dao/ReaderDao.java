package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ReaderDao {
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS reader (
                id INT PRIMARY KEY AUTO_INCREMENT,
                username VARCHAR(100),
                sex BOOLEAN,
                birthdate DATE,
                phone INT,
                email VARCHAR(100)
            );
        """;
        try (Connection conn = DatabaseConnect.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng Reader đã được tạo.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
