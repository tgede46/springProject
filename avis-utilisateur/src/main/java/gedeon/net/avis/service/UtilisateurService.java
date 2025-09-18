package gedeon.net.avis.service;

import gedeon.net.avis.TypeDeRole;
import gedeon.net.avis.entite.Role;
import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    private final PasswordEncoder passwordEncoder;
    private ValidationService validationService;
    public void inscription(Utilisateur utilisateur){
       if(!utilisateur.getEmail().contains("@")){
           throw new RuntimeException("Votre mail invalide");
       }

        if(!utilisateur.getEmail().contains(".")){
            throw new RuntimeException("Votre mail invalide");
        }


        // Basic password validation
        String mdp = utilisateur.getMdp();
        if (mdp == null || mdp.isBlank()) {
            throw new RuntimeException("mot de passe requis");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()){
            throw new RuntimeException("Votre mail est deja utiliser");
        }

        String mdpCrypte = this.passwordEncoder.encode(mdp);
        utilisateur.setMdp(mdpCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
        utilisateur.setRole(roleUtilisateur);

        utilisateur= this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);
    }
}