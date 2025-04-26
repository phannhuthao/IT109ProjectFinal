package ra.edu.business.dao;

import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Book;
import ra.edu.business.entity.Reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReaderDao {
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS reader (
                id INT PRIMARY KEY AUTO_INCREMENT,
                username VARCHAR(100),
                sex BOOLEAN,
                birthdate DATE,
                phone INT,
                email VARCHAR(100)
            );
        """;
        try (Connection conn = DatabaseConnect.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng Reader đã được tạo.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Reader> getAllReaders() {
        List<Reader> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Reader";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                boolean sex = rs.getBoolean("sex");
                Date birthdate = rs.getDate("birthdate");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                Reader reader = new Reader(id,username, sex, birthdate, phone, email);
                list.add(reader);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất danh sách người đọc: " + e.getMessage());
        }
        return list;
    }
}
