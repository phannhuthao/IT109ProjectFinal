package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Borrow;
import ra.edu.business.entity.BorrowDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<BorrowDetails> getBorrowDetails() {
        List<BorrowDetails> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM borrow_details";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                BorrowDetails borrowDetails = new BorrowDetails();
                list.add(borrowDetails);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất chi tiết phiếu mượn: " + e.getMessage());
        }
        return list;
    }

    public static boolean addBorrowDetails(BorrowDetails details) {
        String sql = "INSERT INTO borrow_details (borrow_id, book_id, quantity) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, details.getBorrowId());
            ps.setInt(2, details.getBookId());
            ps.setInt(3, details.getQuantity());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm chi tiết phiếu mượn: " + e.getMessage());
            return false;
        }
    }



}
