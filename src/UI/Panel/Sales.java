package UI.Panel;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import Item.Item;
import Item.Miembros;
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
    private CustomButton.Option reset, cashback, complete;
    private CustomButton.Decision delete, add;

    private double totalValue = 0;
    
    public Sales(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        DefaultTableModel model = new DefaultTableModel(null, new Object[] {"Nombre", "ID", "Precio I.", "Descuento", "Precio F.", "Cantidad"});
        scroll = new CustomScroll(460, 398, model);

        Object[] miembros = {"Juan"};
        member = new CustomCombo("Seleccion miembro", miembros);

        Object[] productos = {" "};
        item = new CustomCombo("Seleccion item", productos);

        text = new CustomLabel.Semi("Total acumulado", SwingConstants.CENTER, 22.0F);
        text.setPreferredSize(new Dimension(220, 60));
        total = new CustomLabel.Semi("$" + totalValue, SwingConstants.CENTER, 36.0F);

        reset = new CustomButton.Option(180, 50, "Reiniciar");
        reset.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                // Limpia los datos
                cleanTable();
                totalValue = 0;
                total.setText("$" + totalValue);

            }

        }); 

        cashback = new CustomButton.Option(180, 50, "Usar cashback");
        cashback.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (member.getBox().getSelectedItem() != null) {

                    String[] name = member.getBox().getSelectedItem().toString().replace(" ", "").split("-");

                    if (name[1].equals("PREMIUM")) {
                        new FieldFrame.UseCashback(Sales.this, name[0]);
                    }

                }

            }

        });

        complete = new CustomButton.Option(180, 50, "Completar venta");
        complete.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                // Reduce el stock para cada item en la tabla
                DefaultTableModel adminTable = mainFrame.getAdmin().getScroll().getModel();
                DefaultTableModel salesTable = scroll.getModel();

                if (salesTable.getRowCount() > 0) {
                
                    // Reduce el stock de los items
                    for (int a = 0; a < salesTable.getRowCount(); a++) {

                        for (int i = 0; i < adminTable.getRowCount(); i++) {

                            if (adminTable.getValueAt(i, 1).toString().equals(salesTable.getValueAt(a, 1).toString())) {

                                int resta = Integer.parseInt(salesTable.getValueAt(a, 5).toString());
                                int stock = Integer.parseInt(adminTable.getValueAt(i, 6).toString());

                                stock -= resta;
                                adminTable.setValueAt("" + stock, i, 6);

                            }

                        }

                    }

                    // Agrega cashback al miembro en cuestion, busca primero al dueño
                    String[] name = member.getBox().getSelectedItem().toString().replace(" ", "").split("-");
                    DefaultTableModel memberTable = mainFrame.getMembers().getScroll().getModel();

                    if (name[1].equalsIgnoreCase("premium")) {
                        
                        int row = 0;
                        boolean found = false;
                        double cashbackD = 0;

                        for(int i = 0; i < memberTable.getRowCount(); i++) {

                            if (memberTable.getValueAt(i, 0).toString().equals(name[0])) {
                                cashbackD = Double.parseDouble(memberTable.getValueAt(i, 9).toString().replace("$", ""));
                                found = true;
                                row = i;
                                break;
                            }

                        }

                        // Si no lo encontro, busca en Add1
                        if (!found) {
                            for(int i = 0; i < memberTable.getRowCount(); i++) {

                                if (memberTable.getValueAt(i, 5).toString().equals(name[0])) {
                                    cashbackD = Double.parseDouble(memberTable.getValueAt(i, 9).toString().replace("$", ""));
                                    found = true;
                                    row = i;
                                    break;
                                }

                            }
                        }

                        // Si no lo encontro, busca en Add2
                        if (!found) {
                            for(int i = 0; i < memberTable.getRowCount(); i++) {

                                if (memberTable.getValueAt(i, 6).toString().equals(name[0])) {
                                    cashbackD = Double.parseDouble(memberTable.getValueAt(i, 9).toString().replace("$", ""));
                                    found = true;
                                    row = i;
                                    break;
                                }

                            }
                        }

                        cashbackD += totalValue * 0.05;
                        memberTable.setValueAt(String.format("$%.2f", cashbackD), row, 9);

                    }

                    // Limpia los datos
                    cleanTable();
                    totalValue = 0;
                    total.setText("$" + totalValue);

                    // Guarda la tabla con los nuevos datos
                    Item.guardarItems(adminTable);

                    // Guarda los datos de los miembros
                    Miembros.guardarTableModelEnArchivo("src\\data\\DatosM.csv", memberTable);

                }

            }

        }); 

        delete = new CustomButton.Decision(100, 40, "Eliminar");
        delete.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (item.getBox().getSelectedItem() != null) {

                    // Extrae el id del item desde el checkbox y obtiene los detalles del mismo
                    String[] itemId = item.getBox().getSelectedItem().toString().replace(" ", "").split("-");
                    String[] itemData = Item.obtenerDetallesItem(itemId[1]);

                    // Modelos para verificaciones
                    DefaultTableModel salesTable = scroll.getModel();

                    // Revisa si el item ya esta en la tabla y devuelve su numero de fila
                    boolean inTable = false;
                    int row = 0;

                    for (int i = 0; i < salesTable.getRowCount(); i++) {

                        String temp = salesTable.getValueAt(i, 1).toString();
                        if (temp.equals(itemData[1]) ) {

                            inTable = true;
                            row = i;
                            break;

                        }

                    }

                    // Solo si ya esta en la tabla
                    if (inTable) {

                        int cantidad = Integer.parseInt(salesTable.getValueAt(row, 5).toString());

                        // Modificacion de la cantidad
                        cantidad -= 1;
                        salesTable.setValueAt(cantidad, row, 5);

                        // Reduccion del dinero
                        double finalPrice = Double.parseDouble(itemData[4].replace("$", ""));
                        salesTable.setValueAt(String.format("$%.2f", finalPrice * cantidad), row, 4);

                        if (cantidad == 0) {

                            salesTable.removeRow(row);

                        }

                        totalValue -= finalPrice;
                        String output = String.format("$%.2f", totalValue);

                        if (totalValue < 0) {
                            totalValue *= -1;
                        }

                        total.setText(output);

                    }

                }

            }

        });

        add = new CustomButton.Decision(100, 40, "Añadir");
        add.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (item.getBox().getSelectedItem() != null) {

                    // Modelo desde el panel admin
                    DefaultTableModel itemsModel = mainFrame.getAdmin().getScroll().getModel();

                    // Extrae el id del item desde el checkbox y obtiene los detalles del mismo
                    String[] itemId = item.getBox().getSelectedItem().toString().replace(" ", "").split("-");
                    String[] itemData = Item.obtenerDetallesItem(itemId[1]);

                    int cantidad = 0;
                    int adminRow = 0;

                        // Busca el item en la tabla de items (en admin)
                        for (int i = 0; i < itemsModel.getRowCount(); i++) {

                            if (itemsModel.getValueAt(i, 1).toString().equals(itemId[1]) ) {
                                cantidad = Integer.parseInt(itemsModel.getValueAt(i, 6).toString());
                                adminRow = i;
                                break;
                            }

                    }

                    if (Integer.parseInt(itemsModel.getValueAt(adminRow, 6).toString()) >= 1) {

                        String name = itemData[0].toString();
                        String id = itemData[1].toString();
                        String priceI = itemData[2].toString();
                        String discount = itemData[3].toString();
                        String priceF = itemData[4].toString();
                        String amount = "1";
            
                        int row = 0;
                        boolean inTable = false;
                        DefaultTableModel salesTable = scroll.getModel();

                        // Revisa si el item ya esta en la tabla y devuelve su numero de fila
                        for (int i = 0; i < salesTable.getRowCount(); i++) {

                            String temp = salesTable.getValueAt(i, 1).toString();
                            if (temp.equals(itemData[1]) ) {

                                inTable = true;
                                row = i;
                                break;

                            }

                        }

                        if (!inTable) {

                            Object[] item = {name, id, priceI, discount, priceF, amount};
                            salesTable.addRow(item);
                            totalValue += Double.parseDouble(priceF.replace("$", ""));

                        } else {

                            // Agrega mas items solo si hay stock
                            if (cantidad > Integer.parseInt(salesTable.getValueAt(row, 5).toString())) {

                                int nuevaCantidad = (Integer.parseInt(salesTable.getValueAt(row, 5).toString()) + 1);
                                salesTable.setValueAt("" + nuevaCantidad, row, 5);
                                salesTable.setValueAt(String.format("$%.2f", Double.parseDouble(priceF.replace("$", "")) * nuevaCantidad), row, 4);
                                totalValue += Double.parseDouble(priceF.replace("$", ""));
                                
                            }

                        }

                        String output = String.format("$%.2f", totalValue);

                        if (totalValue < 0) {
                            totalValue *= -1;
                        }

                        total.setText(output);

                    }

                }

            }

        });

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
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(container, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 60, 0, 0);
        add(reset, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 30, 0, 30);
        add(cashback, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 60);
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

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public CustomScroll getScroll() {
        return scroll;
    }

    public CustomLabel getTotal() {
        return total;
    }

    public void setTotalValue(double value) {
        totalValue = value;
    }

    public CustomButton.Option getCompleteButton() {
        return complete;
    }

}
