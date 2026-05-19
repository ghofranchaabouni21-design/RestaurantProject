package view;

import model.Commande;
import model.Plat;
import javax.swing.*;
import java.awt.*;

public class FactureView extends JFrame {
    private Commande commande;

    public FactureView(Commande commande) {
        this.commande = commande;
        setTitle("Facture - Commande n°" + commande.getId());
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        StringBuilder sb = new StringBuilder();
        sb.append("               FACTURE\n");
        sb.append("Commande n° ").append(commande.getId()).append("\n");
        sb.append("---------------------------------\n");
        double totalHT = 0;
        for (Plat p : commande.getPlats()) {
            sb.append(String.format("%-25s %8.2f €\n", p.getNom(), p.getPrix()));
            totalHT += p.getPrix();
        }
        double tva = totalHT * 0.19;
        double totalTTC = totalHT + tva;

        sb.append("---------------------------------\n");
        sb.append(String.format("%-25s %8.2f €\n", "Total HT :", totalHT));
        sb.append(String.format("%-25s %8.2f €\n", "TVA (19%) :", tva));
        sb.append(String.format("%-25s %8.2f €\n", "Total TTC :", totalTTC));
        sb.append("---------------------------------\n");
        sb.append("Merci et à bientôt !\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}