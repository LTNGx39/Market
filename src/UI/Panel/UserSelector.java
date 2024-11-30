package UI.Panel;

import UI.MainFrame;
import UI.Assets.CustomButton;
import UI.Assets.CustomLabel;
import javax.swing.*;
import java.awt.*;

public class UserSelector extends javax.swing.JPanel {

    private CustomButton admin, sale, member;
    private CustomLabel title, adminText, sellText, memberText;

    public UserSelector(MainFrame mainFrame) {

        setBorder(BorderFactory.createEmptyBorder(130, 130, 180, 130));
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        title = new CustomLabel.Semi("Elija un perfil", SwingConstants.CENTER, 32.0F);
        title.setPreferredSize(new Dimension(300, 40));

        adminText = new CustomLabel.Semi("Administracion", SwingConstants.CENTER, 18.0F);
        adminText.setPreferredSize(new Dimension(140, 30));

        sellText = new CustomLabel.Semi("Ventas", SwingConstants.CENTER, 18.0F);
        sellText.setPreferredSize(new Dimension(140, 30));

        memberText = new CustomLabel.Semi("Membresias", SwingConstants.CENTER, 18.0F);
        memberText.setPreferredSize(new Dimension(140, 30));

        admin = new CustomButton.User(CustomButton.User.PINK_PFP);
        sale = new CustomButton.User(CustomButton.User.BLUE_PFP);
        member = new CustomButton.User(CustomButton.User.YELLOW_PFP);

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
        add(sale, gbc);

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

    public CustomButton getAdminButton() {
        return admin;
    }

    public CustomButton getSaleButton() {
        return sale;
    }

    public CustomButton getMemberButton() {
        return member;
    }
    
}
