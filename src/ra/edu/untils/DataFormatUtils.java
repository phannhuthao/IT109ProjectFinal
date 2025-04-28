package ra.edu.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatUtils {
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
