package UI.Panel;

import javax.swing.*;

import UI.*;
import UI.MainFrame;
import UI.Assets.CustomButton;
import UI.Assets.CustomFont;
import UI.Assets.Palette;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSelector extends javax.swing.JPanel {

    private CustomButton admin, sell, member;
    private TextLabel title, adminText, sellText, memberText;

    public UserSelector(MainFrame frame) {

        setBorder(BorderFactory.createEmptyBorder(130, 130, 180, 130));
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        title = new TextLabel("Elija un perfil", CustomFont.interSemiBold, 32.0F);
        title.setPreferredSize(new Dimension(300, 40));

        adminText = new TextLabel("Administracion", CustomFont.interSemiBold, 18.0F);
        sellText = new TextLabel("Ventas", CustomFont.interSemiBold, 18.0F);
        memberText = new TextLabel("Membresias", CustomFont.interSemiBold, 18.0F);

        admin = new CustomButton(frame, "src/UI/Media/Image/pink.png", "Administracion");
        sell = new CustomButton(frame, "src/UI/Media/Image/blue.png", "Ventas");
        member = new CustomButton(frame, "src/UI/Media/Image/yellow.png", "Membresias");

        // Agregar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        Insets none = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 28, 0);
        add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = none;
        add(admin, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 60, 0, 60);
        add(sell, gbc);

        gbc.gridx = 2;
        gbc.insets = none;
        add(member, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(adminText, gbc);

        gbc.gridx = 1;
        add(sellText, gbc);

        gbc.gridx = 2;
        add(memberText, gbc);
        
    }
    
}

class TextLabel extends javax.swing.JLabel {

    public TextLabel(String text, Font font, float pt) {

        super(text);
        setPreferredSize(new Dimension(140, 30));
        setFont(font.deriveFont(pt));
        setForeground(Palette.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);

    }

}
