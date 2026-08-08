package raizes_do_nordeste_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import raizes_do_nordeste_api.Entity.models.Usuario;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key chave = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRACAO = 86400000; // 24 horas em ms

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .claim("id", usuario.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(chave)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean tokenValid(String token, String email) {
        String emailToken = extractEmail(token);
        return emailToken.equals(email) && !tokenExpired(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean tokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
