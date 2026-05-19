package view;

import dao.PlatDAO;
import model.Plat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlatFrame extends JFrame {
    private PlatDAO platDAO = new PlatDAO();
    private JTable table;
    private DefaultTableModel model;
    private JTextField nomField, prixField;
    private JButton addBtn, updateBtn, deleteBtn, refreshBtn;

    public PlatFrame() {
        setTitle("Gestion du menu");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        chargerPlats();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nom", "Prix (€)"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Ajouter / Modifier"));
        inputPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Prix :"));
        prixField = new JTextField();
        inputPanel.add(prixField);

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Ajouter");
        updateBtn = new JButton("Modifier");
        deleteBtn = new JButton("Supprimer");
        refreshBtn = new JButton("Actualiser");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                nomField.setText((String) model.getValueAt(row, 1));
                prixField.setText(String.valueOf(model.getValueAt(row, 2)));
            }
        });

        addBtn.addActionListener(e -> ajouterPlat());
        updateBtn.addActionListener(e -> modifierPlat());
        deleteBtn.addActionListener(e -> supprimerPlat());
        refreshBtn.addActionListener(e -> chargerPlats());
    }

    private void chargerPlats() {
        try {
            List<Plat> plats = platDAO.getAllPlats();
            model.setRowCount(0);
            for (Plat p : plats) {
                model.addRow(new Object[]{p.getId(), p.getNom(), p.getPrix()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void ajouterPlat() {
        String nom = nomField.getText().trim();
        String prixStr = prixField.getText().trim();
        if (nom.isEmpty() || prixStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }
        try {
            double prix = Double.parseDouble(prixStr);
            Plat p = new Plat(0, nom, prix);
            platDAO.addPlat(p);
            chargerPlats();
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide.");
        }
    }

    private void modifierPlat() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un plat.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        String nom = nomField.getText().trim();
        String prixStr = prixField.getText().trim();
        if (nom.isEmpty() || prixStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }
        try {
            double prix = Double.parseDouble(prixStr);
            Plat p = new Plat(id, nom, prix);
            platDAO.updatePlat(p);
            chargerPlats();
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide.");
        }
    }

    private void supprimerPlat() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce plat ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            platDAO.deletePlat(id);
            chargerPlats();
            clearFields();
        }
    }

    private void clearFields() {
        nomField.setText("");
        prixField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlatFrame().setVisible(true));
    }
}