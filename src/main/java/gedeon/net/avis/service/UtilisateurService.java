package gedeon.net.avis.service;

import gedeon.net.avis.TypeDeRole;
import gedeon.net.avis.entite.Role;
import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.entite.Validation;
import gedeon.net.avis.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {
    private  UtilisateurRepository utilisateurRepository;
    private  PasswordEncoder passwordEncoder;
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

    public void activation(Map<String,String> activation){
        Validation validation=this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("votre code a expire");
        }
        Utilisateur utilisateurActiver =this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(()->new RuntimeException("utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }
}