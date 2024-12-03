package UI.Panel;

import javax.swing.*;

import Item.ItemPersistencia;
import UI.*;
import UI.Assets.*;

import java.awt.*;

public class Sales extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomScroll scroll;
    private CustomLabel text, total;
    private CustomButton.Option reset, complete;
    private CustomButton.Decision delete, add;
    
    public Sales(MainFrame mainFrame) {

        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Configuracion de componentes
        scroll = new CustomScroll(460, 398, ItemPersistencia.leerItemsDesdeArchivo());

        // Datos de prueba
        scroll.getModel().addRow(new Object[] {"Agua", "Premium", "Fuego", "Tierra", "Luz", "Oscuridad"});

        text = new CustomLabel.Semi("Total acumulado", SwingConstants.CENTER, 16.0F);
        total = new CustomLabel.Semi("$0", SwingConstants.CENTER, 32.0F);

        reset = new CustomButton.Option(180, 50, "Reiniciar");
        complete = new CustomButton.Option(180, 50, "Completar venta");

        delete = new CustomButton.Decision(100, 40, "Eliminar");
        add = new CustomButton.Decision(100, 40, "Añadir");

        // Adicion
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Prueba de componente
        add(new CustomCombo(220, 65, "Seleccionar miembro", new Object[] {"Pepe", "Toño"}));

    }

}
