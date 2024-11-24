package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class UserSelector extends javax.swing.JPanel {

    private UserButton admin, sell, member;
    private TextLabel title, adminText, sellText, memberText;

    public UserSelector(Frame frame) {

        setBorder(BorderFactory.createEmptyBorder(130, 130, 180, 130));
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Configuracion de componentes
        title = new TextLabel("Elija un perfil", CustomFont.interSemiBold, 32.0F);
        title.setPreferredSize(new Dimension(300, 40));
        adminText = new TextLabel("Administracion", CustomFont.interMedium, 18.0F);
        sellText = new TextLabel("Ventas", CustomFont.interMedium, 18.0F);
        memberText = new TextLabel("Membresias", CustomFont.interMedium, 18.0F);

        admin = new UserButton(frame, "src/Media/Image/pink.png", adminText);
        sell = new UserButton(frame, "src/Media/Image/blue.png", sellText);
        member = new UserButton(frame, "src/Media/Image/yellow.png", memberText);

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

class UserButton extends javax.swing.JButton {

    private Frame frame;
    private String name;
    private boolean isMouseOver;

    public UserButton(Frame frame, String imagePath, TextLabel label) {

        super(new ImageIcon(imagePath));
        this.frame = frame;
        name = label.getText();
        setPreferredSize(new Dimension(140, 140));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isMouseOver = false;
            }

        });

        addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.getTitleBar().setTitle(getName());
                frame.getTitleBar().getLeftButton().setEnabled(true);
                frame.getTitleBar().setInMenu(false);

            }
            
        });

    }

    public String getName() {
        return name;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Palette.WHITE);
        if (isMouseOver) {
            g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        }
        super.paintComponent(g);
        
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
