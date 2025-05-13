package ra.edu.business.service;

import ra.edu.business.config.ColorCode;
import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.dao.ReaderDao;
import ra.edu.business.entity.Reader;
import ra.edu.business.model.ReaderBusiness;
import ra.edu.validate.ReaderValidator;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.Menu.displayFoundReaders;

public class ReaderService {

    public List<Reader> getAllReaders() throws SQLException {
        return ReaderBusiness.getAllReaders();
    }

    public static void addNewReader(Scanner sc) {
        try {
            System.out.println(ColorCode.GREEN + "========== THÊM NGƯỜI ĐỌC MỚI ==========" + ColorCode.RESET);
            int id = 0;

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

            Reader reader = new Reader(id ,username, sex, birthdate, phone, email);
            ReaderBusiness.addReader(reader);
            System.out.println(ColorCode.GREEN + "Thêm người đọc thành công!" + ColorCode.RESET);

        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi thêm người đọc: " + e.getMessage() + ColorCode.RESET);
        }
    }

    public static void updateReader(Scanner sc) {
        System.out.println(ColorCode.YELLOW +"========== CẬP NHẬT NGƯỜI ĐỌC ==========");
        System.out.println("=============================-==========" + ColorCode.RESET);

        System.out.print("Nhập ID cần sửa: ");
        int id = sc.nextInt();
        sc.nextLine();

        Reader reader = ReaderBusiness.getReaderById(id);
        if (reader == null) {
            System.out.println(ColorCode.RED + "Người đọc không tồn tại với ID: " + id + ColorCode.RESET);
            return;
        }

        System.out.println("Thông tin người đọc hiện tại:");
        System.out.println(ColorCode.YELLOW + "Tên: " + reader.getUsername() + ColorCode.RESET);
        System.out.print("Nhập tên mới (Nhấn Enter nếu không muốn thay đổi): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) {
            if (ReaderValidator.isValidatedReader(name)) {
                reader.setUsername(name);
            } else {
                System.out.println(ColorCode.RED + "Tên không hợp lệ" + ColorCode.RESET);
            }
        }

        System.out.println(ColorCode.YELLOW + "Giới tính (Nam: true / Nữ: false): " + reader.getSex() + ColorCode.RESET);
        System.out.print("Nhập giới tính mới (Nhấn Enter nếu không muốn thay đổi): ");
        String genderInput = sc.nextLine();
        if (!genderInput.isEmpty()) {
            if (genderInput.equalsIgnoreCase("true") || genderInput.equalsIgnoreCase("false")) {
                reader.setSex(Boolean.parseBoolean(genderInput));
            } else {
                System.out.println(ColorCode.RED + "Giới tính không hợp lệ" + ColorCode.RESET);
            }
        }

        System.out.println(ColorCode.YELLOW + "Ngày sinh: " + new SimpleDateFormat("dd/MM/yyyy").format(reader.getBirthdate()) + ColorCode.RESET);
        System.out.print("Nhập ngày sinh mới (dd/MM/yyyy) (Nhấn Enter nếu không muốn thay đổi): ");
        String birthStr = sc.nextLine();
        if (!birthStr.isEmpty()) {
            Date validDate = ReaderValidator.isValidateBirthday(birthStr);
            if (validDate != null) {
                reader.setBirthdate(validDate);
            } else {
                System.out.println(ColorCode.RED + "Ngày sinh không hợp lệ" + ColorCode.RESET);
            }
        }

        System.out.println(ColorCode.YELLOW + "SĐT: " + reader.getPhone() + ColorCode.RESET);
        System.out.print("Nhập SĐT mới (Nhấn Enter nếu không muốn thay đổi): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) {
            if (phone.matches("^\\d{10,11}$")) {
                reader.setPhone(phone);
            } else {
                System.out.println(ColorCode.RED + "SĐT không hợp lệ. Giữ nguyên số cũ." + ColorCode.RESET);
            }
        }

        System.out.println(ColorCode.YELLOW + "Email: " + reader.getEmail() + ColorCode.RESET);
        System.out.print("Nhập email mới (Nhấn Enter nếu không muốn thay đổi): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) {
            if (ReaderValidator.isValidateEmail(email)) {
                reader.setEmail(email);
            } else {
                System.out.println(ColorCode.RED + "Email không hợp lệ. Giữ nguyên email cũ." + ColorCode.RESET);
            }
        }

        ReaderBusiness.updateReader(reader);
        System.out.println(ColorCode.GREEN + "Cập nhật thông tin người đọc thành công!" + ColorCode.RESET);
    }



    public static void deleteReader(Scanner sc) {
        System.out.println(ColorCode.RED + "========== XÓA NGƯỜI ĐỌC ==========");
        System.out.print("Nhập ID cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();

        Reader reader = ReaderDao.getReaderById(id);
        if (reader == null) {
            System.out.println(ColorCode.RED + "Không tìm thấy người đọc với ID: " + id + ColorCode.RESET);
            return;
        }

        System.out.println(ColorCode.YELLOW+ "Bạn có chắc chắn muốn xóa độc giả '" + reader.getUsername() + "'? (Y/N): " + ColorCode.RESET);
        String confirm = sc.nextLine();
        // Dùng equalsIgnoreCase để so sánh hai chữ thường và hoa của 1 kiểu chữ
        if (confirm.equalsIgnoreCase("Y")) {
            ReaderBusiness.deleteByIdReader(id);
        } else {
            System.out.println(ColorCode.YELLOW + "Hủy xóa độc giả." + ColorCode.RESET);
        }
    }

    public static void searchReaderByName(Scanner sc) {
        System.out.print("Nhập tên độc giả cần tìm: ");
        String name = sc.nextLine().trim();
        List<Reader> foundReaders = ReaderBusiness.searchReaderByName(name);
        displayFoundReaders(foundReaders);
    }



}