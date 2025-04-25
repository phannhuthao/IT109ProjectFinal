package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BorrowDetailsDao {
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS borrow_details (
                borrow_id INT,
                book_id INT,
                quantity INT,
                PRIMARY KEY (borrow_id, book_id),
                FOREIGN KEY (borrow_id) REFERENCES borrow(id),
                FOREIGN KEY (book_id) REFERENCES book(id)
            );
        """;
        try (Connection conn = DatabaseConnect.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng BorrowDetails đã được tạo");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
