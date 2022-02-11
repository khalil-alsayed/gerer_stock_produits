/**
 *
 * @author khalil al sayed
 */
public class Produit {
    private String ID;
    private String nom;
    private int quantite;
    private int prix;

    public Produit(String ID, String nom, int quantite, int prix) {
        this.ID = ID;
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Produit() {
    }

    public Produit(String id, int quantite, int prix) {
        this.ID = id;
        this.quantite = quantite;
        this.prix = prix;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }


    
}
