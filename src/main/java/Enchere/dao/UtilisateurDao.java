package Enchere.dao;

import Enchere.connexion.Connexion;
import Enchere.models.Utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDao {
    Utilisateur user = new Utilisateur();
    public Utilisateur login(String user,String mdp) throws Exception {
        Utilisateur u = null;
        try {
            String requete = "select * from Utilisateur where email='" + user + "' and mdp='" + mdp + "' ";
            Connexion c = new Connexion(requete, "");
            ResultSet res = c.getResultset();
            c.getResultset().next();
            String idUser = res.getString(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);
            String m = res.getString(5);
            u = new Utilisateur(nom,prenom,email,m);
        } catch (Exception e) {

        }
        return u;
    }

    public void Inscription(Connexion con,String nom,String prenom,String email,String mdp) throws Exception {

        String requete="insert into utilisateur(nom,prenom,email,mdp) values ('"+nom+"','"+prenom+"','"+email+"','"+mdp+"')";
        con = new Connexion(requete);
        con.getResultset();
    }

    public float getCompteUser(int idclient,Connexion con) throws Exception {
        String requete = "select compte from utilisateur  where idutilisateur="+idclient+" ";
        con = new Connexion(requete,"");
        con.getResultset().next();
        float result = con.getResultset().getFloat(1);
        return result;
    }

    public void setCompteUser(int idclient,float montant,Connexion con) throws Exception
    {
        float soldeUser = this.getCompteUser(idclient,con)+montant;
        try {
            String requete = "update utilisateur set compte="+soldeUser+" where idutilisateur="+idclient+"";
            con = new Connexion(requete);
            con.getCommit();
        } catch (Exception exc) {

            try {
                con.getRollBack();
                System.out.println("Transaction échouée : annulation");
            } catch (SQLException ex) {

            }
        }
        finally {}
    }

    public void rechargerCompte(int idclient,float montant,Connexion con) throws Exception
    {
        try {
            String requete = "insert into rechargementcompte(idutilisateur,montant) values("+idclient+","+montant+")";
            con = new Connexion(requete);
            con.getCommit();
        } catch (Exception exc) {
            try {
                con.getRollBack();
                System.out.println("Transaction échouée : annulation");
            } catch (SQLException ex) {}
        }
        finally {}
    }

//    public static void main(String args[]){
//        UtilisateurDao udao=new UtilisateurDao();
//        try {
//            udao.Inscription(new Connexion(),"nom","prenom","email","motdepasse");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//    }



}
