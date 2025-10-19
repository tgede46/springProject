package gedeon.net.avis.service;

import gedeon.net.avis.entite.Avis;
import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AvisService {
    private final AvisRepository avisRepository;

    public void creer(Avis avis){
        Utilisateur utilisateur=(Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUtilisateur(utilisateur);
        this.avisRepository.save(avis);
    }
}
