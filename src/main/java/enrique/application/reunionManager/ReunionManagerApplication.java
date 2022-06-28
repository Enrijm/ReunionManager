package enrique.application.reunionManager;

import enrique.application.reunionManager.domain.Reventao;
import enrique.application.reunionManager.domain.Role;
import enrique.application.reunionManager.service.ReventaoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ReunionManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReunionManagerApplication.class, args);
	}

	@Bean // If I do not put the annotation @Beans SB will not know where are my args!!
	CommandLineRunner run(ReventaoService reventaoService){
		// all inside here will be actived when the app will execute
		return args -> {
			// adding Roles
			reventaoService.saveRole(new Role(null,"ROLE_USER"));
			reventaoService.saveRole(new Role(null,"ROLE_ADMIN"));
			reventaoService.saveRole(new Role(null,"ROLE_MANAGER"));
			reventaoService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
			// adding reventaos
			reventaoService.addReventao(new Reventao(null,"Sergio","Terry","Le faltan 2 puertas a su coche",15,60,"Siempre",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Julio","Pachulio","Rockstar",16,45,"FinDe",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Rafa","Afa","Rata",20,60,"FinDe",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Ignacio","Gargola","Niñas para niño",15,60,"Entre Semana",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Juan","JuanilloRaves","Cuidado si mochillo",15,60,"Siempre",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Enrique","Kike","Creador",20,45,"FinDe",new ArrayList<>()));
			reventaoService.addReventao(new Reventao(null,"Alvaro","Bamba","Don para Cara Plastico",20,50,"FinDe",new ArrayList<>()));

			// adding Roles
			reventaoService.addRoleToReventao("Enrique","ROLE_MANAGER");
			reventaoService.addRoleToReventao("Enrique","ROLE_ADMIN");
			reventaoService.addRoleToReventao("Enrique","ROLE_SUPER_ADMIN");
			reventaoService.addRoleToReventao("Juan","ROLE_USER");
			reventaoService.addRoleToReventao("Ignacio","ROLE_USER");
			reventaoService.addRoleToReventao("Julio","ROLE_USER");
			reventaoService.addRoleToReventao("Rafa","ROLE_USER");
			reventaoService.addRoleToReventao("Sergio","ROLE_USER");
			reventaoService.addRoleToReventao("Alvaro","ROLE_USER");
		};

	}

}
