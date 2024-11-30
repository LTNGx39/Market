package UI.Panel;

import UI.*;
import javax.swing.*;

import UI.MainFrame;
import UI.Assets.CustomButton;
import UI.Assets.CustomField;
import UI.Assets.CustomLabel;
import UI.Assets.Palette;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FieldFrame extends javax.swing.JFrame {

    private static MainFrame mainFrame;

    public static class AddItem extends FieldFrame {

        private CustomLabel title;
        private CustomField name, id, desc, buy, sell, discount, stock;
        private CustomButton save, cancel;

        public AddItem() {

            Shadow shadow = new Shadow(mainFrame, this);
            setSize(322, 407);
            setGeneralSettings();

            // Configuracion de componentes
            title = new CustomLabel.Bold("Nuevo item", SwingConstants.CENTER, 20.0F);
            title.setPreferredSize(new Dimension(200, 30));
            
            name = new CustomField("Nombre:");
            id = new CustomField("ID:");
            desc = new CustomField("Descripcion:");
            buy = new CustomField("P. Compra:");
            sell = new CustomField("P. Venta:");
            discount = new CustomField("Descuento:");
            stock = new CustomField("Stock:");

            save = new CustomButton.Decision(CustomButton.Decision.ACCEPT);
            cancel = new CustomButton.Decision(CustomButton.Decision.CANCEL);

            // Adicion de los componentes
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(title, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 10, 0);
            add(name, gbc);

            gbc.gridy = 2;
            add(id, gbc);

            gbc.gridy = 3;
            add(desc, gbc);

            gbc.gridy = 4;
            add(buy, gbc);

            gbc.gridy = 5;
            add(sell, gbc);

            gbc.gridy = 6;
            add(discount, gbc);

            gbc.gridy = 7;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(stock, gbc);

            gbc.gridy = 8;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 10, 0, 20);
            add(save, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(cancel, gbc);

            setVisible(true);

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static class EditItem extends FieldFrame {

        private CustomLabel title;
        private CustomField name, id, desc, buy, sell, discount, stock;
        private CustomButton save, cancel;

        public EditItem() {

            Shadow shadow = new Shadow(mainFrame, this);
            setSize(322, 407);
            setGeneralSettings();

            // Configuracion de componentes
            title = new CustomLabel.Bold("Editar item", SwingConstants.CENTER, 20.0F);
            title.setPreferredSize(new Dimension(200, 30));
            
            name = new CustomField("Nombre:");
            id = new CustomField("ID:");
            desc = new CustomField("Descripcion:");
            buy = new CustomField("P. Compra:");
            sell = new CustomField("P. Venta:");
            discount = new CustomField("Descuento:");
            stock = new CustomField("Stock:");

            save = new CustomButton.Decision(CustomButton.Decision.ACCEPT);
            cancel = new CustomButton.Decision(CustomButton.Decision.CANCEL);

            // Adicion de los componentes
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(title, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 10, 0);
            add(name, gbc);

            gbc.gridy = 2;
            add(id, gbc);

            gbc.gridy = 3;
            add(desc, gbc);

            gbc.gridy = 4;
            add(buy, gbc);

            gbc.gridy = 5;
            add(sell, gbc);

            gbc.gridy = 6;
            add(discount, gbc);

            gbc.gridy = 7;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(stock, gbc);

            gbc.gridy = 8;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 10, 0, 20);
            add(save, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(cancel, gbc);

            setVisible(true);

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
        
    }

    protected class Shadow extends javax.swing.JFrame {

        private FieldFrame fieldFrame;

        public Shadow(MainFrame mainFrame, FieldFrame fieldFrame) {
    
            this.fieldFrame = fieldFrame;
            setSize(mainFrame.getWidth() - 2, mainFrame.getHeight() - 2);
            setUndecorated(true);

            int blackInt = Palette.BLACK.getRed();
            setBackground(new Color(blackInt, blackInt, blackInt, 120));
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            setLocationRelativeTo(mainFrame);
            setType(Type.UTILITY);

            addMouseListener(new MouseAdapter() {
                
                @Override
                public void mousePressed(MouseEvent e) {

                    dispose();
                    fieldFrame.dispose();

                }

            });

            setVisible(true);
    
        }
    
    }

    protected class Pane extends javax.swing.JPanel {

        public Pane() {

            setBorder(BorderFactory.createEmptyBorder(16, 21, 21, 21));
            setLayout(new GridBagLayout());
    
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
