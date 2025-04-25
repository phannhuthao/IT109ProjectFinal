package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDao {
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS book (
                id INT PRIMARY KEY AUTO_INCREMENT,
                title VARCHAR(255),
                author VARCHAR(255),
                publish_year VARCHAR(10),
                quantity INT,
                category VARCHAR(100)
            );
        """;
        try (Connection conn = DatabaseConnect.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng Book đã được tạo thành công");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM book";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisherYear = Integer.parseInt(rs.getString("publish_year"));
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                Book course = new Book(id, title, author, publisherYear, quantity, category);
                list.add(course);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất danh sách chứa sách: " + e.getMessage());
        }
        return list;
    }

    public static Book getBookById(int id) {
        Book book = null;
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisherYear = rs.getInt("publish_year");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                book = new Book(id, title, author, publisherYear, quantity, category);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất thông tin sách theo ID: " + e.getMessage());
        }
        return book;
    }


    public boolean addBook(Book book) {
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "INSERT INTO book (id, title, author, publish_year, quantity, category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, book.getId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getPublisherYear());
            ps.setInt(5, book.getQuantity());
            ps.setString(6, book.getCategory());
            int rowsInserted = ps.executeUpdate();
            con.close();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sách: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateBook(Book book) {
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "UPDATE Book SET title = ?, author = ?, publish_year = ?, quantity = ?, category = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublisherYear());
            ps.setInt(4, book.getQuantity());
            ps.setString(5, book.getCategory());
            ps.setInt(6, book.getId());
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật sách: " + e.getMessage());
            return false;
        }
    }

}
