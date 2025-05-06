package ra.edu.business.model;
;
import ra.edu.business.dao.BorrowDetailsDao;
import ra.edu.business.entity.BorrowDetails;

import java.util.List;

public class BorrowDetailsBusniess {
    private static final BorrowDetailsDao borrowDetailsDao = new BorrowDetailsDao();

    public static List<BorrowDetails> getAllBorrowsDetails() {
        return BorrowDetailsDao.getBorrowDetails();
    }

    public static boolean addBorrowDetails(BorrowDetails borrowDetails) {
        return BorrowDetailsDao.addBorrowDetails(borrowDetails);
    }
}
