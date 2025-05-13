package ra.edu.presentation;

import ra.edu.business.config.ColorCode;
import ra.edu.business.dao.AdminDao;
import ra.edu.business.entity.Book;
import ra.edu.business.entity.Borrow;
import ra.edu.business.entity.Reader;
import ra.edu.business.model.BookBusniess;
import ra.edu.business.model.BorrowBusniess;
import ra.edu.business.model.ReaderBusiness;
import ra.edu.business.service.BookService;
import ra.edu.business.service.BorrowService;
import ra.edu.business.service.ReaderService;
import ra.edu.untils.DataFormatUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Menu {
    public static void run() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(ColorCode.WHITE_BRIGHT+"|=======================================|");
            System.out.println("|     CHƯƠNG TRÌNH QUẢN LÍ THƯ VIỆN     |");
            System.out.println("|=======================================|"+ColorCode.RESET);

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
                    menuSearch(sc);
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

        System.out.println(ColorCode.CYAN_BRIGHT +
                "|----------------------------------------------------------DANH SÁCH SÁCH----------------------------------------------------------------------|");
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        System.out.format("| ID    | Tên Sách                       | Tác Giả                                            | Năm Xuất Bản         | Số Lượng   | Thể Loại   |%n");
        System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");

        for (Book book : books) {
            List<String> titleLines = DataFormatUtils.splitText(book.getTitle(), 32);
            List<String> authorLines = DataFormatUtils.splitText(book.getAuthor(), 52);
            List<String> categoryLines = DataFormatUtils.splitText(book.getCategory(), 10);

            int maxLines = Math.max(titleLines.size(), Math.max(authorLines.size(), categoryLines.size()));

            for (int i = 0; i < maxLines; i++) {
                System.out.format("| %-5s ", i == 0 ? book.getId() : "");
                System.out.format("| %-30s ", i < titleLines.size() ? titleLines.get(i) : "");
                System.out.format("| %-50s ", i < authorLines.size() ? authorLines.get(i) : "");
                System.out.format("| %-20s ", i == 0 ? book.getPublisherYear() : "");
                System.out.format("| %-10s ", i == 0 ? book.getQuantity() : "");
                System.out.format("| %-10s |%n", i < categoryLines.size() ? categoryLines.get(i) : "");
            }

            System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
        }

        System.out.println(" " + ColorCode.RESET);
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
            String category = book.getCategory();
            // Chia thể loại thành các phần nhỏ nếu quá dài
            List<String> splitCategories = DataFormatUtils.splitText(category, 10);

            System.out.format(leftAlignFormat, book.getId(), book.getTitle(), book.getAuthor(),
                    book.getPublisherYear(), book.getQuantity(), splitCategories.get(0));
            System.out.format("|-------|--------------------------------|----------------------------------------------------|----------------------|------------|------------|%n");
            // Nếu có nhiều phần của thể loại, hiển thị thêm các dòng tiếp theo
            for (int i = 1; i < splitCategories.size(); i++) {
                System.out.format("|       |                                |                                                    |                      |            | %-10s |%n", splitCategories.get(i));
            }
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

        System.out.println(ColorCode.CYAN_BRIGHT +"|----------------------------------------------------------DANH SÁCH NGƯỜI ĐỌC--------------------------------------|");
        System.out.format("| ID    | Tên Người Đọc                   | Giới Tính  | Ngày Sinh    | SĐT            | Email                      |%n");
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n");

        String leftAlignFormat2 = "| %-5s | %-31s | %-10s | %-12s | %-14s | %-26s |%n";
        for (Reader reader: readers) {
            String gender = reader.getSex() ? "Nam" : "Nữ";
            System.out.format(leftAlignFormat2, reader.getId(), reader.getUsername(),
                    gender, reader.getBirthdate(), reader.getPhone(), reader.getEmail());
        }
        System.out.format("|-------|---------------------------------|------------|--------------|----------------|----------------------------|%n"+ColorCode.RESET);
        System.out.println();
    }

    public static void displayFoundReaders(List<Reader> readers) {
        if (readers.isEmpty()) {
            System.out.println(ColorCode.RED + "Không tìm thấy tên độc giả nào phù hợp" + ColorCode.RESET);
            return;
        }
        System.out.println(ColorCode.GREEN + "Kết quả tìm kiếm độc giả:" + ColorCode.RESET);
        System.out.println("|----------------------------------------------------------KẾT QUẢ TÌM KIẾM ĐỘC GIẢ-------------------------------------|");
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
            System.out.println("|==========----------------==========|");
            System.out.print("Nhập lựa chọn: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    showInfoBorrow();
                    break;
                case 2:
                    BorrowService.addBorrow(sc);
                    break;
                case 3:
                    BorrowService.returnBook(sc);
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    public static void showInfoBorrow() {
        List<Borrow> borrows = BorrowBusniess.getAllBorrows();
        if (borrows.isEmpty()) {
            System.out.println("Không có phiếu mượn nào");
            return;
        }

        System.out.println(ColorCode.CYAN_BRIGHT + "|--------------------------------------------DANH SÁCH PHIẾU MƯỢN--------------------------------------|");
        System.out.format("| %-5s | %-20s | %-12s | %-12s | %-10s                              |%n",
                "ID", "ID độc giả", "Ngày mượn", "Ngày trả", "Trạng thái");
        System.out.println("|------------------------------------------------------------------------------------------------------|");

        for (Borrow b : borrows) {
            String status = b.isReturned() ? "Đã trả" : "Chưa trả";
            System.out.format("| %-5d | %-20d | %-12s | %-12s | %-10s                              |%n",
                    b.getId(), b.getReaderId(), b.getBorrowDate(), b.getReturnDate(), status);
        }

        System.out.println("|------------------------------------------------------------------------------------------------------|"+ ColorCode.RESET);
    }



    // Menu sắp xếp
    private static void menuSearch(Scanner sc) {
        System.out.println("|---------Chọn thứ tự sắp xếp----------|");
        System.out.println("|1. Tên A-Z                            |");
        System.out.println("|2. Tên Z-A                            |");
        System.out.println("|3. Quay về                            |");
        System.out.println("|---------===================----------|");
        System.out.print("Nhập lựa chọn: ");
        int choice;
        choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                BookService.sortBooksAZ();
                break;
            case 2:
                BookService.sortBooksZA();
                break;
            case 3:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ");
        }
    }

    public static void showInfoSearchSort(Scanner sc) {
        List<Book> sortedBooks = BookBusniess.sortBooksByTitle();
        System.out.println(ColorCode.GREEN + "Danh sách sách đã được sắp xếp theo tên:" + ColorCode.RESET);
        displayFoundBooks(sortedBooks);
    }

}
