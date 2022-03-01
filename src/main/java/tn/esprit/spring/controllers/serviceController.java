package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.service.serviceService;

@RestController
public class serviceController {
	@Autowired
	serviceService servserv;
	@PostMapping("/addService/{userId}")
	@ResponseBody
	public void addService(@RequestBody tn.esprit.spring.entities.Service s,@PathVariable Long userId ){
		servserv.addService(s, userId);
	}
@PutMapping("/updateService")
@ResponseBody
public void updateService(@RequestBody tn.esprit.spring.entities.Service s ){
	servserv.updateService(s);
}

}
