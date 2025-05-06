package ra.edu.business.service;

import ra.edu.business.config.ColorCode;
import ra.edu.business.dao.BorrowDetailsDao;
import ra.edu.business.entity.BorrowDetails;

import java.util.Scanner;

public class BorrowDetailsService {

    // thêm mới
    public static void addDetalsBorrow(Scanner sc) {
        try {
            System.out.println("========== THÊM PHIẾU MƯỢN ==========");

            int borrowId;

            while (true) {
                System.out.print("Nhập ID phiếu mượn: ");
                try {
                    borrowId = sc.nextInt();
                    sc.nextLine();
                    if (borrowId <= 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ColorCode.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorCode.RESET);
                }
            }

            int bookId;
            while (true) {
                System.out.print("Nhập ID sách mượn: ");
                try {
                    bookId = sc.nextInt();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(ColorCode.RED + "Ngày không hợp lệ!" + ColorCode.RESET);
                }
            }

            int quantity;
            while (true) {
                System.out.print("Nhập số lượng: ");
                try {
                    quantity = sc.nextInt();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(ColorCode.RED + "Số lượng không hợp lệ!" + ColorCode.RESET);
                }
            }
            BorrowDetails borrowDetails = new BorrowDetails(borrowId, bookId, quantity);
            boolean success = BorrowDetailsDao.addBorrowDetails(borrowDetails);
            if (success) {
                System.out.println(ColorCode.GREEN + "Thêm phiếu mượn thành công!" + ColorCode.RESET);
            } else {
                System.out.println(ColorCode.RED + "Thêm phiếu mượn thất bại!" + ColorCode.RESET);
            }

        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi tạo phiếu mượn" + ColorCode.RESET);
        }
    }
}