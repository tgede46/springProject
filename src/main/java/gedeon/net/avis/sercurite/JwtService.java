package gedeon.net.avis.sercurite;

import gedeon.net.avis.entite.Jwt;
import gedeon.net.avis.entite.Utilisateur;
import gedeon.net.avis.repository.JwtRepository;
import gedeon.net.avis.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    private UtilisateurService utilisateurService;
    private final String SECRET_KEY = "c59491855515985997857282c041132653e1e977738399fe064636d0f3e6bfa0";
    private JwtRepository jwtRepository;

    public Map<String,String> generate(String username){
        Utilisateur utilisateur= (Utilisateur) this.utilisateurService.loadUserByUsername(username);
        final Map<String, String> jwtMap = this.generateJwt(utilisateur);
        final Jwt jwt = Jwt.builder().valeur(jwtMap.get("bearer")).desactive(false).expire(false).utilisateur(utilisateur).build();
        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    public String extractUsername(String token) {
        return this.getClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate=this.getClaims(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaims(String token, Function<Claims, T> function) {
        Claims claims=getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime+30*60*1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                Claims.EXPIRATION,new  Date(expirationTime),
                Claims.SUBJECT,utilisateur.getEmail()
        );


        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer",bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(value,false,false).orElseThrow(()->
                new RuntimeException("token invalide"));
    }

    public void deconnexion() {
        Utilisateur utilisateur =(Utilisateur)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt =this.jwtRepository.findUtilisateurValidToken(utilisateur.getEmail(),false,false)
                .orElseThrow(()->new RuntimeException("token invalide"));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }
}