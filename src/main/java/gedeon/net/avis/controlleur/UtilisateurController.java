package gedeon.net.avis.controlleur;


import gedeon.net.avis.dto.AuthentificationDTO;
import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.sercurite.JwtService;
import gedeon.net.avis.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        log.info("Inscription");
        this.utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String,String> activation){
        this.utilisateurService.activation(activation);
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "connexion")
    public Map<String,String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );
        if (authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }
}