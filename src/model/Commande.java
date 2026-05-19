package model;

import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int id;
    private List<Plat> plats;
    private double total;

    public Commande(int id) {
        this.id = id;
        this.plats = new ArrayList<>();
        this.total = 0.0;
    }

    public void ajouterPlat(Plat p) {
        plats.add(p);
        total += p.getPrix();
    }

    public void retirerPlat(Plat p) {
        plats.remove(p);
        total -= p.getPrix();
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Plat> getPlats() { return plats; }
    public double getTotal() { return total; }
    public void setTotal(double total) {
        this.total = total;
    }
}