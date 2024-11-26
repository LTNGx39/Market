package UI.Panel;

import javax.swing.*;

import UI.*;
import UI.Assets.CustomFont;
import UI.Assets.Palette;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class TitleBar extends javax.swing.JPanel {

    private CornerButton back, close;
    private JLabel title;

    private boolean inMenu = true;
    
    public TitleBar(MainFrame frame) {

        setPreferredSize(new Dimension(frame.getUsableWidth(), 32));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        back = new CornerButton(this, Palette.GRAY);
        back.setCorner(CustomShape.left);
        back.setEnabled(false);
        back.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.getCard().show(frame.getPanelChanger(), "users");

                getLeftButton().setIsMouseOver(false);
                getLeftButton().setEnabled(false);
                setTitle("Market");
                inMenu = true;

            }

        });

        close = new CornerButton(this, Palette.RED);
        close.setCorner(CustomShape.right);
        close.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                System.exit(0);

            }

        });

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

    public boolean getInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public CornerButton getLeftButton() {
        return back;
    }

}

class CornerButton extends javax.swing.JButton {

    private TitleBar titleBar;

    private boolean isMouseOver;
    private Shape corner;

    public CornerButton(TitleBar titleBar, Color baseColor) {

        this.titleBar = titleBar;
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

    public void setIsMouseOver(boolean value) {
        isMouseOver = value;
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

        g2.translate(24, 16);
        g2.setColor(Palette.WHITE);

        if (corner == CustomShape.left) {
            if (!titleBar.getInMenu()) {
                g2.fill(CustomShape.back);
            }
        } else {
            g2.fill(CustomShape.cross);
        }

    }

}

class CustomShape {

    public static Shape left, right, back, cross;

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

        // Back
        Area backA = new Area(new Rectangle2D.Double(2, 6, 8, 2));
        Area backA1 = new Area(new Rectangle2D.Double(0, 0, 2, 7));
        Area backA2 = new Area(new Rectangle2D.Double(0, 7, 2, 7));

        AffineTransform backT = new AffineTransform();
        backT.rotate(Math.toRadians(45), 0, 7);
        backA1.transform(backT);
        backT.rotate(Math.toRadians(-90), 0, 7);
        backA2.transform(backT);
        
        backA.add(backA1);
        backA.add(backA2);
        backT.rotate(Math.toRadians(45), 0, 7);
        backT.translate(-5, -7);
        backA.transform(backT);
        back = backA;

        // Cross
        Area crossA = new Area(new Rectangle(0, 5, 12, 2));
        crossA.add(new Area(new Rectangle(5, 0, 2, 12)));

        AffineTransform crossT = new AffineTransform();
        crossT.translate(-6, -6);
        crossT.rotate(Math.toRadians(45), 6, 6);
        crossA.transform(crossT);
        cross = crossA;

    }

}
