package UI.Assets;

import UI.*;
import UI.Panel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class CustomButton extends javax.swing.JButton {

    // Variables protegidas
    protected static MainFrame mainFrame;
    protected boolean isMouseOver = false;

    // Constructores simples
    protected CustomButton() {
        super();
    }

    protected CustomButton(String text) {
        super(text);
    }
    
    protected CustomButton(Icon image) {
        super(image);
    }

    public static void setFrame(MainFrame mainFrame) {
        CustomButton.mainFrame = mainFrame;
    }

    // Metodos protegidos
    protected void addGeneralSettings() {

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        addMouseOverListener();

    }

    protected void addMouseOverListener() {
        
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

    protected Graphics2D mouseOverPaint(Graphics2D g2) {

        if (!isMouseOver) {
            g2.setColor(getBackground());
        } else {
            g2.setColor(getForeground());
        }
        return g2;

    }

    // ----------------------------------------------------------------------------------------------------

    public static class User extends CustomButton {

        // Variables estaticas
        public static final String PINK_PFP = "src\\UI\\Media\\Image\\pink.png";
        public static final String BLUE_PFP = "src\\UI\\Media\\Image\\blue.png";
        public static final String YELLOW_PFP = "src\\UI\\Media\\Image\\yellow.png";

        // Variables globales
        private String panelName;
        private Shape circle = new Ellipse2D.Double(0, 0, 140, 140);

        public User(String path) {

            super(new ImageIcon(path));

            // Configuracion
            setPreferredSize(new Dimension(140, 140));
            setBackground(Palette.DARK_GRAY);
            setForeground(Palette.WHITE);
            addGeneralSettings();

            if (path == PINK_PFP) {
                panelName = "Administracion";
            } else if (path == BLUE_PFP) {
                panelName = "Ventas";
            } else {
                panelName = "Membresias";
            }
            
            addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {

                    mainFrame.getTitleBar().getTitleLabel().setText(panelName);
                    mainFrame.getCardLayout().show(mainFrame.getPanelChanger(), panelName);
                    mainFrame.getTitleBar().getBackButton().setEnabled(true);

                }
                
            });

        }

        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2 = mouseOverPaint(g2);
            g2.fill(circle);

            super.paintComponent(g);

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static class TitleBar extends CustomButton {

        // Variables estaticas
        public static final int BACK = 1;
        public static final int CLOSE = 2;

        // Variables globales
        private Shape corner, icon;

        public TitleBar(int ID) {

            super();

            switch (ID) {

                case BACK:
                    corner = CustomShape.left;
                    icon = CustomShape.back;
                    setForeground(Palette.GRAY);
                    setEnabled(false);
                    break;

                case CLOSE:
                	corner = CustomShape.right;
                    icon = CustomShape.cross;
                    setForeground(Palette.RED);
                    break;

            }

            setPreferredSize(new Dimension(48, 32));
            setBackground(Palette.ALPHA_0);
            addGeneralSettings();

        }

        public void setMouseOver(boolean truth) {
            isMouseOver = truth;
        }

        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2 = mouseOverPaint(g2);
            g2.fill(corner);

            if (isEnabled()) {
                g2.setColor(Palette.WHITE);
                g2.translate(24, 16);
                g2.fill(icon);
            }

            super.paintComponent(g);

        }

        // Clase interna, contiene formas usadas para dibujar el icono de los botones
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

    // ----------------------------------------------------------------------------------------------------

    public static class Option extends CustomButton {

        // Variables globales
        private Shape round = new RoundRectangle2D.Double(0, 0, 180, 50, 30, 30);

        // Constructor base
        public Option(String text) {

            super();

            setText(text);
            setPreferredSize(new Dimension(180, 50));
            setBackground(Palette.BLACK);
            setFont(CustomFont.interBold);
            addGeneralSettings();

        }

        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            setForeground(Palette.GRAY);
            g2 = mouseOverPaint(g2);
            g2.fill(round);

            setForeground(Palette.WHITE);
            super.paintComponent(g);

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static class Decision extends CustomButton {

        // Variables globales
        private Shape round = new RoundRectangle2D.Double(0, 0, 120, 40, 30, 30);

        public Decision(String text) {

            super();

            setText(text);
            setPreferredSize(new Dimension(120, 40));
            setBackground(Palette.BLACK);
            setFont(CustomFont.interBold);
            addGeneralSettings();

        }

        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            setForeground(Palette.GRAY);
            g2 = mouseOverPaint(g2);
            g2.fill(round);

            setForeground(Palette.WHITE);
            super.paintComponent(g);

        }
        
    }

}
