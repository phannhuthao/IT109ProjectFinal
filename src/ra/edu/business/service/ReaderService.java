package ra.edu.business.service;

import ra.edu.business.config.ColorCode;
import ra.edu.business.entity.Reader;
import ra.edu.business.model.ReaderBusiness;
import ra.edu.validate.ReaderValidator;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReaderService {

    public List<Reader> getAllReaders() throws SQLException {
        return ReaderBusiness.getAllReaders();
    }

    public static void addNewReader(Scanner sc) {
        try {
            System.out.println("========== THÊM NGƯỜI ĐỌC MỚI ==========");

            int id;
            while (true) {
                System.out.print("Nhập ID: ");
                try {
                    id = sc.nextInt();
                    sc.nextLine();

                    if (!ReaderValidator.isValidateId(id)) {
                        System.out.println(ColorCode.RED + "ID phải là số nguyên dương. Vui lòng nhập lại." + ColorCode.RESET);
                        continue;
                    }

                    if (ReaderValidator.isDuplicateId(id)) {
                        System.out.println(ColorCode.RED + "ID đã tồn tại! Vui lòng nhập ID khác." + ColorCode.RESET);
                        continue;
                    }

                    break;
                } catch (Exception e) {
                    System.out.println(ColorCode.RED + "Lỗi nhập ID: " + e.getMessage() + ColorCode.RESET);
                    sc.nextLine();
                }
            }


            String username;
            while (true) {
                try {
                    System.out.print("Nhập tên người đọc: ");
                    username = sc.nextLine();
                    if (ReaderValidator.isValidatedReader(username)) {
                        break;
                    } else {
                        System.out.println(ColorCode.RED + "Tên người đọc không hợp lệ. Không được chứa kí tự đặc biệt và không để trống." + ColorCode.RESET);
                    }
                } catch (Exception e) {
                    System.out.println(ColorCode.RED + "Lỗi nhập tên người đọc: " + e.getMessage() + ColorCode.RESET);
                }
            }

            boolean sex;
            while (true) {
                try {
                    System.out.print("Nhập giới tính (true: Nam / false: Nữ): ");
                    if (sc.hasNextBoolean()) {
                        sex = sc.nextBoolean();
                        sc.nextLine();
                        break;
                    } else {
                        System.out.println(ColorCode.RED + "Giới tính phải là true hoặc false." + ColorCode.RESET);
                        sc.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println(ColorCode.RED + "Lỗi nhập giới tính: " + e.getMessage() + ColorCode.RESET);
                    sc.nextLine();
                }
            }

            Date birthdate;
            while (true) {
                try {
                    System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
                    String birthStr = sc.nextLine();
                    // SimpleDateFormat  nhận các chuỗi ngày tháng có định dạng là năm 4 chữ số (yyyy), tháng 2 chữ số (MM),
                    // và ngày trong tháng 2 chữ số (dd),
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    // setLenient(false) là một cách để kiểm soát cách xử lý các ngày tháng không hợp lệ khi chuyển chuỗi
                    // thành đối tượng Date.
                    sdf.setLenient(false);


                    birthdate = sdf.parse(birthStr);

                    break;
                } catch (ParseException e) {
                    System.out.println(ColorCode.RED + "Ngày sinh phải theo định dạng yyyy-MM-dd. Vui lòng nhập lại." + ColorCode.RESET);
                }
            }

            String phone;
            while (true) {
                try {
                    System.out.print("Nhập SĐT: ");
                    phone = sc.nextLine();
                    if (phone.matches("^\\d{10,11}$")) {
                        break;
                    } else {
                        System.out.println(ColorCode.RED + "Số điện thoại phải là số và có 10 hoặc 11 chữ số." + ColorCode.RESET);
                    }
                } catch (Exception e) {
                    System.out.println(ColorCode.RED + "Lỗi nhập số điện thoại: " + e.getMessage() + ColorCode.RESET);
                }
            }

            String email;
            while (true) {
                try {
                    System.out.print("Nhập Email: ");
                    email = sc.nextLine();
                    if (ReaderValidator.isValidateEmail(email)) {
                        break;
                    } else {
                        System.out.println(ColorCode.RED + "Email không hợp lệ. Vui lòng nhập lại." + ColorCode.RESET);
                    }
                } catch (Exception e) {
                    System.out.println(ColorCode.RED + "Lỗi nhập email: " + e.getMessage() + ColorCode.RESET);
                }
            }

            Reader reader = new Reader(id, username, sex, birthdate, phone, email);
            ReaderBusiness.addReader(reader);
            System.out.println(ColorCode.GREEN + "Thêm người đọc thành công!" + ColorCode.RESET);

        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi thêm người đọc: " + e.getMessage() + ColorCode.RESET);
        }
    }

}
