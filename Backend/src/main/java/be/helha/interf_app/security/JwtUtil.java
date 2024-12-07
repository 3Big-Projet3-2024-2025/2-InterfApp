package be.helha.interf_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import be.helha.interf_app.Controller.UserController;
import be.helha.interf_app.Model.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;


@Component
public class JwtUtil {
    private final static String key = "mklsqfmlkqsjdfoziuhbrfzeamzekpoiwxnbjlxdwf";
    private final static SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    
    private UserController userController;

    public JwtUtil(UserController userController) {
        this.userController = userController;
    }

    public static String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+3600000))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "roles", user.getRoles()
                ))
                .signWith(secretKey)
                .compact();
    }

    public Authentication validateJWT(String JWT){
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        Claims claims = jwtParser.parseSignedClaims(JWT).getPayload();
        String email = (String)claims.get("email");
        String roles = (String)claims.get("roles");

        if (Objects.nonNull(email)){
            List<SimpleGrantedAuthority> authorities = List.of(roles.split(","))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
               
            User user = userController.getUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            return new UsernamePasswordAuthenticationToken(email, user.getPassword(), authorities);
        
                
        }

        return null;
    }
}
