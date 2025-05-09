package ra.edu.business.dao;

import ra.edu.business.config.ColorCode;
import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Borrow;
import ra.edu.business.entity.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static List<Borrow> getBorrow() {
        List<Borrow> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM borrow";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int readerId = rs.getInt("reader_id");
                Date borrowDate = rs.getDate("borrow_date");
                Date returnDate = rs.getDate("return_date");
                String status = rs.getString("status");
                Borrow borrow = new Borrow(id, readerId, borrowDate, returnDate, status);
                list.add(borrow);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất danh sách chứa phiếu mượn: " + e.getMessage());
        }
        return list;
    }

    public static boolean addBorrow(Borrow borrow) {
        String sql = "INSERT INTO borrow (id, reader_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, borrow.getId());
            ps.setInt(2, borrow.getReaderId());
            ps.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            ps.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            ps.setString(5, borrow.getStatus());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm phiếu mượn: " + e.getMessage());
            return false;
        }
    }

    public static boolean returnBook(int borrowId) {
        String sql = "UPDATE borrow SET return_date = ?, status = 'RETURNED' WHERE id = ?";
        try (Connection con = DatabaseConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Lấy ngày hiện tại làm ngày trả
            java.sql.Date returnDate = new java.sql.Date(System.currentTimeMillis());
            ps.setDate(1, returnDate);
            ps.setInt(2, borrowId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật thông tin phiếu mượn: " + e.getMessage());
            return false;
        }
    }

    public static Borrow getBorrowById(int id) {
        Borrow borrow = null;
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM borrow WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int readerId = rs.getInt("reader_id");
                Date borrowDate = rs.getDate("borrow_date");
                Date returnDate = rs.getDate("return_date");
                String status = rs.getString("status");
                borrow = new Borrow(id, readerId, borrowDate, returnDate, status);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi truy xuất thông tin phiếu mượn theo ID: " + e.getMessage() + ColorCode.RESET);
        }
        return borrow;
    }

}
