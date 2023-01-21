package Enchere.controllerAPI;

import Enchere.connexion.Connexion;
import Enchere.dao.EnchereDao;
import Enchere.dao.PrelevementEnchereDao;
import Enchere.dao.TokenUserDao;
import Enchere.models.Enchere;
import Enchere.ObjectBdd.ManipDb;
import Enchere.models.Response;
import Enchere.models.TokenUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/enchere")
@CrossOrigin
public class EnchereController {

    EnchereDao ed = new EnchereDao();
    PrelevementEnchereDao ped = new PrelevementEnchereDao();
    Connexion con1 = new Connexion();
    Connection con;
    {
        try {
            con = ManipDb.pgConnect("postgres","enchere","hasinjaka");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listeEnchere")
    public List<Enchere> getListeEnchere() throws SQLException {
        List<Enchere> liste=null;
        try{
            liste= new EnchereDao().getListEnchere(con);
        }catch(Exception e){

        }
        finally{
//            if(con!=null){
//                con.close();
//            }
        }
        return liste;
    }

    @GetMapping("ficheEnchere/{idEnchere}")
    public List<Enchere> getFicheEnchere(@PathVariable int idEnchere) throws SQLException {
        List<Enchere> liste=null;
        try{
            liste= new EnchereDao().getFicheEnchere(con,idEnchere);
        }catch(Exception e){

        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return liste;
    }

    @PostMapping("ReEncherir")
    public Response ReEncherir(@RequestHeader("token")String token) throws Exception {
        Response response = new Response();
        TokenUserDao tud = new TokenUserDao();
        if(tud.validTokenUser(token)!=0)
        {
                response.setMessage("votre offre a été bien prise en compte");
        }
        else{
            response.setMessage("veuillez dabord vous authentifier");
        }
        return response;
    }

    @GetMapping("ListeEnchereUser")
    public ResponseEntity<List<Enchere>> ListeEnchereUser(@RequestHeader("token") String token){
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = null;
        try{
            if(tud.validTokenUser(token)!=0)
            {
                tu = tud.getTokenUser(token);
                return new ResponseEntity<List<Enchere>>(new EnchereDao().getListeEnchereUser(con,tu.getIdUtilisateur()), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("AjoutEnchere/{id}")
    public Response AjoutEnchere(@PathVariable int id,@RequestParam("description") String description,@RequestParam("prixminimumvente") float prixminimumvente,@RequestParam("durreenchere") int durreenchere) throws Exception {
        Response response = new Response();
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = new TokenUser();
        //   if(tud.validTokenUser(token)!=0)
        // {
        //   tu = tud.getTokenUser(token);
        int result = ed.AjouterEncher(con1, id, description, prixminimumvente, durreenchere);

        //commission
        ped.Inserer(con1, result, ed.MontantPrelevee(result));

        response.setMessage("votre vente a été bien prise en compte");
        // }
        // else{
        //     response.setMessage("veuillez dabord vous authentifier");
        // }
        return response;
    }


        @PostMapping("/search")
        public List<Enchere> advancedSearch(@RequestParam(required = false, value="datedebut") String startDate,
                @RequestParam(required = false, value="datefin") String endDate,
                @RequestParam(required = false, value="description") String category,
                @RequestParam(required = false, value="status") String auctionStatus,
                @RequestParam(required = false, value="motcle") String keywords){

            PreparedStatement stmt = ed.generateStatement(con1,startDate,endDate,category,auctionStatus,keywords);
            List<Enchere> encheres= null;
            try {
                encheres = ed.getListEnchereRecherche(stmt);
//                con1.getConnection().close();
            } catch (Exception e) {
            }
                return encheres;
            }
    }


