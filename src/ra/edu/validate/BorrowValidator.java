package ra.edu.validate;

import ra.edu.business.model.BorrowBusniess;

public class BorrowValidator {
    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isDuplicateId(int id) {
        try {
            // Nếu trả về null thì không trùng
            return BorrowBusniess.getBorrowById(id) != null;
        } catch (Exception e) {
            // Nếu có lỗi trong việc tìm kiếm ID, trả về false (không hợp lệ)
            return false;
        }
    }


}
