package enrique.application.reunionManager.service;

import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.domain.Role;
import enrique.application.reunionManager.exceptions.ReventaoNotFoundException;
import enrique.application.reunionManager.repo.ReventaoRepo;
import enrique.application.reunionManager.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ReventaoService implements UserDetailsService {

    private final ReventaoRepo reventaoRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Reventao reventao = this.findByName(username);
        if(reventao == null){
            log.error("Reventao not found in the database");
            throw new ReventaoNotFoundException("Reventao not found in the database");
        }else{
            log.info("Reventao found in the database: {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        reventao.getRoles().forEach(role ->
            {authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
        return new org.springframework.security.core.userdetails.User(reventao.getName(),reventao.getPassword(),authorities);
    }


    public Reventao addReventao(Reventao reventao){
        log.info("Saving new reventao {} to DataBase",reventao.getName());
        reventao.setPassword(passwordEncoder.encode(reventao.getPassword()));
        return reventaoRepo.save(reventao);
    }

    public List<Reventao> findAllReventaos(){
        log.info("Fetching all the reventaos");
        return reventaoRepo.findAll();
    }

    public Reventao findByName(String name){
        log.info("Fetching reventao {}",name);
        return reventaoRepo.findReventaoByName(name).
                orElseThrow(()-> new ReventaoNotFoundException("Reventao con nombre: "+name+" no encontrado"));
    }

    public Role saveRole(Role role){
        log.info("Saving new role {} to DataBase",role.getName());
        return roleRepo.save(role);}

    public void addRoleToReventao(String reventaoName,String roleName){
        log.info("Adding {} role to {} reventao",roleName,reventaoName);
        Reventao reventao = this.findByName(reventaoName);
        Role role = roleRepo.findByName(roleName);
        reventao.getRoles().add(role);

    }



    public Reventao updateReventao(Reventao reventaoUpdate){
        return reventaoRepo.save(reventaoUpdate);
    }

    public void deleteById(Long id){
        reventaoRepo.deleteReventaoById(id)
                .orElseThrow(()-> new ReventaoNotFoundException("Reventao con id: "+id+" no encontrado"));

    }

}
