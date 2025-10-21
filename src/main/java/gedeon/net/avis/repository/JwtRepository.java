package gedeon.net.avis.repository;

import gedeon.net.avis.entite.Jwt;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValeur(String value);
}
