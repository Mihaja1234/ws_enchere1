package Enchere.models;

import Enchere.ObjectBdd.Mere;
import Enchere.connexion.Connexion;


public class CategorieProduit extends Mere {

    public Integer IdCategorieProduit;
    public String typeCategorie;

    public CategorieProduit() {
    }

    public CategorieProduit(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public Integer getIdCategorieProduit() {
        return IdCategorieProduit;
    }

    public void setIdCategorieProduit(Integer idCategorieProduit) {
        IdCategorieProduit = idCategorieProduit;
    }

    public String getTypeCategorie() {
        return typeCategorie;
    }

    public void setTypeCategorie(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public void NewCategorie(Connexion con) throws Exception
    {
        String requete="insert into categorieproduit(typecategorie) values ('"+this.getTypeCategorie()+"')";
        System.out.println(requete);
        con = new Connexion(requete);
        con.getResultset();
    }
}
