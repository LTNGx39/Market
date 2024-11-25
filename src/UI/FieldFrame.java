package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FieldFrame extends javax.swing.JFrame {

    public static final int ADD_ITEM = 1;
    public static final int EDIT_ITEM = 2;
    public static final int DELETE_ITEM = 3;
    
    public FieldFrame(Frame frame, int type) {

        Shadow shadow = new Shadow(frame);
        shadow.setVisible(true);

        switch (type) {

            case 1:
                
                break;
        
            case 2:

                break;

        }

    }

}

class Shadow extends javax.swing.JFrame {

    public Shadow(Frame frame) {

        setSize(frame.getWidth() - 2, frame.getHeight() - 2);
        setUndecorated(true);
        int blackInt = Palette.BLACK.getRed();
        setBackground(new Color(blackInt, blackInt, blackInt, 120));
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(frame);
        setType(Type.UTILITY);

    }

}
