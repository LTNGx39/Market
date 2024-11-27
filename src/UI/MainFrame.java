package UI;

import javax.swing.*;

import UI.Assets.*;
import UI.Panel.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MainFrame extends javax.swing.JFrame {

    private TitleBar titleBar;

    private CardLayout cardLayout;
    private JPanel panelChanger;

    private UserSelector userSelector;
    private Admin admin;
    private Sales sales;
    private Members members;

    public MainFrame(int width, int height) {

        setSize(width + 2, height + 2);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setContentPane(new CustomPane(this));
        setBackground(Palette.ALPHA_0);

        titleBar = new TitleBar(this);
        userSelector = new UserSelector(this);
        admin = new Admin(this);
        sales = new Sales(this);
        members = new Members(this);

        panelChanger = new JPanel();
        cardLayout = new CardLayout();
        panelChanger.setLayout(cardLayout);
        panelChanger.setOpaque(false);
        
        add(titleBar);
        add(panelChanger);
        panelChanger.add(userSelector, "Usuarios");
        panelChanger.add(admin, "Administracion");
        panelChanger.add(sales, "Ventas");
        panelChanger.add(members, "Membresias");

    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getPanelChanger() {
        return panelChanger;
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public Admin getAdmin() {
        return admin;
    }

    public int getUsableWidth() {
        return getWidth() - 2;
    }

    public int getUsableHeight() {
        return getHeight() - 2;
    }

}

class CustomPane extends javax.swing.JPanel {
    
    MainFrame frame;

    public CustomPane(MainFrame frame) {

        this.frame = frame;
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Borde
        g2.setColor(Palette.GRAY);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 22, 22));

        // Area interna
        g2.setColor(Palette.DARK_GRAY);
        g2.fill(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, 20, 20));

    }

}