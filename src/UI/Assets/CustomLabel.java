package UI.Assets;

import UI.*;
import UI.Assets.CustomFont;
import UI.Panel.*;

import javax.swing.*;

import java.awt.*;

public class CustomLabel extends javax.swing.JLabel {

    // ID de labels
    public static final int REGULAR = 1;
    public static final int MEDIUM = 2;
    public static final int SEMI_BOLD = 3;
    public static final int BOLD = 4;

    // Variables globales
    private MainFrame mainFrame;
    private Font font;
    
    public CustomLabel(MainFrame mainFrame, int ID, String text, Float size) {

        super(text);
        this.mainFrame = mainFrame;

        // Configuracion segun ID
        switch (ID) {

            case 1:
                font = CustomFont.interRegular;
                break;

            case 2:
                font = CustomFont.interMedium;
                break;

            case 3:
                font = CustomFont.interSemiBold;
                break;

            case 4:
                font = CustomFont.interBold;
                break;

        }

        // Caracteristicas generales
        setFont(font.deriveFont(size));
        setForeground(Palette.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);

    }

}
