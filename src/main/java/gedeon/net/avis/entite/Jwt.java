package gedeon.net.avis.entite;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "jwt")
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String valeur;
    private boolean desactive;
    private boolean expire;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur;
}
