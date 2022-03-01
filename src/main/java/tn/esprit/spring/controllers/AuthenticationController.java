package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.serviceInterface.user.IUserService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		if ( userService.getUser(user.getUsername()) != null){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

}
