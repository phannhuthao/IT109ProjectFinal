package ra.edu.business.model;

import ra.edu.business.dao.BookDao;
import ra.edu.business.entity.Book;

import java.util.Comparator;
import java.util.List;

public class BookBusniess {
    private static final BookDao bookDao = new BookDao();

    public static List<Book> getAllBooks() {
        return BookDao.getAllBooks();
    }

    public static boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    public static boolean updateBook(Book book) {
        return BookDao.updateBook(book);
    }

    public static Book getBookById(int id) {
        return BookDao.getBookById(id);
    }

    public static boolean deleteBookById(int id) {
        return BookDao.deleteBookById(id);
    }

    public static List<Book> searchBooksByName(String name) {
        return BookDao.searchBookByName(name);
    }

    public static List<Book> sortBooksByTitleAsc() {
        return BookDao.sortBooksByTitleAsc();
    }

    public static List<Book> sortBooksByTitleDesc() {
        return BookDao.sortBooksByTitleDesc();
    }

    public static List<Book> sortBooksByTitle() {
        List<Book> books = getAllBooks();
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }


}
