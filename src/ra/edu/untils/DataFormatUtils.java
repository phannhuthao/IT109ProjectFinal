package ra.edu.untils;

import java.util.ArrayList;
import java.util.List;

public class DataFormatUtils {

    public static List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        // Tạo một biến start đánh dấu vị trí bắt đầu của chuỗi con cần lấy
        int start = 0;
        // Vòng lặp while tiếp tục chạy miễn là start còn nhỏ hơn độ dài của chuỗi (text.length()),
        // điều này giúp đảm bảo rằng tất cả phần chuỗi sẽ được chia ra thành các đoạn nhỏ mà không bị thiếu.
        //Sử dụng while giúp cho điều kiện dừng phụ thuộc trực tiếp vào giá trị của start,
        // mà không cần phải tính toán trước số lần lặp như trong vòng for.
        while (start < text.length()) {
            int end = Math.min(start + maxLength, text.length());
            result.add(text.substring(start, end));
            start += maxLength;
        }
        return result;
    }
}
