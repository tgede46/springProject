package gedeon.net.avis.service;

import gedeon.net.avis.entite.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gedeon.net");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("votre code d'activation");

        String texte= String.format("Bonjour %s, votre code d'activation est %s",
                validation.getUtilisateur().getNom(),
                validation.getCode());
        message.setText(texte);
        javaMailSender.send(message);
    }
}
