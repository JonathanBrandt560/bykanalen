package se.JonathanAnton.bykanalen.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/** Service-klass för att skapa och validera JWT-tokens (JSON Web Tokens).
 Används av UserService vid inloggning (för att utfärda en ny token) och av
 JwtAuthFilter vid varje inkommande förfrågan (för att verifiera en befintlig token
 och identifiera vilken användare den tillhör). */
@Service
public class JwtService {
    /* Hemlig nyckel som används för att signera/verifiera tokens. Läses från
    application.properties. Måste hållas hemlig - alla som känner till den
     kan skapa giltiga tokens. */
    @Value("${jwt.secret}")
    private String secret;

    // Hur länge en token är giltig, angivet i millisekunder.
    @Value("${jwt.expiration}")
    private long expiration;

    /* Skapar en ny signerad JWT-token för den angivna användaren.
     "Subject" i token sätts till användarnamnet, vilket gör att vi senare
     kan identifiera vem token tillhör via extractUsername(). */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /* Läser ut användarnamnet (subject) från en token, efter att ha verifierat
    att signaturen är giltig. Kastar ett undantag om token är felaktig eller manipulerad. */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /* Kontrollerar att token tillhör rätt användare OCH att den inte har gått ut.
    Används av JwtAuthFilter innan en förfrågan tillåts passera som autentiserad. */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Kontrollerar enbart om tokens utgångsdatum har passerat.
    private boolean isTokenExpired(String token) {
        Date expiry = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiry.before(new Date());
    }

    /* Omvandlar den hemliga nyckel-strängen till en SecretKey-instans.
    Samma nyckel måste användas vid både signering (generateToken) och
    verifiering (extractUsername/isTokenExpired) - annars misslyckas all validering. */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}