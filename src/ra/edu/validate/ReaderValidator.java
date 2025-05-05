package ra.edu.validate;

import ra.edu.business.model.ReaderBusiness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ReaderValidator {
    private static final String NAME_REGEX = "^[a-zA-ZÀ-Ỹà-ỹ0-9\\s]+$";
    private static final String BIRTHDAY_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    // ID chỉ được là số nguyên dương
    public static boolean isValidateId(int id) {
        return id > 0;
    }

    public static boolean isDuplicateId(int id) {
        try {
            // Nếu trả về null thì không trùng
            return ReaderBusiness.getReaderById(id) != null;
        } catch (Exception e) {
            // Nếu có lỗi trong việc tìm kiếm ID, trả về false (không hợp lệ)
            return false;
        }
    }

    // Tên người đọc không được có kí tự đặc biệt
    public static boolean isValidatedReader(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(NAME_REGEX, username);
    }

    // Ngày sinh phải có định dạng là dd/mm/yyyy
    public static Date isValidateBirthday(String birthday) {
        if (birthday == null || birthday.trim().isEmpty()) {
            return null;
        }
        if (!Pattern.matches(BIRTHDAY_REGEX, birthday)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(birthday);
        } catch (ParseException e) {
            return null;
        }
    }


    // email phải đúng định dạng email (không có kí tự đặc biệt lạ)
    public static boolean isValidateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
