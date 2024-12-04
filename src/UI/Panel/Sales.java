package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Item.Item;
import UI.*;
import UI.Assets.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.ObjID;

public class Sales extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomScroll scroll;
    private CustomCombo member, item;
    private CustomLabel text, total;
    private CustomButton.Option reset, complete;
    private CustomButton.Decision delete, add;
    
    public Sales(MainFrame mainFrame) {

        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        DefaultTableModel model = new DefaultTableModel(null, new Object[] {"Nombre", "ID", "Precio I.", "Descuento", "Precio F.", "Cantidad"});
        scroll = new CustomScroll(460, 398, model);

        Object[] miembros = {"Juan"};
        member = new CustomCombo("Seleccion miembro", miembros);

        Object[] productos = {"xd"};
        item = new CustomCombo("Seleccion item", productos);

        text = new CustomLabel.Semi("Total acumulado", SwingConstants.CENTER, 22.0F);
        text.setPreferredSize(new Dimension(220, 60));
        total = new CustomLabel.Semi("$0", SwingConstants.CENTER, 36.0F);

        reset = new CustomButton.Option(180, 50, "Reiniciar");
        reset.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cleanTable();

            }

        }); 

        complete = new CustomButton.Option(180, 50, "Completar venta");

        delete = new CustomButton.Decision(100, 40, "Eliminar");
        add = new CustomButton.Decision(100, 40, "Añadir");

        // Adicion
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;

        // En el contenedor
        g.gridx = 0;
        g.gridy = 0;
        g.gridheight = 5;
        g.insets = new Insets(0, 0, 0, 40);
        container.add(scroll, g);

        g.gridx = 1;
        g.gridheight = 1;
        g.gridwidth = 2;
        g.insets = new Insets(0, 0, 35, 0);
        container.add(member, g);

        g.gridy = 1;
        g.insets = new Insets(0, 0, 15, 0);
        container.add(item, g);

        g.gridy = 2;
        g.gridwidth = 1;
        g.insets = new Insets(0, 0, 15, 20);
        container.add(delete, g);

        g.gridx = 2;
        g.insets = new Insets(0, 0, 15, 0);
        container.add(add, g);

        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        g.insets = new Insets(0, 0, 0, 0);
        container.add(text, g);

        g.gridy = 4;
        container.add(total, g);

        // En el panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(container, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 165, 0, 15);
        add(reset, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 15, 0, 165);
        add(complete, gbc);

    }

    private void cleanTable() {

        DefaultTableModel model = scroll.getModel();

        if (model.getRowCount() > 0) {

            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }

        }
    }

    public CustomCombo getMemberBox() {
        return member;
    }

    public CustomCombo getItemBox() {
        return item;
    }

}
