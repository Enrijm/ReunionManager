package enrique.application.reunionManager.repo;

import com.fasterxml.jackson.annotation.OptBoolean;
import enrique.application.reunionManager.domain.Reventao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface ReventaoRepo extends JpaRepository<Reventao,Long> {
    public Optional<Reventao> deleteReventaoById(Long id);
    public Optional<Reventao> findReventaoByName(String name);
}
