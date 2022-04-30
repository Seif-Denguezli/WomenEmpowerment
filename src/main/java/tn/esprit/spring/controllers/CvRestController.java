package tn.esprit.spring.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.esprit.spring.entities.Candidacy;
import tn.esprit.spring.entities.CvInfo;
import tn.esprit.spring.service.offer.CvStorageServiceImpl;
import tn.esprit.spring.serviceInterface.offer.ICandidacyService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/Cv")
public class CvRestController {
	 @Autowired
	  private CvStorageServiceImpl storageService;
	 @Autowired
		ICandidacyService CandidacyService;
	  @PostMapping("/upload/{userId}/{offerId}")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file,@PathVariable("userId") Long userId,@PathVariable("offerId") Long offerId) {
	    String message = "";

	    try {
	    	CandidacyService.postulerOffre(offerId, userId);
	      storageService.store(file,userId);
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
		    

	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      

	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	    
	  }
	  @GetMapping("/Cv")
	  public ResponseEntity<List<ResponseFile>> getListFiles() {
	    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/cv/")
	          .path(dbFile.getId())
	          .toUriString();
	      return new ResponseFile(
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getType(),
	          dbFile.getData().length);
	    }).collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
	  @GetMapping("/Cv/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
	    CvInfo fileDB = storageService.getFile(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	  }
	  @DeleteMapping("/delete/{id}")
	  public void deleteFile(@PathVariable("id")String idFile) {
		  storageService.removeFile(idFile);
		
	  }

}
