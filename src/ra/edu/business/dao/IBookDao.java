package ra.edu.business.dao;

import ra.edu.business.entity.Book;

import java.util.List;

public interface IBookDao {
    void createTable();
    List<Book> getAllBooks();
    Book getBookById(int id);
    boolean addBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBookById(int id);
    List<Book> searchBookByName(String name);
    List<Book> sortBooksByTitleAsc();
    List<Book> sortBooksByTitleDesc();
}
