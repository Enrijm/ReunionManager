package enrique.application.reunionManager.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.domain.Role;
import enrique.application.reunionManager.service.ReventaoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ReventaoController {

    private final ReventaoService service;

    @GetMapping
    public ResponseEntity<String> MostrarInfo(){
        return ResponseEntity.ok().body("Good logging inside the API");
    }

    @GetMapping("/reventaos")
    public ResponseEntity<List<Reventao> > getAll(){
        return ResponseEntity.ok().body(service.findAllReventaos());
    }

    @GetMapping("/nombre/{name}")
    public Reventao getByName(@PathVariable("name") String name){
        return service.findByName(name);
    }

    @PostMapping("/reventao/add")
    public ResponseEntity<Reventao> add(@RequestBody Reventao reventao){
        //return new ResponseEntity<>(service.addReventao(reventao),HttpStatus.CREATED);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/reventao/add").toUriString());
        return ResponseEntity.created(uri).body(service.addReventao(reventao));
    }

    @PostMapping("/role/add")
    public ResponseEntity<Role> add(@RequestBody Role role){
        //return new ResponseEntity<>(service.addReventao(reventao),HttpStatus.CREATED);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/add").toUriString());
        return ResponseEntity.created(uri).body(service.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToReventao(@RequestBody RoleToUserForm form){
        service.addRoleToReventao(form.getUsername(),form.getRolename());
        // i am not putting here the body because AddRoleToReventao is a void
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Reventao> update(@RequestBody Reventao reventao){
        return ResponseEntity.ok().body(service.updateReventao(reventao));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        // Login - Success - Request - "Bearer " + Token - Reponse
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decoded = verifier.verify(refresh_token);
                // when we verify that the token is valid
                String username = decoded.getSubject();
                Reventao reventao = service.findByName(username);

                String access_token = JWT.create()
                        .withSubject(reventao.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000 )) // 10 min will expired the token
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",reventao.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                // For put it as a JSON
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());

                // For put it as a JSON
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");

        }


    }



}

// for be able to manage the @RequesBody of the controller /role/addtouser
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}


