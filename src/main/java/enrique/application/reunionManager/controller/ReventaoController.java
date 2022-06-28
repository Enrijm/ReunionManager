package enrique.application.reunionManager.controller;

import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.domain.Role;
import enrique.application.reunionManager.service.ReventaoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ReventaoController {

    private final ReventaoService service;

    @GetMapping
    public ResponseEntity<String> MostrarInfo(){
        return ResponseEntity.ok().body("Estamos dentro del API");
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



}

// for be able to manage the @RequesBody of the controller /role/addtouser
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}


