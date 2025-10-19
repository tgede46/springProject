package gedeon.net.avis.service;

import gedeon.net.avis.entite.Avis;
import gedeon.net.avis.repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AvisService {
    private final AvisRepository avisRepository;

    public void creer(Avis avis){
        this.avisRepository.save(avis);
    }
}
