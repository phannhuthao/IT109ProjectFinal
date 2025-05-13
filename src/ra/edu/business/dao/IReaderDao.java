package ra.edu.business.dao;

import java.io.Reader;
import java.util.List;

public interface IReaderDao {
    void createTable();

    List<Reader> getAllReaders();

    Reader getReaderById(int id);

    void addReaders(Reader reader);

    void updateReader(Reader reader);

    void deleteByIdReader(int id);

    List<Reader> searchByNameReader(String name);
}
