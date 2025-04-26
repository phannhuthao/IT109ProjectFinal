package ra.edu.business.service;

import ra.edu.business.entity.Reader;
import ra.edu.business.model.BookBusniess;
import ra.edu.business.model.ReaderBusiness;

import java.sql.SQLException;
import java.util.List;

public class ReaderService {

    public List<Reader> getAllReaders() throws SQLException {
        return ReaderBusiness.getAllReaders();
    }

}
