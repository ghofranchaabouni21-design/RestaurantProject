package dao;

import model.Commande;
import model.Plat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {

    private final Connection conn = DBConnection.getConnection();

    // Insérer une commande et ses détails
    public int creerCommande(Commande cmd) {
        int generatedId = -1;
        try {
            // 1. Insérer dans la table commande
            String sqlCommande = "INSERT INTO commande (total) VALUES (?)";
            PreparedStatement psCmd = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS);
            psCmd.setDouble(1, cmd.getTotal());
            psCmd.executeUpdate();

            ResultSet rs = psCmd.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
                cmd.setId(generatedId);
            }

            // 2. Insérer les détails (detail_commande)
            String sqlDetail = "INSERT INTO detail_commande (id_commande, id_plat, quantite) VALUES (?, ?, ?)";
            PreparedStatement psDet = conn.prepareStatement(sqlDetail);
            for (Plat p : cmd.getPlats()) {
                psDet.setInt(1, generatedId);
                psDet.setInt(2, p.getId());
                psDet.setInt(3, 1); // quantité fixe 1 pour l'instant
                psDet.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // Récupérer toutes les commandes (optionnel)
    public List<Commande> getAllCommandes() {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Commande c = new Commande(rs.getInt("id"));
                c.setTotal(rs.getDouble("total"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== MÉTHODES AJOUTÉES POUR LE DASHBOARD ==========

    // Retourne le nombre total de commandes
    public int getNombreCommandes() {
        String sql = "SELECT COUNT(*) FROM commande";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retourne le chiffre d'affaires total (somme de tous les totaux)
    public double getChiffreAffairesTotal() {
        String sql = "SELECT SUM(total) FROM commande";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}