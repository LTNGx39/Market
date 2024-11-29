package UI.Panel;

import UI.*;
import javax.swing.*;

import UI.MainFrame;
import UI.Assets.CustomButton;
import UI.Assets.CustomLabel;
import UI.Assets.Palette;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FieldFrame extends javax.swing.JFrame {

    private static MainFrame mainFrame;

    public static class AddItem extends FieldFrame {

        private CustomLabel title, nameTxt, idTxt, descTxt, buyTxt, sellTxt, discountTxt, stockTxt;
        // private CustomField name, id, desc, buy, sell, discount, stock;
        private CustomButton calcel, save;

        public AddItem() {

            Shadow shadow = new Shadow(mainFrame);
            setSize(300, 383);
            setGeneralSettings();

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static void setFrame(MainFrame mainFrame) {
        FieldFrame.mainFrame = mainFrame;
    }

    protected void setGeneralSettings() {

        setUndecorated(true);
        setLocationRelativeTo(mainFrame);
        setContentPane(new Pane());
        setBackground(Palette.ALPHA_0);
        setVisible(true);
        
    }

    protected class Shadow extends javax.swing.JFrame {

        public Shadow(MainFrame mainFrame) {
    
            setSize(mainFrame.getWidth() - 2, mainFrame.getHeight() - 2);
            setUndecorated(true);

            int blackInt = Palette.BLACK.getRed();
            setBackground(new Color(blackInt, blackInt, blackInt, 120));
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            setLocationRelativeTo(mainFrame);
            setType(Type.UTILITY);

            setVisible(true);
    
        }
    
    }

    protected class Pane extends javax.swing.JPanel {

        public Pane() {

            setBorder(BorderFactory.createEmptyBorder(16, 21, 21, 21));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Borde
            g2.setColor(Palette.GRAY);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 22, 22));

            // Area interna
            g2.setColor(Palette.DARK_GRAY);
            g2.fill(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, 20, 20));

        }
    
    }

}
