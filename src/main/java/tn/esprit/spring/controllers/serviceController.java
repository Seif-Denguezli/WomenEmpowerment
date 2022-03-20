package tn.esprit.spring.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Service;
import tn.esprit.spring.service.IService;
import tn.esprit.spring.service.serviceService;

@RestController
@RequestMapping("/service")
public class serviceController {
	@Autowired
	IService servserv;
	@PostMapping("/addService/{userId}")
	@ResponseBody
	public void addService(@RequestBody tn.esprit.spring.entities.Service s,@PathVariable Long userId ){
		servserv.addService(s, userId);
	}
@PutMapping("/updateService/{serviceId}")
@ResponseBody
public void updateService(@RequestBody tn.esprit.spring.entities.Service s,@PathVariable Long serviceId ){
	servserv.updateService(s, serviceId);
}
@GetMapping("/affichge")

public List<Service> affichService( ){
	return servserv.affichService();
	
}
@DeleteMapping("delete/{serviceId}")
@ResponseBody
public void deletService(@PathVariable Long serviceId ){
	servserv.deletService(serviceId);
}
@GetMapping("recherche")
public List<tn.esprit.spring.entities.Service> recherche(String keyword){
	return servserv.recherche(keyword);
}
/*
@GetMapping("/filtredate")	
List<Service> getAllBetweenDates(@RequestBody LocalDate start, @RequestBody LocalDate end){
	return servserv.getAllBetweenDates(null, null);
}*/
}
