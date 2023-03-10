package Enchere.dao;

import Enchere.connexion.Connexion;
import Enchere.models.Enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnchereDao {

    public List<Enchere> getListEnchere(Connection con) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }

    public List<Enchere> getListEnchereRecherche(PreparedStatement stm) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        ResultSet res=stm.executeQuery();
        while(res.next()){
            Enchere e=new Enchere(res.getInt("idenchere"),res.getInt("idutilisateur"),
                    res.getString("description"),res.getFloat("prixminimumvente"),
                    res.getInt("durreenchere"),
                    res.getTimestamp("dateheureenchere"),res.getInt("status"));
            liste.add(e);
        }
        return liste;
    }

    public List<Enchere> getListeEnchereUser(Connection con,int idutilisateur) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"idutilisateur="+idutilisateur+"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }


    public List<Enchere> getFicheEnchere(Connection con,int idenchere) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"idenchere="+idenchere+"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }

    public void AjouterEnchere(Connexion con, int idutilisateur, String description, float prixminimumvente, int durreenchere) throws Exception
    {
        String requete="INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere) values ("+idutilisateur+",'"+description+"',"+prixminimumvente+","+durreenchere+")";
        con = new Connexion(requete);
        System.out.println(requete);
    }

    public float getPourcentage() throws SQLException {
        String requete = "select pourcentage from PourcentagePrelevee" ;
        Connexion con = new Connexion(requete,"hh");
        con.getResultset().next();
        float pourcentage = con.getResultset().getFloat(1);
        return pourcentage;
    }

    public float getPrixDeVente(int idenchere) throws SQLException {
        String requete = "select prixMinimumVente from enchere where idenchere="+idenchere+"";
        Connexion con = new Connexion(requete,"hh");
        con.getResultset().next();
        float prixVente = con.getResultset().getFloat(1);
        return prixVente;
    }

    public float MontantPrelevee(int idenchere) throws Exception
    {
        float pourcentage = this.getPourcentage();
        float montantPrelevee = (this.getPrixDeVente(idenchere) * pourcentage)/100;
        return montantPrelevee;
    }

    public int AjouterEncher(Connexion con,int idutilisateur,String description,float prixminimumvente,int durreenchere) throws Exception
    {
        String requete="INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere) values ("+idutilisateur+",'"+description+"',"+prixminimumvente+","+durreenchere+") returning idenchere";
        con = new Connexion(requete,"");
        con.getResultset().next();
        int result = con.getResultset().getInt(1);
        return result;
    }

    public PreparedStatement generateStatement(Connexion conn, String startDate,String endDate,
                                            String category,String auctionStatus, String keywords) {
        PreparedStatement stmt =null;
        try {

            String query = "SELECT e.*, p.nomproduit FROM enchere e join produit_enchere pe using (idenchere) " +
                    "join produit p using (idproduit) WHERE 1=1";
            StringBuilder sb = new StringBuilder(query);


            int parameterIndex = 1;

            // check if the user entered a start date
            if (startDate != null) {
                sb.append(" AND dateheureenchere >= '"+java.sql.Date.valueOf(startDate)+"'");

            }

            // check if the user entered an end date
            if (endDate != null) {
                sb.append(" AND dateheureenchere <= '"+java.sql.Date.valueOf(startDate)+"'");
            }

            // check if the user entered a category
            if (category != null) {
                sb.append(" AND e.description  like '%"+category+"%'");
            }

            // check if the user entered an auction status
            if (auctionStatus != null) {

                sb.append(" AND status = '"+auctionStatus+"'");

            }

            // check if the user entered a name or description
            if (keywords != null) {
                sb.append(" AND (nomproduit LIKE '%"+keywords+"%' OR e.description LIKE '%"+keywords+"%')");
            }
            query=sb.toString();
            stmt = conn.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }

    }
