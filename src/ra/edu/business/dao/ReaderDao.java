package ra.edu.business.dao;

import ra.edu.business.config.ColorCode;
import ra.edu.business.config.DatabaseConnect;
import ra.edu.business.entity.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReaderDao {
    public void createTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS reader (
          id INT PRIMARY KEY AUTO_INCREMENT,
          name VARCHAR(100),
          gender BOOLEAN,
          birthdate DATE,
          phone VARCHAR(20),
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
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                Date birthdate = rs.getDate("birthdate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                Reader reader = new Reader(id,name, gender, birthdate, phone, email);
                list.add(reader);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy xuất danh sách người đọc: " + e.getMessage());
        }
        return list;
    }

    public static Reader getReaderById(int id) {
         Reader reader = null;
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM reader WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                Date birthdate = rs.getDate("birthdate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                reader = new Reader(id, name, gender, birthdate, phone, email);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi truy xuất thông tin người đọc theo ID: " + e.getMessage() + ColorCode.RESET);
        }
        return reader;
    }

    public void addReaders(Reader reader) {
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "INSERT INTO reader (id, name, gender, birthdate, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reader.getId());
            ps.setString(2, reader.getUsername());
            ps.setBoolean(3, reader.getSex());
            ps.setDate(4, new java.sql.Date(reader.getBirthdate().getTime()));
            ps.setString(5, reader.getPhone());
            ps.setString(6, reader.getEmail());

            ps.executeUpdate();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(ColorCode.RED +"Lỗi không thêm người đọc: " + e.getMessage() + ColorCode.RESET);
        }
    }

    public static void updateReader(Reader reader) {
        try (Connection con = DatabaseConnect.getConnection()) {
            String sql = "UPDATE reader SET name = ?, gender = ?, birthdate = ?, phone = ?, email = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, reader.getUsername());
            ps.setBoolean(2, reader.getSex());
            ps.setDate(3, new java.sql.Date(reader.getBirthdate().getTime()));
            ps.setString(4, reader.getPhone());
            ps.setString(5, reader.getEmail());
            ps.setInt(6, reader.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(ColorCode.RED + "Lỗi khi cập nhật người đọc: " + e.getMessage() + ColorCode.RESET);
        }
    }


    public void deleteByIdReader(int id) {
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "DELETE FROM reader WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(ColorCode.GREEN + "Xóa người đọc thành công!" + ColorCode.RESET);
            } else {
                System.out.println(ColorCode.RED + "Không tìm thấy người đọc để xóa!" + ColorCode.RESET);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(ColorCode.RED + "Lỗi không xóa người đọc: " + e.getMessage() + ColorCode.RESET);
        }
    }

    public static List<Reader> searchByNameReader(String name) {
        List<Reader> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM reader WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            // Thêm '%' để tìm kiếm chứa từ khóa ở bất kỳ đâu trong tên
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String readerName = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                Date birthdate = rs.getDate("birthdate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                Reader reader = new Reader(id, readerName, gender, birthdate, phone, email);
                list.add(reader);
            }

            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(ColorCode.RED + "Lỗi khi tìm kiếm người đọc theo tên: " + e.getMessage() + ColorCode.RESET);
        }
        return list;
    }


}
