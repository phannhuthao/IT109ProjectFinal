package ra.edu.business.model;

import ra.edu.business.dao.BorrowDao;
import ra.edu.business.entity.Borrow;

import java.util.List;
import java.util.Scanner;

public class BorrowBusniess {
    private static final BorrowDao borrowDao = new BorrowDao();

    public static List<Borrow> getAllBorrows() {
        return BorrowDao.getBorrow();
    }

    public static boolean addBorrow(Scanner sc) {
        return  BorrowBusniess.addBorrow(sc);
    }

    public static Borrow getBorrowById(int id) {
        return BorrowDao.getBorrowById(id);
    }


}

