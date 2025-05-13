package ra.edu.business.model;

import ra.edu.business.dao.BookDaoImpl;
import ra.edu.business.entity.Book;

import java.util.Comparator;
import java.util.List;

public class BookBusniess {
    private static final BookDaoImpl bookDao = new BookDaoImpl();

    public static List<Book> getAllBooks() {
        return BookDaoImpl.getAllBooks();
    }

    public static boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    public static boolean updateBook(Book book) {
        return BookDaoImpl.updateBook(book);
    }

    public static Book getBookById(int id) {
        return BookDaoImpl.getBookById(id);
    }

    public static boolean deleteBookById(int id) {
        return BookDaoImpl.deleteBookById(id);
    }

    public static List<Book> searchBooksByName(String name) {
        return BookDaoImpl.searchBookByName(name);
    }

    public static List<Book> sortBooksByTitleAsc() {
        return BookDaoImpl.sortBooksByTitleAsc();
    }

    public static List<Book> sortBooksByTitleDesc() {
        return BookDaoImpl.sortBooksByTitleDesc();
    }

    public static List<Book> sortBooksByTitle() {
        List<Book> books = getAllBooks();
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }


}
