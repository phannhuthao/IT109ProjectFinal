import ra.edu.presentation.Menu;

public class MainApplication {
    public static void main(String[] args) {
        try {
            Menu.run();
        } catch (Exception e) {
            System.out.println("Lỗi khi chạy chương trình: " + e.getMessage());
            e.printStackTrace();
        }
    }
}