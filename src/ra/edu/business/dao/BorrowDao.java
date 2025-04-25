package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BorrowDao {
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS borrow (
                id INT PRIMARY KEY AUTO_INCREMENT,
                reader_id INT,
                borrow_date DATE,
                return_date DATE,
                status VARCHAR(50),
                FOREIGN KEY (reader_id) REFERENCES reader(id)
            );
        """;
        try (Connection conn = DatabaseConnect.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng Borrow đã được tạo");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
