package Enchere.connexion;

import Enchere.dao.EnchereDao;
import Enchere.ObjectBdd.ManipDb;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception{

      /*  ProduitDao pd = new ProduitDao();
        Connection con = objectBdd.ManipDb.pgConnect("postgres","cloudfinal","hardi");
        List<ProduitCategorie> list = pd.getListProduct(con);
        for (ProduitCategorie p : list){
            System.out.println(p.getTypeCategorie());
        }*/
        Connection con = ManipDb.pgConnect("postgres","enchere","hasinjaka");
        Connexion con1= new Connexion();
        EnchereDao ed = new EnchereDao();
        int result = ed.AjouterEncher(con1,2,"hhu",3222,22);
        System.out.println(result);
    }
}
