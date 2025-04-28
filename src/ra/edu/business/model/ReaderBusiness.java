package ra.edu.business.model;

import ra.edu.business.dao.ReaderDao;
import ra.edu.business.entity.Book;
import ra.edu.business.entity.Reader;


import java.util.List;

public class ReaderBusiness {
    private static final ReaderDao readerDao = new ReaderDao();

    public static List<Reader> getAllReaders() {
        return ReaderDao.getAllReaders();
    }
    public static Reader getReaderById(int id) {
        return ReaderDao.getReaderById(id);
    }

    public static void addReader(Reader reader) {
        new ReaderDao().addReaders(reader);
    }
    public static void deleteByIdReader(int id) {
        readerDao.deleteByIdReader(id);
    }

    public static List<Reader> searchReaderByName(String name) {
        return ReaderDao.searchByNameReader(name);
    }

    }
