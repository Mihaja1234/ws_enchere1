package Enchere.controllerVIEW;

import Enchere.dao.AdminDao;
import Enchere.dao.PrelevementEnchereDao;
import Enchere.dao.TokenAdminDao;
import Enchere.dao.UtilisateurDao;
import Enchere.ObjectBdd.ManipDb;
import Enchere.connexion.Connexion;
import Enchere.models.Admin;
import Enchere.models.CategorieProduit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

@Controller
public class AdministrateurController {

    Connexion con;

    Connection con1 = ManipDb.pgConnect("postgres","enchere","hasinjaka");

    PrelevementEnchereDao p = new PrelevementEnchereDao();
    AdminDao a = new AdminDao();

    CategorieProduit cp = new CategorieProduit();
    UtilisateurDao ud = new UtilisateurDao();

    public AdministrateurController() throws Exception {
    }

    @GetMapping("/")
    public String logadmin(Model model) {
        model.addAttribute("email", "admin@example.com");
        model.addAttribute("mdp","adminpassword");
        return "logadmin";
    }

    @PostMapping("/login")
    public String traitementLogin(HttpServletRequest request,Model m) throws Exception{

        HttpSession session = request.getSession(true);
        String id = request.getParameter("email");
        String mdp = request.getParameter("mdp");
        AdminDao dao = new AdminDao();
        TokenAdminDao tokendao= new TokenAdminDao();
        if(dao.login(id,mdp)!=null){
            Admin admin = dao.login(id,mdp);
            String token_admin = tokendao.insertTokenAdmin(admin);
            session.setAttribute("admin", admin);
            session.setAttribute("token",token_admin);
            return "redirect:/backOffice";
        }else{
            return "redirect:/?error=1";
        }
    }

    @RequestMapping("/backOffice")
    public String backOffice(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        if(session.getAttribute("admin")!=null){
            request.setAttribute("chiffreAffaire",p.ChiffreAffaire(con));
            return "backOffice";
        }else {
            return "/";
        }
    }

    @RequestMapping("/ListeRechargementCompte")
    public String ListeRechargementCompte(HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        if(session.getAttribute("admin")!=null){
            request.setAttribute("chiffreAffaire",p.ChiffreAffaire(con));
            List<Object[]> listeRechargementCompte = a.listeRechargementCompte(con);
            request.setAttribute("listeRechargementCompte", listeRechargementCompte);
            return "ListeRechargementCompte";
        }else {
            return "/";
        }
    }

    @RequestMapping("/Validation/{idRechargementCompte}/{idUtilisateur}/{montant}")
    public String ListeRechargementCompte(HttpServletRequest request,@PathVariable int idRechargementCompte,@PathVariable int idUtilisateur,@PathVariable float montant) throws Exception{
        HttpSession session = request.getSession();
        if(session.getAttribute("admin")!=null){
            a.ValiderRechargementCompte(idRechargementCompte,con);
            ud.setCompteUser(idUtilisateur,montant,con);
            request.setAttribute("chiffreAffaire",p.ChiffreAffaire(con));
            List<Object[]> listeRechargementCompte = a.listeRechargementCompte(con);
            request.setAttribute("listeRechargementCompte", listeRechargementCompte);
            return "ListeRechargementCompte";
        }else {
            return "/";
        }
    }

    @RequestMapping("/logout")
    public String deconnexion(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        TokenAdminDao dao = new TokenAdminDao();
        dao.deleteTokenAdmin((String) session.getAttribute("token"), admin.getIdadmin());
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/newCategorie")
    public String newCategorie(HttpServletRequest request) throws Exception {
        String typecategorie = request.getParameter("typeCategorie");
        CategorieProduit c = new CategorieProduit();
        c.setTypeCategorie(typecategorie);
        c.NewCategorie(con);
        return "redirect:/backOffice";
    }

}

