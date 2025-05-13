package ra.edu.validate;

import ra.edu.business.model.BookBusniess;

import java.util.regex.Pattern;

public class BookValidator {
    private static final String NAME_REGEX = "^[a-zA-ZÀ-Ỹà-ỹ0-9\\s]+$";

//    // ID chỉ được là số nguyên dương, không được trùng Id
//    public static boolean isValidId(int id) {
//        return id > 0;
//
//    }
//
//    public static boolean isDuplicateId(int id) {
//        try {
//            // Nếu trả về null thì không trùng
//            return BookBusniess.getBookById(id) != null;
//        } catch (Exception e) {
//            // Nếu có lỗi trong việc tìm kiếm ID, trả về false (không hợp lệ)
//            return false;
//        }
//    }

    // Kiểm tra chuỗi không rỗng và không null
    private static boolean isNotEmpty(String str) {
        // Kiểm tra chuỗi không rỗng và không null
        return str != null && !str.trim().isEmpty();
    }

    private static boolean isValidLength(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

    // Kiểm tra chuỗi có khớp với biểu thức chính quy (regex) hay không
    private static boolean matchesRegex(String str, String regex) {
        return Pattern.matches(regex, str);
    }

    // Kiểm tra tên sách
    public static boolean isValidTitle(String title) {
        // Không được null hoặc rỗng
        return isNotEmpty(title)
                && isValidLength(title, 2, 100)
                // Phải khớp định dạng cho phép
                && matchesRegex(title, NAME_REGEX);
    }

    // Kiểm tra tên tác giả
    public static boolean isValidAuthor(String author) {
        return isNotEmpty(author)
                && isValidLength(author, 2, 100)
                && matchesRegex(author, NAME_REGEX);
    }

    // Kiểm tra thể loại
    public static boolean isValidCategory(String category) {
        return isNotEmpty(category)
                && isValidLength(category, 2, 50);
    }

    // Kiểm tra tiêu đề sách đã tồn tại hay chưa
    public static boolean isDuplicateTitle(String title) {
        try {
            return BookBusniess.getBookByTitle(title) != null;
        } catch (Exception e) {
            return false;
        }
    }


    // Tên tiêu đề và tên tác giả
    public static boolean isValidatedTitleAndAuthor(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(NAME_REGEX, name);
    }

    // Năm xuất bản là số dương
    public static boolean isValidatePublisherYear(int publisherYear) {
        return publisherYear > 0;
    }

    // Số lượng là số dương
    public static boolean isValidateQuantity(int quantity) {
        return quantity > 0;
    }
}
