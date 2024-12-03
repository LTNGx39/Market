package UI.Assets;

import UI.Assets.*;
import UI.Panel.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;

public class CustomScroll extends javax.swing.JScrollPane {

    private DefaultTableModel model;
    private JTable table;
    
    public CustomScroll(int width, int height, DefaultTableModel modelReaded) {

        super();

        setPreferredSize(new Dimension(width, height));

        // Modelo de datos
        model = modelReaded;

        // Tabla base
        table = new JTable(model);
        table.setBackground(Palette.GRID_BLACK);
        table.setForeground(Palette.WHITE);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setFont(CustomFont.interMedium.deriveFont(14.0F));
        table.setRowHeight(30);
        table.setGridColor(Palette.GRID_GRAY);
        table.setSelectionBackground(Palette.GRAY);
        table.setSelectionForeground(Palette.WHITE);
        table.setGridColor(Palette.GRID_GRAY);
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Borde a los lados
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

                return c;
            }

        };

        // Aplicar el renderizador a las celdas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        // Configuracion del header
        JTableHeader header = new JTableHeader(table.getColumnModel());

        DefaultTableCellRenderer headerRender = new DefaultTableCellRenderer();
        headerRender.setHorizontalAlignment(SwingConstants.CENTER);
        headerRender.setBackground(Palette.GRID_GRAY);
        header.setDefaultRenderer(headerRender);

        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        header.setReorderingAllowed(false);
        table.setTableHeader(header);

        // Configuracion del JScrollPane
        setViewportView(table);
        setBackground(Palette.GRID_BLACK);
        getViewport().setBackground(Palette.GRID_BLACK);
        setBorder(BorderFactory.createLineBorder(Palette.GRID_GRAY));

    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

}
