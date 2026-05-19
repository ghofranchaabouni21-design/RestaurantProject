package util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UIHelper {

    // Palette de couleurs (tu peux la conserver ou l’adapter)
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color ACCENT = new Color(26, 188, 156);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color BG_LIGHT = new Color(248, 249, 250);

    // Méthode pour charger une icône (logo)
    public static ImageIcon loadLogoIcon(int width, int height) {
        URL imgURL = UIHelper.class.getResource("/resources/images/logo.png");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Logo non trouvé - chemin : /resources/images/logo.png");
            return null;
        }
    }

    // (Optionnel) styliser un bouton
    public static void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    // (Optionnel) styliser un tableau
    public static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
    }
}