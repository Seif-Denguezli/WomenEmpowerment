package tn.esprit.spring.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface IService {
	public void addService(tn.esprit.spring.entities.Service s , Long userId);
	public void updateService(tn.esprit.spring.entities.Service s,Long serviceId );
	public List<tn.esprit.spring.entities.Service> affichService( );
	public void deletService(Long serviceId );
	public List<tn.esprit.spring.entities.Service> recherche(String keyword);
}
