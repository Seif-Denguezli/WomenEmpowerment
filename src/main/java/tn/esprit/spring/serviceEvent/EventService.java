package tn.esprit.spring.serviceEvent;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Event;

public interface EventService  {
	public void addEvent(Event event) ;
	public ResponseEntity<?> Update_Event(Event event, Long idEvent, Long idUser);
	public ResponseEntity<?> DeleteEvent(Long idEvent, Long idUser) ;
	public void affecterEventToUser(Long idEvent , Long idUser);
	public Event deletDoation(Long idUser, Long idEvent);
	public void removeEvent(Long IdEvent);
}
