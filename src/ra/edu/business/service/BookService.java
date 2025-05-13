package ra.edu.business.service;

import ra.edu.business.config.ColorCode;
import ra.edu.business.entity.Book;
import ra.edu.business.model.BookBusniess;
import ra.edu.validate.BookValidator;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.Menu.displayFoundBooks;

public class BookService {
    // Hiển thị danh sách sách
    public List<Book> getAllBooks() throws SQLException {
        return BookBusniess.getAllBooks();
    }

    // thêm sách
    public static void addBook(Scanner sc) {
        try {
            System.out.println(ColorCode.GREEN + "|=====================================|");
            System.out.println("|          THÊM SÁCH MỚI              |");
            System.out.println("|=====================================|" + ColorCode.RESET);

            int id = 0;

            String title;
            while (true) {
                System.out.print("Nhập tên sách: ");
                title = sc.nextLine();
                if (!BookValidator.isValidTitle(title)) {
                    System.out.println(ColorCode.RED + "Tên sách không hợp lệ (trống, sai định dạng hoặc độ dài không phù hợp)!" + ColorCode.RESET);
                    continue;
                }
                if (BookBusniess.getBookByTitle(title) != null) {
                    System.out.println(ColorCode.RED + "Tên sách này đã có. Vui lòng nhập tên khác." + ColorCode.RESET);
                    continue;
                }
                break;
            }

            String author;
            while (true) {
                System.out.print("Nhập tên tác giả: ");
                author = sc.nextLine();
                if (!BookValidator.isValidAuthor(author)) {
                    System.out.println(ColorCode.RED + "Tên tác giả không hợp lệ (trống, sai định dạng hoặc độ dài không phù hợp)!" + ColorCode.RESET);
                    continue;
                }
                break;
            }

            int publisherYear;
            while (true) {
                System.out.print("Nhập năm xuất bản: ");
                try {
                    publisherYear = sc.nextInt();
                    if (BookValidator.isValidatePublisherYear(publisherYear)) break;
                    else System.out.println(ColorCode.RED + "Năm xuất bản phải là số nguyên dương" + ColorCode.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Năm xuất bản không hợp lệ!" + ColorCode.RESET);
                }
            }

            int quantity;
            while (true) {
                System.out.print("Nhập số lượng: ");
                try {
                    quantity = sc.nextInt();
                    sc.nextLine();
                    if (BookValidator.isValidateQuantity(quantity)) break;
                    else System.out.println(ColorCode.RED + "Số lượng phải là số nguyên dương" + ColorCode.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Số lượng không hợp lệ!" + ColorCode.RESET);
                }
            }

            System.out.print("Nhập thể loại: ");
            String category = sc.nextLine();

            if (category.isEmpty()) {
                System.out.println(ColorCode.RED + "Trường 'Thể loại' không được để trống"+ ColorCode.RESET);
            }

            Book book = new Book(id, title, author, publisherYear, quantity, category);

            boolean success = BookBusniess.addBook(book);
            if (success) {
                System.out.println(ColorCode.GREEN + "Thêm sách thành công!" + ColorCode.RESET);
            } else {
                System.out.println(ColorCode.RED + "Thêm sách thất bại. Vui lòng kiểm tra lại thông tin." + ColorCode.RESET);
            }
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi thêm sách: " + e.getMessage() + ColorCode.RESET);
        }
    }

    // Cập nhật lại sách
    public static void updateBook(Scanner sc) {
        try {
            System.out.println(ColorCode.YELLOW + "|=============================================|");
            System.out.println("|           CẬP NHẬT DANH SÁCH SÁCH           |");
            System.out.println("|=============================================|" + ColorCode.RESET);
            System.out.print("Nhập ID sách cần sửa: ");
            int id = Integer.parseInt(sc.nextLine());

            Book book = BookBusniess.getBookById(id);
            if (book == null) {
                System.out.println(ColorCode.RED + "Sách không tồn tại với ID: " + id + ColorCode.RESET);
                return;
            }

            System.out.println("Thông tin sách hiện tại:");
            System.out.println(ColorCode.YELLOW+"Tiêu đề: " + book.getTitle()+  ColorCode.RESET);
            System.out.print("Nhập tiêu đề mới (Nhấn Enter giữ nguyên nếu không thay đổi): ");
            String title = sc.nextLine();
            if (title.isEmpty()) {
                title = book.getTitle();
            }
            System.out.println("------------------------------------------------------------------");

            System.out.println(ColorCode.YELLOW+ "Tác giả: " + book.getAuthor() +  ColorCode.RESET);
            System.out.print("Nhập tên tác giả mới (Nhấn Enter giữ nguyên nếu không thay đổi): ");
            String author = sc.nextLine();
            if (author.isEmpty()) {
                author = book.getAuthor();
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println(ColorCode.YELLOW+"Năm xuất bản: " + book.getPublisherYear() + ColorCode.RESET);
            System.out.print("Nhập năm xuất bản mới (Nhấn Enter giữ nguyên nếu không thay đổi): ");
            String publisherYearInput = sc.nextLine();
            int publisherYear = book.getPublisherYear();
            if (!publisherYearInput.isEmpty()) {
                try {
                    publisherYear = Integer.parseInt(publisherYearInput);
                } catch (NumberFormatException e) {
                    System.out.println("Năm xuất bản không hợp lệ, giữ nguyên." + ColorCode.RESET);
                }
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println(ColorCode.YELLOW + "Số lượng: " + book.getQuantity() + ColorCode.RESET);
            System.out.print("Nhập số lượng mới (Nhấn Enter giữ nguyên nếu không thay đổi): ");
            String quantityInput = sc.nextLine();
            int quantity = book.getQuantity();
            if (!quantityInput.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quantityInput);
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ, giữ nguyên." + ColorCode.RESET);
                }
            }

            System.out.println("------------------------------------------------------------------");
            System.out.println(ColorCode.YELLOW + "Thể loại: " + book.getCategory() + ColorCode.RESET);
            System.out.print("Nhập thể loại mới (Nhấn Enter giữ nguyên nếu không thay đổi): ");
            String category = sc.nextLine();
            if (category.isEmpty()) category = book.getCategory();

            Book updatedBook = new Book(id, title, author, publisherYear, quantity, category);
            boolean result = BookBusniess.updateBook(updatedBook);

            if (result) {
                System.out.println(ColorCode.GREEN + "Cập nhật sách thành công!" + ColorCode.RESET);
            } else {
                System.out.println(ColorCode.RED + "Cập nhật thất bại!" + ColorCode.RESET);
            }

        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Đã xảy ra lỗi khi cập nhật sách: " + e.getMessage() + ColorCode.RESET);
        }
    }


    public static void deleteBook(Scanner sc) {
        try {
            System.out.println(ColorCode.RED_BOLD+"|==============================|");
            System.out.println("|           XÓA SÁCH           |");
            System.out.println("|==============================|"+ColorCode.RESET);
            System.out.print("Nhập ID sách cần xóa: ");
            int id = sc.nextInt();
            sc.nextLine();

            Book book = BookBusniess.getBookById(id);
            if (book == null) {
                System.out.println(ColorCode.RED + "Không tìm thấy sách với ID này: " + id + ColorCode.RESET);
                return;
            }

            System.out.println(ColorCode.YELLOW + "Bạn có chắc chắn muốn xóa sách '" + book.getTitle() + "'? (Y/N): "+ ColorCode.RESET);
            String confirm = sc.nextLine();
            // Dùng equalsIgnoreCase để so sánh hai chữ thường và hoa của 1 kiểu chữ
            if (confirm.equalsIgnoreCase("Y")) {
                boolean success = BookBusniess.deleteBookById(id);
                if (success) {
                    System.out.println(ColorCode.GREEN + "Xóa sách thành công!" + ColorCode.RESET);
                } else {
                    System.out.println(ColorCode.RED + "Xóa sách thất bại!" + ColorCode.RESET);
                }
            } else {
                System.out.println(ColorCode.YELLOW + "Hủy bỏ thao tác xóa." + ColorCode.RESET);
            }
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Đã xảy ra lỗi khi xóa sách: " + e.getMessage() + ColorCode.RESET);
        }
    }

    public static void searchBookByName(Scanner sc) {
        System.out.println(ColorCode.YELLOW+"|==============================|");
        System.out.println("|           TÌM SÁCH           |");
        System.out.println("|==============================|"+ColorCode.RESET);
        System.out.print("Nhập tên sách cần tìm: ");
        String name = sc.nextLine().trim();
        List<Book> foundBooks = BookBusniess.searchBooksByName(name);
        displayFoundBooks(foundBooks);
    }

    public static void sortBooksAZ() {
        List<Book> books = BookBusniess.sortBooksByTitleAsc();
        displayFoundBooks(books);
    }

    public static void sortBooksZA() {
        List<Book> books = BookBusniess.sortBooksByTitleDesc();
        displayFoundBooks(books);
    }

}
