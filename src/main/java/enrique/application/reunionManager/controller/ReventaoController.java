package enrique.application.reunionManager.controller;

import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.service.ReventaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reventao")

public class ReventaoController {

    private final ReventaoService service;

    @Autowired
    public ReventaoController(ReventaoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reventao> > getAll(){
        return new ResponseEntity<>(service.findAllReventaos(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{name}")
    public Reventao getByName(@PathVariable("name") String name){
        return service.findByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<Reventao> add(@RequestBody Reventao reventao){
        service.addReventao(reventao);
        return new ResponseEntity<>(service.addReventao(reventao),HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Reventao> update(@RequestBody Reventao reventao){
        return new ResponseEntity<>(service.updateReventao(reventao), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
