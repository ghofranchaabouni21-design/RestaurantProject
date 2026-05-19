import dao.PlatDAO;
import dao.CommandeDAO;
import model.Plat;
import model.Commande;
import java.util.List;

public class TestCommande {
    public static void main(String[] args) {
        PlatDAO platDao = new PlatDAO();
        CommandeDAO cmdDao = new CommandeDAO();

        // Récupérer tous les plats
        List<Plat> plats = platDao.getAllPlats();
        if (plats.size() >= 2) {
            Commande commande = new Commande(0);
            commande.ajouterPlat(plats.get(0)); // Pizza
            commande.ajouterPlat(plats.get(1)); // Salade
            System.out.println("Total de la commande : " + commande.getTotal() + " €");

            int idCommande = cmdDao.creerCommande(commande);
            System.out.println("Commande enregistrée avec l'ID : " + idCommande);
        }
    }
}