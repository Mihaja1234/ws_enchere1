package Enchere.controllerVIEW;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController2 {

    @GetMapping("/")
    public String IndexUtilisateur(Model model) {
       // model.addAttribute("email", "hardi@gmail.com");
       // model.addAttribute("mdp","hardi");
        //return "logutilisateur";
        return "indexutilisateur";
    }

    @GetMapping("/Inscription")
    public String pageInscription()
    {
        return "Inscription";
    }

}
