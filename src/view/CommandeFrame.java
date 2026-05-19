package view;

import dao.PlatDAO;
import dao.CommandeDAO;
import model.Plat;
import model.Commande;
import util.PDFGenerator;  // ← import ajouté

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CommandeFrame extends JFrame {
    private PlatDAO platDAO = new PlatDAO();
    private CommandeDAO commandeDAO = new CommandeDAO();
    private List<Plat> menu;
    private List<Plat> panier = new ArrayList<>();

    private JTable tableMenu;
    private DefaultTableModel modelMenu;
    private JTable tablePanier;
    private DefaultTableModel modelPanier;
    private JLabel totalLabel;

    public CommandeFrame() {
        setTitle("Prise de commande");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        chargerMenu();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Menu (gauche)
        modelMenu = new DefaultTableModel(new String[]{"ID", "Nom", "Prix (€)"}, 0);
        tableMenu = new JTable(modelMenu);
        JScrollPane scrollMenu = new JScrollPane(tableMenu);
        scrollMenu.setBorder(BorderFactory.createTitledBorder("Menu"));

        // Panier (droite)
        modelPanier = new DefaultTableModel(new String[]{"Nom", "Prix (€)"}, 0);
        tablePanier = new JTable(modelPanier);
        JScrollPane scrollPanier = new JScrollPane(tablePanier);
        scrollPanier.setBorder(BorderFactory.createTitledBorder("Panier"));

        // Boutons
        JButton addButton = new JButton("Ajouter au panier");
        JButton removeButton = new JButton("Retirer du panier");
        JButton validerButton = new JButton("Valider la commande");
        totalLabel = new JLabel("Total : 0.00 €");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(validerButton);
        buttonPanel.add(totalLabel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollMenu, scrollPanier);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        addButton.addActionListener(e -> ajouterAuPanier());
        removeButton.addActionListener(e -> retirerDuPanier());
        validerButton.addActionListener(e -> validerCommande());
    }

    private void chargerMenu() {
        try {
            menu = platDAO.getAllPlats();
            modelMenu.setRowCount(0);
            for (Plat p : menu) {
                modelMenu.addRow(new Object[]{p.getId(), p.getNom(), p.getPrix()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement menu: " + e.getMessage());
        }
    }

    private void ajouterAuPanier() {

        int selectedRow = tableMenu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un plat dans le menu.");
            return;
        }
        int id = (int) modelMenu.getValueAt(selectedRow, 0);
        String nom = (String) modelMenu.getValueAt(selectedRow, 1);
        double prix = (double) modelMenu.getValueAt(selectedRow, 2);

        Plat plat = new Plat(id, nom, prix);
        panier.add(plat);
        modelPanier.addRow(new Object[]{plat.getNom(), plat.getPrix()});
        mettreAJourTotal();
    }

    private void retirerDuPanier() {
        int selectedRow = tablePanier.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un plat dans le panier.");
            return;
        }
        panier.remove(selectedRow);
        modelPanier.removeRow(selectedRow);
        mettreAJourTotal();
    }

    private void mettreAJourTotal() {
        double total = 0;
        for (Plat p : panier) {
            total += p.getPrix();
        }
        totalLabel.setText(String.format("Total : %.2f €", total));
    }

    // --- Méthode modifiée : après enregistrement, affiche la facture et génère PDF ---
    private void validerCommande() {
        if (panier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le panier est vide.");
            return;
        }
        // Créer un objet Commande (id temporaire 0)
        Commande commande = new Commande(0);
        for (Plat p : panier) {
            commande.ajouterPlat(p);
        }
        // Enregistrer en base et récupérer l'ID généré
        int idCommande = commandeDAO.creerCommande(commande);
        if (idCommande != -1) {
            // Mettre à jour l'ID de l'objet commande
            commande.setId(idCommande);

            // 🔥 Générer le PDF de la facture
            PDFGenerator.generate(commande);

            // Afficher la facture (fenêtre Swing)
            new FactureView(commande).setVisible(true);

            // Vider le panier
            panier.clear();
            modelPanier.setRowCount(0);
            mettreAJourTotal();

            // Message de confirmation
            JOptionPane.showMessageDialog(this, "Commande enregistrée ! ID : " + idCommande + "\nPDF généré.");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la commande.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CommandeFrame().setVisible(true));
    }
}