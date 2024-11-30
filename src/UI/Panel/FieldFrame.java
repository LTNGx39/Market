package UI.Panel;

import UI.*;
import javax.swing.*;
import javax.swing.table.TableModel;

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
    protected Shadow shadow;

    public static class AddItem extends FieldFrame {

        private CustomLabel title;
        private CustomField name, id, desc, buy, sell, discount, stock;
        private CustomButton save, cancel;

        public AddItem() {

            shadow = new Shadow(mainFrame, this);
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

            save = new CustomButton.Decision(this, CustomButton.Decision.ACCEPT);
            cancel = new CustomButton.Decision(this, CustomButton.Decision.CANCEL);

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
            add(cancel, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(save, gbc);

            setVisible(true);

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static class EditItem extends FieldFrame {

        private TableModel data;
        private CustomLabel title;
        private CustomField name, id, desc, buy, sell, discount, stock;
        private CustomButton save, cancel;

        public EditItem(int row) {

            shadow = new Shadow(mainFrame, this);
            setSize(322, 407);
            setGeneralSettings();
            data = mainFrame.getAdmin().getTable().getModel();

            // Configuracion de componentes
            title = new CustomLabel.Bold("Editar item", SwingConstants.CENTER, 20.0F);
            title.setPreferredSize(new Dimension(200, 30));
            Object[] rowData = getRowData(data, row);
            
            name = new CustomField("Nombre:");
            name.getField().setText("" + data.getValueAt(row, 0));
            id = new CustomField("ID:");
            id.getField().setText("" + data.getValueAt(row, 1));
            desc = new CustomField("Descripcion:");
            desc.getField().setText("" + data.getValueAt(row, 2));
            buy = new CustomField("P. Compra:");
            buy.getField().setText("" + data.getValueAt(row, 3));
            sell = new CustomField("P. Venta:");
            sell.getField().setText("" + data.getValueAt(row, 4));
            discount = new CustomField("Descuento:");
            discount.getField().setText("" + data.getValueAt(row, 5));
            stock = new CustomField("Stock:");
            stock.getField().setText("" + data.getValueAt(row, 6));

            save = new CustomButton.Decision(this, CustomButton.Decision.ACCEPT);
            cancel = new CustomButton.Decision(this,CustomButton.Decision.CANCEL);

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
            add(cancel, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(save, gbc);

            setVisible(true);

        }

        public Object[] getRowData(TableModel model, int row) {

            Object[] data = new Object[model.getColumnCount()];

            for (int col = 0; col < model.getColumnCount(); col++) {
                data[col] = model.getValueAt(row, col);
            }

            return data;

        }

    }

    // ----------------------------------------------------------------------------------------------------

    public static class DeleteItem extends FieldFrame {

        private TableModel model;
        private CustomLabel title, text1, text2;
        private CustomButton cancel, accept;

        public DeleteItem(int row) {

            shadow = new Shadow(mainFrame, this);
            setSize(322, 202);
            setGeneralSettings();
            setLayout(new GridBagLayout());
            model = mainFrame.getAdmin().getTable().getModel();

            title = new CustomLabel.Bold("Eliminar item", SwingConstants.CENTER, 20.0F);
            title.setPreferredSize(new Dimension(200, 30));
            text1 = new CustomLabel.Semi("¿Esta seguro que quiere eliminar", SwingConstants.CENTER, 16.0F);
            text1.setPreferredSize(new Dimension(280, 30));
            text1.setVerticalAlignment(SwingConstants.BOTTOM);
            text2 = new CustomLabel.Semi("el producto \"" + model.getValueAt(row, 0) + "\"?", SwingConstants.CENTER, 16.0F);
            text2.setPreferredSize(new Dimension(280, 30));
            text2.setVerticalAlignment(SwingConstants.TOP);

            cancel = new CustomButton.Decision(this, CustomButton.Decision.CANCEL);
            accept = new CustomButton.Decision(this, CustomButton.Decision.ACCEPT);

            // Adicion de los componentes
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(title, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(text1, gbc);

            gbc.gridy = 2;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(text2, gbc);

            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 10, 0, 10);
            add(cancel, gbc);

            gbc.gridx = 1;
            add(accept, gbc);

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
        setType(Type.UTILITY);
        
    }

    public JFrame getShadow() {
        return shadow;
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
