package UI.Panel;

import UI.*;
import UI.Assets.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingConstants;

public class TitleBar extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomButton.TitleBar back, close;
    private CustomLabel title;
    
    public TitleBar(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(mainFrame.getUsableWidth(), 32));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        back = new CustomButton.TitleBar(CustomButton.TitleBar.BACK);
        back.addActionListener(new ActionListener() {
                        
            @Override
            public void actionPerformed(ActionEvent e) {

                // Salir a market
                mainFrame.getCardLayout().show(mainFrame.getPanelChanger(), "Usuarios");
                mainFrame.getTitleBar().getTitleLabel().setText("Market");
                back.setMouseOver(false);
                back.setEnabled(false);

            }

        });

        close = new CustomButton.TitleBar(CustomButton.TitleBar.CLOSE);
        close.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                mainFrame.dispose();
                System.exit(0);

            }

        });

        title = new CustomLabel.Bold("Market", SwingConstants.CENTER, 16.0F);
        title.setPreferredSize(new Dimension(mainFrame.getUsableWidth() - 96, 32));

        // Listener para arrastrar la ventana
        MouseAdapter drag = new MouseAdapter() {

            int mouseX, mouseY;

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = mainFrame.getMousePosition().x;
                mouseY = mainFrame.getMousePosition().y;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;

                mainFrame.setLocation(x, y);
            } 

        };

        title.addMouseListener(drag);
        title.addMouseMotionListener(drag);

        // Añadir los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(back, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(title);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(close);

    }

    public CustomButton getBackButton() {
        return back;
    }

    public CustomButton getCloseButton() {
        return back;
    }

    public CustomLabel getTitleLabel() {
        return title;
    }

}
