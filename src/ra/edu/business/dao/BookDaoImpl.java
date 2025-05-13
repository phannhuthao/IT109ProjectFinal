package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl {
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

    public static boolean deleteBookById(int id) {
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "DELETE FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sách: " + e.getMessage());
        }
        return false;
    }

    public static List<Book> searchBookByName(String name) {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM book WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            // tìm chứa từ khóa name: "%" + name + "%" để SQL tìm mọi title mà có chứa từ name đó
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisherYear = Integer.parseInt(rs.getString("publish_year"));
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                Book book = new Book(id, title, author, publisherYear, quantity, category);
                list.add(book);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm sách: " + e.getMessage());
        }
        return list;
    }


    public static List<Book> sortBooksByTitleAsc() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM book ORDER BY title ASC";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public static List<Book> sortBooksByTitleDesc() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM book ORDER BY title DESC";
        try (
                Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookList;
    }


}
