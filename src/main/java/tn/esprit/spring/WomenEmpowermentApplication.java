package tn.esprit.spring;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.serviceInterface.user.IUserService;

@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class WomenEmpowermentApplication {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(WomenEmpowermentApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(IUserService userService) {
		return args -> {
			
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPERADMIN"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			
			userService.saveUser(new User(1L, "SeifDenguezli", "seifdenguezli", "azerty123", new ArrayList<>()));
			userService.addRoleToUser("seifdenguezli", "ROLE_MANAGER");
		};
	}
}
