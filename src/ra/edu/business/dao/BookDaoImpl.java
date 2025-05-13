package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl {

    public static List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            CallableStatement cs = con.prepareCall("{CALL get_all_books()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
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
            CallableStatement cs = con.prepareCall("{CALL get_book_by_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                book = new Book(
                        id,
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
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
            CallableStatement cs = con.prepareCall("{CALL add_book(?, ?, ?, ?, ?)}");
            cs.setString(1, book.getTitle());
            cs.setString(2, book.getAuthor());
            cs.setInt(3, book.getPublisherYear());
            cs.setInt(4, book.getQuantity());
            cs.setString(5, book.getCategory());
            int rowsInserted = cs.executeUpdate();
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
            CallableStatement cs = con.prepareCall("{CALL update_book(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, book.getId());
            cs.setString(2, book.getTitle());
            cs.setString(3, book.getAuthor());
            cs.setInt(4, book.getPublisherYear());
            cs.setInt(5, book.getQuantity());
            cs.setString(6, book.getCategory());
            int rows = cs.executeUpdate();
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
            CallableStatement cs = con.prepareCall("{CALL delete_book_by_id(?)}");
            cs.setInt(1, id);
            int rowsDeleted = cs.executeUpdate();
            con.close();
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
            CallableStatement cs = con.prepareCall("{CALL search_book_by_name(?)}");
            cs.setString(1, "%" + name + "%");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm sách: " + e.getMessage());
        }
        return list;
    }

    public static List<Book> sortBooksByTitleAsc() {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            CallableStatement cs = con.prepareCall("{CALL sort_books_by_title_asc()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi sắp xếp sách tăng dần theo tiêu đề: " + e.getMessage());
        }
        return list;
    }

    public static List<Book> sortBooksByTitleDesc() {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            CallableStatement cs = con.prepareCall("{CALL sort_books_by_title_desc()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                ));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi sắp xếp sách giảm dần theo tiêu đề: " + e.getMessage());
        }
        return list;
    }
}
