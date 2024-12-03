package UI.Assets;

import UI.*;
import UI.Panel.*;
import javax.swing.*;
import java.awt.*;

public class CustomCombo extends javax.swing.JPanel {
    
    protected CustomLabel.Bold title;
    protected JComboBox box;

    public CustomCombo(int width, int height, String text, Object[] items) {

        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        title = new CustomLabel.Bold(text, SwingConstants.CENTER, 16.0F);
        
        box = new JComboBox<>(items);
        box.setPreferredSize(new Dimension(getPreferredSize().width, 30));

        // Adicion
        add(title);
        add(box);

    }

}
