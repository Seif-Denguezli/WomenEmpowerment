package tn.esprit.spring.service.event;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;

public interface EventService  {
	public void addEvent(Event event) ;
	public Event editEvent(Event event);
	public void removeEvent(Long IdEvent);
	public void joinEvent(Long idUser, Long idEvent );
	public Event deleteEvent(Long idUser, Long idEvent);
	public List<Event> Get_all_Event();
	//public Set<Event> Get_Event_by_User(Long idUser);
	public Long retrieveMaxEventTransactioned();
	
	
	
}
