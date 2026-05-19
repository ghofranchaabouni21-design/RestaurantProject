package dao;

import model.Plat;
import java.sql.*;
import java.util.ArrayList;

public class PlatDAO {

    Connection conn = DBConnection.getConnection();

    // Ajouter Plat
    public void addPlat(Plat p) {
        try {
            String sql = "INSERT INTO plat (nom, prix) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Afficher tous les plats
    public ArrayList<Plat> getAllPlats() {
        ArrayList<Plat> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM plat";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Plat p = new Plat(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"));
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    // Modifier un plat
    public void updatePlat(Plat p) {
        String sql = "UPDATE plat SET nom = ?, prix = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            System.out.println("Plat modifié : " + p.getNom());
        } catch (SQLException e) {
            System.out.println("Erreur updatePlat : " + e.getMessage());
        }
    }

    // Supprimer un plat par son ID
    public void deletePlat(int id) {
        String sql = "DELETE FROM plat WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Plat supprimé, ID : " + id);
        } catch (SQLException e) {
            System.out.println("Erreur deletePlat : " + e.getMessage());
        }
    }
}