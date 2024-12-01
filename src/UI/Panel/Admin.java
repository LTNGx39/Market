package UI.Panel;

import UI.*;
import UI.Assets.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Admin extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomScroll scroll;

    private CustomButton.Option addItem, editItem, deleteItem;
    
    public Admin(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        scroll = new CustomScroll(720, 398, new Object[] {"Nombre", "ID", "Descripcion", "P. Compra", "P. Venta", "Descuento", "Stock"});

        // Dato de prueba
        scroll.getModel().addRow(new Object[] {"Leche", "001", "Lala entera", "23$", "42$", "0%", "4"});
        scroll.getModel().addRow(new Object[] {"Maiz", "002", "Para animales de granja", "47$", "65$", "0%", "9"});
        scroll.getModel().addRow(new Object[] {"Llanta", "003", "R23 - 64''", "180$", "299$", "24%", "2"});
        
        // Botones
        addItem = new CustomButton.Option(180, 50, "Añadir item");
        addItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                new FieldFrame.AddItem(Admin.this);

            }

        });

        editItem = new CustomButton.Option(180, 50, "Editar item");
        editItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = scroll.getTable().getSelectedRow();
                if (row != -1) {
                    new FieldFrame.EditItem(Admin.this, row);
                }

            }

        });

        deleteItem = new CustomButton.Option(180, 50, "Eliminar item");
        deleteItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = scroll.getTable().getSelectedRow();
                if (row != -1) {
                    new FieldFrame.DeleteItem(Admin.this, row);
                }

            }

        });

        // Agregar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(scroll, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 60, 0, 0);
        add(addItem, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 30, 0, 30);
        add(editItem, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 60);
        add(deleteItem, gbc);

    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public CustomScroll getScroll() {
        return scroll;
    }

}
