package UI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class CustomFont {
    
    public static Font interMedium;
    public static Font interSemiBold;
    public static Font interBold;

    static {
        try {
           interMedium = Font.createFont(Font.TRUETYPE_FONT, new File("src/Media/Font/Inter_Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            interMedium = new Font("Arial", Font.PLAIN, 18);
        }

        try {
            interBold = Font.createFont(Font.TRUETYPE_FONT, new File("src/Media/Font/Inter_Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            interBold = new Font("Arial", Font.PLAIN, 18);
        }

        try {
            interSemiBold = Font.createFont(Font.TRUETYPE_FONT, new File("src/Media/Font/Inter_SemiBold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            interSemiBold = new Font("Arial", Font.PLAIN, 18);
        }
    }
}
