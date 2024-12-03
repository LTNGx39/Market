package UI.Panel;

import javax.swing.*;

import Item.ItemPersistencia;
import UI.*;
import UI.MainFrame;
import UI.Assets.*;
import java.awt.*;
import java.awt.event.*;

public class Members extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomScroll scroll;

    private CustomButton addMember, editMember, deleteMember;
    
    public Members(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        scroll = new CustomScroll(720, 398, ItemPersistencia.leerItemsDesdeArchivo());

        // Datos de prueba
        scroll.getModel().addRow(new Object[] {"Agua", "Premium", "Fuego", "Tierra", "Luz", "Oscuridad", "Particulas", "1954", "1924"});

        addMember = new CustomButton.Option(180, 50, "Añadir miembro");
        addMember.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                new FieldFrame.AddMember(Members.this);

            }

        });

        editMember = new CustomButton.Option(180, 50, "Editar miembro");
        editMember.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = scroll.getTable().getSelectedRow();
                if (row != -1) {
                    new FieldFrame.EditMember(Members.this, row);
                }

            }

        });

        deleteMember = new CustomButton.Option(180, 50, "Eliminar miembro");
        deleteMember.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = scroll.getTable().getSelectedRow();
                if (row != -1) {
                    new FieldFrame.DeleteMember(Members.this, row);
                }

            }

        });

        // Adicion de los componentes
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
        add(addMember, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 30, 0, 30);
        add(editMember, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 60);
        add(deleteMember, gbc);

    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public CustomScroll getScroll() {
        return scroll;
    }

}
