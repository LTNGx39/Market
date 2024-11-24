package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class TitleBar extends javax.swing.JPanel {

    private CustomButton back, close;
    private JLabel title;
    
    public TitleBar(Frame frame) {

        setPreferredSize(new Dimension(frame.getUsableWidth(), 32));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        back = new CustomButton(Palette.GRAY);
        back.setCorner(Corner.left);
        close = new CustomButton(Palette.RED);
        close.setCorner(Corner.right);

        title = new JLabel("Market") {
            {
                setPreferredSize(new Dimension(frame.getUsableWidth() - 96, 32));
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(CustomFont.interMedium.deriveFont(16.0F));
                setForeground(Palette.WHITE);
            }
        };

        // Añadir los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(back, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(title);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(close);

        // Listener
        MouseAdapter drag = new MouseAdapter() {

            int mouseX, mouseY;

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;

                frame.setLocation(x, y);
            } 
        };
        addMouseListener(drag);
        addMouseMotionListener(drag);

    }

    public void setTitle(String text) {
        title.setText(text);
    }

}

class CustomButton extends javax.swing.JButton {

    boolean isMouseOver;
    Shape corner;

    public CustomButton(Color baseColor) {

        super();
    
        setPreferredSize(new Dimension(48, 32));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBackground(Palette.DARK_GRAY);
        setForeground(baseColor);

        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isMouseOver = false;
            }

        });

    }

    public void setCorner(Shape corner) {
        this.corner = corner;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isMouseOver) {
            g2.setColor(getForeground());
        } else {
            g2.setColor(getBackground());
        }
        g2.fill(corner);

    }

}

class Corner {

    public static Shape left, right;

    static {

        // Left corner
        Area leftA = new Area(new RoundRectangle2D.Double(0, 0, 48, 32, 20, 20));
        leftA.add(new Area(new Rectangle2D.Double(24, 0, 24, 32)));
        leftA.add(new Area(new Rectangle2D.Double(0, 16, 48,16)));
        left = leftA;

        // Right corner
        Area rightA = new Area(new RoundRectangle2D.Double(0, 0, 48, 32, 20, 20));
        rightA.add(new Area(new Rectangle2D.Double(0, 0, 24, 32)));
        rightA.add(new Area(new Rectangle2D.Double(0, 16, 48,16)));
        right = rightA;

    }

}
