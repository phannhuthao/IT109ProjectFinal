package ra.edu.business.service;

import ra.edu.business.config.ColorCode;
import ra.edu.business.dao.BorrowDao;
import ra.edu.business.entity.Borrow;
import ra.edu.business.model.BorrowBusniess;
import ra.edu.validate.BorrowValidator;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.Menu.showInfoBorrow;

public class BorrowService {
    // hiển thị danh sách mượn
    public List<Borrow> getAllBorrows() {
        return BorrowBusniess.getAllBorrows();
    }

    // thêm
    public static void addBorrow(Scanner sc) {
        try {
            System.out.println(ColorCode.GREEN + "========== THÊM PHIẾU MƯỢN =========="+ColorCode.RESET);

            int id;
            while (true) {
                System.out.print("Nhập ID phiếu mượn: ");
                try {
                    id = sc.nextInt();
                    sc.nextLine();

                    if (!BorrowValidator.isValidId(id)) {
                        System.out.println(ColorCode.RED + "ID phải là số nguyên dương!" + ColorCode.RESET);
                        continue;
                    }

                    if (BorrowValidator.isDuplicateId(id)) {
                        System.out.println(ColorCode.RED + "ID này đã tồn tại! Vui lòng nhập ID khác." + ColorCode.RESET);
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorCode.RESET);
                }
            }

            int readerId;
            while (true) {
                System.out.print("Nhập ID người đọc: ");
                try {
                    readerId = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorCode.RESET);
                }
            }

            Date borrowDate;
            while (true) {
                System.out.print("Nhập ngày mượn (yyyy-MM-dd): ");
                try {
                    String input = sc.nextLine();
                    borrowDate = java.sql.Date.valueOf(input);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(ColorCode.RED + "Định dạng ngày không hợp lệ!" + ColorCode.RESET);
                }
            }

            Date returnDate;
            while (true) {
                System.out.print("Nhập ngày trả (yyyy-MM-dd): ");
                try {
                    String input = sc.nextLine();
                    returnDate = java.sql.Date.valueOf(input);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(ColorCode.RED + "Định dạng ngày không hợp lệ!" + ColorCode.RESET);
                }
            }

            String status;
            while (true) {
                System.out.print("Nhập trạng thái (BORROWED / RETURNED): ");
                status = sc.nextLine().toUpperCase();

                if (!status.equals("BORROWED") && !status.equals("RETURNED")) {
                    System.out.println("Giá trị không hợp lệ! Chỉ được nhập 'BORROWED' hoặc 'RETURNED'");
                    // Trả về false nếu trạng thái không hợp lệ
                    return;
                }

                break;
            }

            Borrow borrow = new Borrow(id, readerId, borrowDate, returnDate, status);
            boolean success = BorrowDao.addBorrow(borrow);
            if (success) {
                System.out.println(ColorCode.GREEN + "Thêm phiếu mượn thành công!" + ColorCode.RESET);
            } else {
                System.out.println(ColorCode.RED + "Thêm phiếu mượn thất bại!" + ColorCode.RESET);
            }
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi tạo phiếu mượn: " + e.getMessage() + ColorCode.RESET);
        }
    }


    public static void returnBook(Scanner sc) {
        try {
            System.out.println("========== TRẢ SÁCH ==========");

            int id;
            while (true) {
                System.out.print("Nhập ID phiếu mượn cần trả: ");
                try {
                    id = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorCode.RESET);
                }
            }

            // Cập nhật ngày trả và trạng thái
            boolean success = BorrowDao.returnBook(id);
            if (success) {
                System.out.println(ColorCode.GREEN + "Trả sách thành công!" + ColorCode.RESET);
                showInfoBorrow();
            } else {
                System.out.println(ColorCode.RED + "Không tìm thấy phiếu mượn hoặc xảy ra lỗi khi trả sách!" + ColorCode.RESET);
            }


        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi trả sách: " + e.getMessage() + ColorCode.RESET);
        }
    }
}
