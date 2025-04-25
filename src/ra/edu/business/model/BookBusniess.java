package ra.edu.business.model;

import ra.edu.business.dao.BookDao;
import ra.edu.business.entity.Book;

import java.util.List;

public class BookBusniess {
    private static BookDao bookDao = new BookDao();

    public static List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public static boolean addBook(Book book) {
        return bookDao.addBook(book);
    }
}
