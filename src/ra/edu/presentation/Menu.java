package ra.edu.presentation;

import ra.edu.business.config.ColorCode;
import ra.edu.business.dao.AdminDao;
import ra.edu.business.entity.Book;
import ra.edu.business.entity.Reader;
import ra.edu.business.model.BookBusniess;
import ra.edu.business.model.ReaderBusiness;
import ra.edu.business.service.BookService;
import ra.edu.business.service.ReaderService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Menu {
    public static void run() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("|***************************************|");
            System.out.println("|-----CHƯƠNG TRÌNH QUẢN LÍ THƯ VIỆN-----|");
            System.out.println("|***************************************|");

            System.out.print("Tài khoản: ");
            String username = sc.nextLine();

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            if (AdminDao.checkLogin(username, password)) {
                System.out.println(ColorCode.GREEN + "Đăng nhập thành công!" + ColorCode.RESET);
                break;
            } else {
                System.out.println(ColorCode.RED + "Sai tài khoản hoặc mật khẩu, vui lòng nhập lại xem sao" + ColorCode.RESET);
            }
        }

        menuMain(sc);
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
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    showInfoBook();
                    break;
                case 2:
                    BookService.addBook(sc);
                    break;
                case 3:
                    BookService.updateBook(sc);
                    break;
                case 4:
                    BookService.deleteBook(sc);
                    break;
                case 5:
                    BookService.searchBookByName(sc);
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println(ColorCode.RED + "Lựa chọn không hợp lệ" + ColorCode.RESET);
            }
        }
    }

    public static void showInfoBook() {
        List<Book> books = BookBusniess.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Không có sách nào.");
            return;
        }
        System.out.println("|----------------------------------------------------------DANH SÁCH SÁCH----------------------------------------------------------------------|");
        String leftAlignFormat1 = "| %-5s | %-30s | %-50s | %-20s | %-10s | %-10s |%n";
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.format("| ID    | Tên Sách                       | Tác Giả                                            | Năm Xuất Bản         | Số Lượng   | Thể Loại   |%n");
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");

        for (Book book : books) {
            System.out.format(leftAlignFormat1, book.getId(), book.getTitle(), book.getAuthor(),
                    book.getPublisherYear(), book.getQuantity(), book.getCategory());
            System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        }
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.println(" ");
    }

    public static void displayFoundBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println(ColorCode.RED + "Không tìm thấy cuốn sách nào phù hợp với yêu cầu bạn vừa nhập" + ColorCode.RESET);
            return;
        }
        System.out.println(ColorCode.GREEN + "Kết quả tìm kiếm sách:" + ColorCode.RESET);
        System.out.println("|----------------------------------------------------------KẾT QUẢ TÌM KIẾM----------------------------------------------------------------------|");
        String leftAlignFormat = "| %-5s | %-30s | %-50s | %-20s | %-10s | %-10s |%n";
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.format("| ID    | Tên Sách                       | Tác Giả                                            | Năm Xuất Bản         | Số Lượng   | Thể Loại   |%n");
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");

        for (Book book : books) {
            System.out.format(leftAlignFormat, book.getId(), book.getTitle(), book.getAuthor(),
                    book.getPublisherYear(), book.getQuantity(), book.getCategory());
        }
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.println();
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
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    showInfoReader();
                    break;
                case 2:
                    ReaderService.addNewReader(sc);
                    break;
                case 3:
                    ReaderService.updateReader(sc);
                    break;
                case 4:
                    ReaderService.deleteReader(sc);
                    break;
                case 5:
                    ReaderService.searchReaderByName(sc);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    public static void showInfoReader() {
        List<Reader> readers = ReaderBusiness.getAllReaders();
        if (readers.isEmpty()) {
            System.out.println("Không có người đọc nào");
            return;
        }

        System.out.println("|----------------------------------------------------------DANH SÁCH NGƯỜI ĐỌC--------------------------------------|");
        System.out.format("| ID    | Tên Người Đọc                   | Giới Tính  | Ngày Sinh    | SĐT            | Email                      |%n");
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n");

        String leftAlignFormat2 = "| %-5s | %-31s | %-10s | %-12s | %-14s | %-26s |%n";
        for (Reader reader: readers) {
            String gender = reader.getSex() ? "Nam" : "Nữ";
            System.out.format(leftAlignFormat2, reader.getId(), reader.getUsername(),
                    gender, reader.getBirthdate(), reader.getPhone(), reader.getEmail());
        }
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n");
        System.out.println();
    }

    public static void displayFoundReaders(List<Reader> readers) {
        if (readers.isEmpty()) {
            System.out.println(ColorCode.RED + "Không tìm thấy tên độc giả nào phù hợp" + ColorCode.RESET);
            return;
        }
        System.out.println(ColorCode.GREEN + "Kết quả tìm kiếm độc giả:" + ColorCode.RESET);
        System.out.println("|----------------------------------------------------------KẾT QUẢ TÌM KIẾM ĐỘC GIẢ--------------------------------------|");
        System.out.format("| ID    | Tên Người Đọc                   | Giới Tính  | Ngày Sinh    | SĐT            | Email                      |%n");
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n");

        String leftAlignFormat = "| %-5s | %-31s | %-10s | %-12s | %-14s | %-26s |%n";
        for (Reader reader : readers) {
            String gender = reader.getSex() ? "Nam" : "Nữ";
            System.out.format(leftAlignFormat, reader.getId(), reader.getUsername(),
                    gender, reader.getBirthdate(), reader.getPhone(), reader.getEmail());
        }
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n");
        System.out.println();
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
