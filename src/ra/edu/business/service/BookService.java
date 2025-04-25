package ra.edu.business.service;

import ra.edu.business.entity.Book;
import ra.edu.business.model.BookBusniess;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookService {
    // Hiển thị danh sách sách
    public List<Book> getAllBooks() throws SQLException {
        return BookBusniess.getAllBooks();
    }

    // Thêm sách mới vào
    public static void addBook(Scanner sc) {
        try {
            System.out.println("========== THÊM SÁCH MỚI ==========");
            System.out.print("Nhập ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập tên tiêu đề: ");
            String title = sc.nextLine();

            System.out.print("Nhập tên tác giả: ");
            String author = sc.nextLine();

            System.out.print("Nhập năm xuất bản: ");
            int publisherYear = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập số lượng: ");
            int quantity = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập thể loại: ");
            String category = sc.nextLine();

            Book book = new Book(id, title, author, publisherYear, quantity, category);

            boolean success = BookBusniess.addBook(book);
            if (success) {
                System.out.println("Thêm sách thành công!");
            } else {
                System.out.println("Thêm sách thất bại. Vui lòng kiểm tra lại thông tin.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sách: " + e.getMessage());
        }
    }
    // Cập nhật lại sách
    public static void upadateBook(Scanner sc) {
        try {
            System.out.println("========== CẬP NHẬT DANH SÁCH SÁCH ==========");
            System.out.print("Nhập ID sách cần sửa: ");
            int id = Integer.parseInt(sc.nextLine());

            Book book = BookBusniess.getBookById(id);
            if (book == null) {
                System.out.println("Sách không tồn tại với ID: " + id);
                return;
            }

            System.out.println("Thông tin sách hiện tại:");

            System.out.println("Tiêu đề: " + book.getTitle());
            System.out.print("Nhập tiêu đề mới (hoặc giữ nguyên nếu không thay đổi): ");
            String title = sc.nextLine();
            if (title.isEmpty()) title = book.getTitle();

            System.out.println("Tác giả: " + book.getAuthor());
            System.out.print("Nhập tên tác giả mới (hoặc giữ nguyên nếu không thay đổi): ");
            String author = sc.nextLine();
            if (author.isEmpty()) author = book.getAuthor();

            System.out.println("Năm xuất bản: " + book.getPublisherYear());
            System.out.print("Nhập năm xuất bản mới (hoặc giữ nguyên nếu không thay đổi): ");
            String publisherYearInput = sc.nextLine();
            // Giữ nguyên nếu không có thay đổi
            int publisherYear = book.getPublisherYear();
            if (!publisherYearInput.isEmpty()) {
                try {
                    publisherYear = Integer.parseInt(publisherYearInput);
                } catch (NumberFormatException e) {
                    System.out.println("Năm xuất bản không hợp lệ, giữ nguyên.");
                }
            }

            System.out.println("Số lượng: " + book.getQuantity());
            System.out.print("Nhập số lượng mới (hoặc giữ nguyên nếu không thay đổi): ");
            String quantityInput = sc.nextLine();
            // Giữ nguyên nếu không có thay đổi
            int quantity = book.getQuantity();
            if (!quantityInput.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quantityInput);
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ, giữ nguyên.");
                }
            }

            System.out.println("Thể loại: " + book.getCategory());
            System.out.print("Nhập thể loại mới (hoặc giữ nguyên nếu không thay đổi): ");
            String category = sc.nextLine();
            if (category.isEmpty()) category = book.getCategory();

            Book updatedBook = new Book(id, title, author, publisherYear, quantity, category);

            boolean success = BookBusniess.updateBook(updatedBook);
            if (success) {
                System.out.println("Cập nhật sách thành công!");
            } else {
                System.out.println("Cập nhật sách thất bại. Vui lòng kiểm tra lại thông tin.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
