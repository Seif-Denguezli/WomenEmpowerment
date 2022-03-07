package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sun.xml.bind.v2.runtime.reflect.Accessor.GetterSetterReflection;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.serviceRepo;
import tn.esprit.spring.repository.userRepo;


@Service
public class serviceService implements IService {
	@Autowired
	serviceRepo servrepo;
	@Autowired
	userRepo userrepo;

	@Override
	public void addService(tn.esprit.spring.entities.Service s , Long userId){
		User u = userrepo.findById(userId).orElse(null);
		
	

		servrepo.save(s);
	}
	@Override
	public void updateService(tn.esprit.spring.entities.Service s,Long serviceId ){
		tn.esprit.spring.entities.Service serv = servrepo.findById(serviceId).orElse(null);
		
		serv.setJob(s.getJob());
		serv.setStartDate(s.getStartDate());
		serv.setEndDate(s.getEndDate());
		servrepo.flush();
		
		
	}
	@Override
	public List<tn.esprit.spring.entities.Service> affichService( ){
		return servrepo.findAll();
	}
	@Override
	public void deletService(Long serviceId ){
		
		servrepo.deleteById(serviceId);
	}
	@Override
	public List<tn.esprit.spring.entities.Service> recherche(String keyword){
	if(keyword != null){
		return	servrepo.recherche(keyword);
	}
		return servrepo.findAll();
	}
	}
	
