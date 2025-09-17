package gedeon.net.avis.controlleur;


import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.sercurite.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        log.info("Inscription");
        this.utilisateurService.inscription(utilisateur);
    }
}