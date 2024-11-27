package UI.Assets;

import UI.*;
import UI.Assets.*;
import UI.Panel.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CustomButton extends javax.swing.JButton {
    
    // ID de botones
    public static final int BACK = 1;
    public static final int CLOSE = 2;
    public static final int OPTION = 3;
    public static final int USER = 4;

    // Variables globales
    private MainFrame mainFrame;
    private int ID;
    private boolean isMouseOver = false;
    private Shape shape;

    // Constructor basico
    public CustomButton(MainFrame mainFrame, int ID) {

        // Caracteristicas segun el ID usado
        this.ID = ID;
        switch (ID) {
            
            case 1:
                setPreferredSize(new Dimension(48, 32));
                setBackground(Palette.DARK_GRAY);
                setForeground(Palette.GRAY);
                shape = CustomShape.left;

                addActionListener(new ActionListener() {
            
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        setMouseOver(false);
                        setEnabled(false);
                        mainFrame.getTitleBar().getTitleLabel().setText("Market");
                        mainFrame.getCardLayout().show(mainFrame.getPanelChanger(), "Usuarios");

                    }
        
                });

                setEnabled(false);
                break;

            case 2:
                setPreferredSize(new Dimension(48, 32));
                setBackground(Palette.DARK_GRAY);
                setForeground(Palette.RED);
                shape = CustomShape.right;

                addActionListener(new ActionListener() {
            
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        mainFrame.dispose();
                        System.exit(0);

                    }
        
                });

                break;

            case 3:
            
                break;

        }

        // Caracteristicas generales
        addMouseOverListener();
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

    }

    // Constructor user button
    public CustomButton(MainFrame mainFrame, String path, String panelName) {

        super(new ImageIcon(path));
        ID = USER;

        // Configuracion general
        setPreferredSize(new Dimension(140, 140));
        shape = new Ellipse2D.Double(0, 0, 140, 140);

        addMouseOverListener();
        setBackground(Palette.DARK_GRAY);
        setForeground(Palette.WHITE);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        
        addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                mainFrame.getTitleBar().getTitleLabel().setText(panelName);
                mainFrame.getCardLayout().show(mainFrame.getPanelChanger(), panelName);
                mainFrame.getTitleBar().getBackButton().setEnabled(true);

            }
            
        });

    }

    private void addMouseOverListener() {
        
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

    private void setMouseOver(boolean truth) {
        isMouseOver = truth;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isMouseOver) {
            g2.setColor(getForeground());
        } else {
            g2.setColor(getBackground());
        }
        g2.fill(shape);

        if (ID == BACK || ID == CLOSE) {

            g2.translate(24, 16);
            g2.setColor(Palette.WHITE);
    
            if (shape == CustomShape.left && isEnabled()) {
                g2.fill(CustomShape.back);
            } else if (shape == CustomShape.right) {
                g2.fill(CustomShape.cross);
            }

        }

        super.paintComponent(g);

    }

    // Clase interna, contiene formas usadas para renderizar los botones
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

}
