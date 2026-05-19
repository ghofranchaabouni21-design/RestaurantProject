package view;

import dao.CommandeDAO;
import dao.PlatDAO;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private CommandeDAO commandeDAO = new CommandeDAO();
    private PlatDAO platDAO = new PlatDAO();

    public DashboardFrame() {
        setTitle("Tableau de bord - Admin");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        int nbCommandes = commandeDAO.getNombreCommandes();
        double revenue = commandeDAO.getChiffreAffairesTotal();
        int nbPlats = platDAO.getAllPlats().size();

        add(createLabel("📦 Nombre de commandes : " + nbCommandes));
        add(createLabel("💰 Chiffre d'affaires total : " + String.format("%.2f €", revenue)));
        add(createLabel("🍽️ Plats disponibles : " + nbPlats));

        // Option : ajouter un bouton pour fermer
        JPanel panel = new JPanel();
        JButton closeBtn = new JButton("Fermer");
        closeBtn.addActionListener(e -> dispose());
        panel.add(closeBtn);
        add(panel); // ou remplacer la troisième ligne par un panel avec le bouton
        // Pour mieux faire, utilise un BorderLayout, mais ceci suffit.
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
    }
}