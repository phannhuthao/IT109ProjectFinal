package ra.edu.presentation;

import ra.edu.business.dao.AdminDao;
import ra.edu.business.entity.Book;
import ra.edu.business.model.BookBusniess;

import java.io.Console;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void run() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("|***************************************|");
            System.out.println("|-----CHƯƠNG TRÌNH QUẢN LÍ THƯ VIỆN-----|");
            System.out.println("|***************************************|");
            System.out.print("Tài khoản: ");
            String username = scanner.nextLine();

            // Nhập mật khẩu với dấu '*'
            String password = "";
            Console console = System.console();
            if (console != null) {
                char[] passwordChars = console.readPassword("Mật khẩu: ");
                password = new String(passwordChars);
            } else {
                // Nếu IDE không hỗ trợ Console
                System.out.print("Mật khẩu: ");
                password = scanner.nextLine();
            }

            if (AdminDao.checkLogin(username, password)) {
                System.out.println("Đăng nhập thành công!");
                break;
            } else {
                System.out.println("Sai tài khoản hoặc mật khẩu, vui lòng thử lại!");
            }
        }

        menuMain(scanner);
    }

    // Menu chính
    private static void menuMain(Scanner sc) {
        while (true) {
            System.out.println("|==========MENU CHÍNH==========|");
            System.out.println("|1. Quản Lý Sách               |");
            System.out.println("|2. Quản Lý Độc Giả            |");
            System.out.println("|3. Quản Lý Mượn/Trả           |");
            System.out.println("|4. Thoát                      |");
            System.out.println("|==========----------==========|");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    menuBook(sc);
                    break;
                case 2:
                    menuReader(sc);
                    break;
                case 3:
                    BorrowAndReturnMenu(sc);
                    break;
                case 4:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại!");
            }
        }
    }

    // Menu quản lí sách
    private static void menuBook(Scanner sc) {
        while (true) {
            System.out.println("|==========Quản Lí Sách==========|");
            System.out.println("|1. Hiển thị danh sách           |");
            System.out.println("|2. Thêm sách                    |");
            System.out.println("|3. Sửa thông tin sách           |");
            System.out.println("|4. Xóa sách                     |");
            System.out.println("|5. Tìm kiếm sách theo tên       |");
            System.out.println("|6. Sắp xếp sách theo tên        |");
            System.out.println("|7. Quay về Menu chính           |");
            System.out.println("|==========------------==========|");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    showInfoBook();
                    break;
                case 2:
                    addBook(sc);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    public static void showInfoBook() {
        List<Book> books = BookBusniess.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Không có sách nào.");
            return;
        }
        System.out.println("|---------------------------------------DANH SÁCH SÁCH-------------------------------------|");
        String leftAlignFormat = "| %-5s | %-30s | %-50s | %-20s | %-10s | %-10s |%n";
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.format("| ID    | Tên Sách                       | Tác Giả                                            | Năm Xuất Bản         | Số Lượng   | Thể Loại   |%n");
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");

        for (Book book : books) {
            System.out.format(leftAlignFormat, book.getId(), book.getTitle(), book.getAuthor(),
                    book.getPublisherYear(), book.getQuantity(), book.getCategory());
        }
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.println(" ");
    }

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

    // Menu quản lí độc giả
    private static void menuReader(Scanner sc) {
        while (true) {
            System.out.println("|==========Quản Lí Độc Giả==========|");
            System.out.println("|1. Hiển thị danh sách độc giả      |");
            System.out.println("|2. Thêm độc giả                    |");
            System.out.println("|3. Sửa thông tin đôc giả           |");
            System.out.println("|4. Xóa độc giả                     |");
            System.out.println("|5. Tìm kiếm độc giả theo tên       |");
            System.out.println("|6. Quay về Menu chính              |");
            System.out.println("|==========---------------==========|");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 6) {
                return;
            }
        }
    }

    // Menu quản lí mượn/trả
    private static void BorrowAndReturnMenu(Scanner sc) {
        while (true) {
            System.out.println("|==========Quản Lí Mượn/Trả==========|");
            System.out.println("|1. Hiển thị danh sách phiếu mượn    |");
            System.out.println("|2. Tạo phiếu mượn                   |");
            System.out.println("|3. Trả sách                         |");
            System.out.println("|4. Tìm kiếm phiếu mượn              |");
            System.out.println("|5. Quay về Menu chính               |");
            System.out.println("|==========--------------------------|");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 5) {
                return;
            }
        }
    }

    // Menu sắp xếp
    private static void menuSearch(Scanner sc) {
        System.out.println("|---------Chọn thứ tự sắp xếp----------|");
        System.out.println("|1. Tên A-Z                            |");
        System.out.println("|2. Tên Z-A                            |");
        System.out.println("|3. Quay về                            |");
        System.out.println("|---------===================----------|");
        System.out.print("Nhập lựa chọn: ");
    }
}
