package Enchere.dao;

import Enchere.models.ProduitCategorie;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProduitDao {


    public List<ProduitCategorie> getListProduct(Connection con) throws Exception {
        List<ProduitCategorie> liste = new ArrayList<>();
        ProduitCategorie p = new ProduitCategorie();
        Object[] result = p.getFromView(con,"ProduitCategorie","");
        for(Object o:result)
        {
            liste.add((ProduitCategorie) o);
        }
        if(liste.size() != 0)
        {
             return liste;
        }
        else {
            return null;
        }
    }

}
