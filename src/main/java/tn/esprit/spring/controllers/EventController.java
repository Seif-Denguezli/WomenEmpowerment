package tn.esprit.spring.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import tn.esprit.spring.entities.APIResponse;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Media;
import tn.esprit.spring.entities.SmsRequest;
import tn.esprit.spring.repository.MediaRepo;
import tn.esprit.spring.service.event.EventService;
import tn.esprit.spring.serviceInterface.event.CloudinaryService;
import tn.esprit.spring.serviceInterface.event.MediaService;

@CrossOrigin
@RestController
@RequestMapping("/Event")
public class EventController {
	@Autowired
	EventService eventService;
	
	@Autowired
    CloudinaryService cloudinaryService;
	
	@Autowired
	MediaService mediaService;
	
	
	
	
	@PostMapping("/add-Event")
	@ResponseBody
	public void addEvent(@RequestBody Event event) {
		eventService.addEvent(event);
	}
	

	  
	@PostMapping(path = "joinEvent/{userid}/{idEvent}")
	public void joinEvent(@PathVariable("userid")Long userid,@PathVariable("idEvent")Long idEvent) {
		
		eventService.joinEvent(userid, idEvent);
		
	}
	@PostMapping(path = "createEventByUser/{userid}")
	public void createEventByUser(@PathVariable("userid")Long userid,@RequestBody Event event) throws MessagingException {
		eventService.createEventbyUser(userid, event);
		
		
	}
	@GetMapping("/Get-all-Event")
	public List<Event> Get_all_Event(){
		return eventService.Get_all_Event();
	}
	
	
	@GetMapping("/userAllDonation/{userid}")
	public Long getUSERDonationByID(@PathVariable("userid")Long userid){
		return eventService.findUserDonationsById(userid);
	}
	  
	  
	  
	@PutMapping("/userparticipe-event/{userid}/{eventId}")
	public void getUSERDonationByID(@PathVariable("userid")Long userid,@PathVariable("eventId")Long eventId){
		 eventService.Participer_event(userid,eventId);
	}
	
	@GetMapping("/object_status/{eventId}")
	public String objectif_status(@PathVariable("eventId")Long eventId){
		return eventService.TargetAtrbut(eventId);
	}
	
	@PutMapping("/affecte-par-avie/{eventId}")
	public Event affect_par_avie(@PathVariable("eventId")Long eventId){
		return eventService.affecte_place_event_byavie(eventId);
	}
	
	//----------pagination------//
    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    private APIResponse<Page<Event>> getProductsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<Event> productsWithPagination = eventService.findEventWithPaginationAndSorting(offset, pageSize, field);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
	
	
	
	//---------------media-------------//
	
	
	
	
	@PostMapping("/upload")
	    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile)throws IOException {
	        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
	        if(bi == null){
	            return new ResponseEntity("invalid image", HttpStatus.BAD_REQUEST);
	        }
	        Map result = cloudinaryService.upload(multipartFile);
	        Media media =
	                new Media((String)result.get("original_filename"),
	                        (String)result.get("url"),
	                        (String)result.get("imagencode"));
	        mediaService.save(media);
			return  new ResponseEntity("uploaded image", HttpStatus.OK);
	        
	    }

	 
	 
	    @DeleteMapping("/deleteImage/{id}")
	    public ResponseEntity<?> delete(@PathVariable("id") Long id)throws IOException {
	        if(!mediaService.exists(id))
	            return new ResponseEntity("does not exist", HttpStatus.NOT_FOUND);
	        Media media = mediaService.getOne(id).get();
	        Map result = cloudinaryService.delete(media.getImagencode());
	        mediaService.delete(id);
	        return new ResponseEntity("imagen eliminada", HttpStatus.OK);
	    }
	
	
	
	
	
	
	  
}
