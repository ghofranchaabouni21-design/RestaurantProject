package model;

public class Plat {
    private int id;
    private String nom;
    private double prix;

    // Constructeur complet
    public Plat(int id, String nom, double prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    // Pour afficher facilement
    @Override
    public String toString() {
        return nom + " (" + prix + "€)";
    }
}