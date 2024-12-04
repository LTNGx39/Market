package UI.Assets;

import UI.*;
import UI.Panel.*;
import javax.swing.*;
import java.awt.*;

public class CustomCombo extends javax.swing.JPanel {
    
    protected CustomLabel.Bold title;
    protected JComboBox box;

    public CustomCombo(String text, Object[] items) {

        setPreferredSize(new Dimension(220, 65));
        setLayout(new GridBagLayout());
        setOpaque(false);

        title = new CustomLabel.Bold(text, SwingConstants.CENTER, 18.0F);
        title.setPreferredSize(new Dimension(220, 20));
        
        box = new JComboBox<>(items);
        box.setPreferredSize(new Dimension(220, 30));
        box.setBackground(Palette.BLACK);
        box.setForeground(Palette.WHITE);
        box.setFont(CustomFont.interMedium.deriveFont(14.0F));
        
        // Adicion
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(box, gbc);

    }

    public JComboBox getBox() {
        return box;
    }

}
