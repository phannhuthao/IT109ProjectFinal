package ra.edu.validate;

import ra.edu.business.model.BookBusniess;

import java.util.regex.Pattern;

public class BookValidator {
    private static final String NAME_REGEX = "^[a-zA-ZÀ-Ỹà-ỹ0-9\\s]+$";

    // ID chỉ được là số nguyên dương, không được trùng Id
    public static boolean isValidId(int id) {
        return id > 0;

    }

    public static boolean isDuplicateId(int id) {
        try {
            // Nếu trả về null thì không trùng
            return BookBusniess.getBookById(id) != null;
        } catch (Exception e) {
            // Nếu có lỗi trong việc tìm kiếm ID, trả về false (không hợp lệ)
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
