package UI.Panel;

import UI.*;
import UI.Assets.CustomButton;
import UI.Assets.CustomFont;
import UI.Assets.Palette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class Admin extends javax.swing.JPanel {

    private JTable itemTable;
    private CustomButton addItem, editItem, deleteItem;
    
    public Admin(MainFrame mainFrame) {

        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        DefaultTableModel data = new DefaultTableModel();
        data.addColumn("Nombre");
        data.addColumn("ID");
        data.addColumn("Descripcion");
        data.addColumn("P. Compra");
        data.addColumn("P. Venta");
        data.addColumn("Descuento");
        data.addColumn("Stock");

        // Datos de prueba
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});
        data.addRow(new Object[] {"Leche", "001", "Descripcion", "23$", "42$", "0", "4"});

        itemTable = new JTable(data);
        itemTable.setFont(CustomFont.interRegular.deriveFont(14.0F));
        itemTable.setRowHeight(30);
        itemTable.setBackground(Palette.MAX_GRAY);
        itemTable.setForeground(Palette.WHITE);
        itemTable.setGridColor(Palette.GRID_GRAY);
        itemTable.setSelectionBackground(Palette.GRAY);
        itemTable.setSelectionForeground(Palette.WHITE);
        itemTable.setOpaque(false);

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
        addItem = new CustomButton.Option(CustomButton.Option.ADM_ADD);
        editItem = new CustomButton.Option(CustomButton.Option.ADM_EDIT);
        deleteItem = new CustomButton.Option(CustomButton.Option.ADM_DEL);

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

}
