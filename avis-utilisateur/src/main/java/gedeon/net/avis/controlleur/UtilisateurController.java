package gedeon.net.avis.controlleur;


import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @PostMapping(path = "/inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        log.info("Inscription");
        this.utilisateurService.inscription(utilisateur);
    }
}