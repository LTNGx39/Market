package UI.Panel;

import UI.*;
import UI.Assets.CustomButton;
import UI.Assets.CustomLabel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleBar extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private CustomButton back, close;
    private CustomLabel title;
    
    public TitleBar(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(mainFrame.getUsableWidth(), 32));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        back = new CustomButton(mainFrame, CustomButton.BACK);
        close = new CustomButton(mainFrame, CustomButton.CLOSE);

        title = new CustomLabel(mainFrame, CustomLabel.SEMI_BOLD, "Market", 16.0F) {
            {
                setPreferredSize(new Dimension(mainFrame.getUsableWidth() - 96, 32));

                // Listener
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

                addMouseListener(drag);
                addMouseMotionListener(drag);
            }
        };

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
