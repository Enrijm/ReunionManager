package enrique.application.reunionManager.service;

import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.exceptions.ReventaoNotFoundException;
import enrique.application.reunionManager.repo.ReventaoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ReventaoService {

    private final ReventaoRepo reventaoRepo;

    @Autowired
    public ReventaoService(ReventaoRepo reventaoRepo) {
        this.reventaoRepo = reventaoRepo;
    }

    public Reventao addReventao(Reventao reventao){
        return reventaoRepo.save(reventao);
    }

    public List<Reventao> findAllReventaos(){
        return reventaoRepo.findAll();
    }

    public Reventao findByName(String name){
        return reventaoRepo.findReventaoByName(name).
                orElseThrow(()-> new ReventaoNotFoundException("Reventao con nombre: "+name+" no encontrado"));
    }

    public Reventao updateReventao(Reventao reventaoUpdate){
        return reventaoRepo.save(reventaoUpdate);
    }

    public void deleteById(Long id){
        reventaoRepo.deleteReventaoById(id)
                .orElseThrow(()-> new ReventaoNotFoundException("Reventao con id: "+id+" no encontrado"));

    }

}
