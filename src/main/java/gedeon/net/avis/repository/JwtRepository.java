package gedeon.net.avis.repository;

import gedeon.net.avis.entite.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValeur(String value);

    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);
    @Query("FROM Jwt J WHERE J.expire=:expire  AND J.desactive=:desactive AND J.utilisateur.email=:email")
    Optional<Jwt> findUtilisateurValidToken(String email, boolean desactive, boolean expire);
    @Query("FROM Jwt J WHERE  J.utilisateur.email=:email")
    Stream<Jwt> findUtilisateur(String email);
}
