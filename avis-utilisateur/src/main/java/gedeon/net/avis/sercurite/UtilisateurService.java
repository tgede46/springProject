package gedeon.net.avis.sercurite;

import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UtilisateurService {
    private UtilisateurRepository utilisateurRepository;

    public void inscription(Utilisateur utilisateur){
        this.utilisateurRepository.save(utilisateur);
    }
}
