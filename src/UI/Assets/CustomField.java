package UI.Assets;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import UI.MainFrame;

public class CustomField extends javax.swing.JPanel{
    
    private MainFrame mainFrame;
    private JTextField field;
    
    public CustomField(String label) {

        super();

        setPreferredSize(new Dimension(280, 30));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Configuracion de componentes
        JLabel text = new CustomLabel.Medium(label, SwingConstants.LEFT, 16.0F);
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        field = new JTextField() {
            {
                setBackground(Palette.BLACK);
                setForeground(Palette.WHITE);
                setSelectionColor(Palette.GRAY);
                setSelectedTextColor(getForeground());
                setCaretColor(getSelectionColor());
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                setFont(CustomFont.interMedium);
            }
            @Override
            public void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));

                super.paintComponent(g);

            }
        };

        add(text);
        add(field);

    }

    public JTextField getField() {
        return field;
    }

    public void setFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

}
