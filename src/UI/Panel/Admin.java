package UI.Panel;

import UI.*;
import UI.Assets.CustomButton;
import UI.Assets.CustomFont;
import UI.Assets.Palette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Admin extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private DefaultTableModel data;
    private JTable itemTable;

    private CustomButton.Option addItem, editItem, deleteItem;
    
    public Admin(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        data = new DefaultTableModel();
        data.addColumn("Nombre");
        data.addColumn("ID");
        data.addColumn("Descripcion");
        data.addColumn("P. Compra");
        data.addColumn("P. Venta");
        data.addColumn("Descuento");
        data.addColumn("Stock");

        // Dato de prueba
        data.addRow(new Object[] {"Leche", "001", "Lala entera", "23$", "42$", "0%", "4"});
        data.addRow(new Object[] {"Maiz", "002", "Para animales de granja", "47$", "65$", "0%", "9"});
        data.addRow(new Object[] {"Llanta", "003", "R23 - 64''", "180$", "299$", "24%", "2"});

        itemTable = new JTable(data);
        itemTable.setFont(CustomFont.interRegular.deriveFont(14.0F));
        itemTable.setRowHeight(30);
        itemTable.setBackground(Palette.MAX_GRAY);
        itemTable.setForeground(Palette.WHITE);
        itemTable.setGridColor(Palette.GRID_GRAY);
        itemTable.setSelectionBackground(Palette.GRAY);
        itemTable.setSelectionForeground(Palette.WHITE);
        itemTable.setOpaque(false);
        itemTable.setRowSelectionInterval(0, 0);
        
        itemTable.getTableHeader().setReorderingAllowed(false);
        itemTable.getTableHeader().setResizingAllowed(false);
        itemTable.getTableHeader().setFont(CustomFont.interMedium.deriveFont(14.0F));
        itemTable.getTableHeader().setBackground(Palette.GRID_GRAY);
        itemTable.getTableHeader().setForeground(Palette.WHITE);
        itemTable.getTableHeader().setOpaque(false);

        JScrollPane scrollPanel = new JScrollPane(itemTable);
        scrollPanel.setPreferredSize(new Dimension(720, 398));
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBorder(BorderFactory.createLineBorder(Palette.GRID_GRAY));
        scrollPanel.setOpaque(false);
        
        // Botones
        addItem = new CustomButton.Option("Añadir item");
        addItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                new FieldFrame.AddItem(Admin.this);

            }

        });

        editItem = new CustomButton.Option("Editar item");
        editItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = itemTable.getSelectedRow();
                if (row != -1) {
                    new FieldFrame.EditItem(Admin.this, row);
                }

            }

        });

        deleteItem = new CustomButton.Option("Eliminar item");
        deleteItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = itemTable.getSelectedRow();
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
        add(scrollPanel, gbc);

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

    public DefaultTableModel getTableModel() {
        return data;
    }

    public JTable getTable() {
        return itemTable;
    }

}
