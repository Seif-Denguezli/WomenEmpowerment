package tn.esprit.spring.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.service.courses.CertificateServiceImpl;

@RestController
@RequestMapping("Certificate")//pre-path
public class CertificateController {
	@Autowired
	CertificateServiceImpl certificateService;
	@PostMapping(path="certifGen/{certificate}")
	public ResponseEntity<byte[]> certif(@PathVariable("certificate") Long certificateid) throws IOException, InterruptedException{
		Long certi = certificateid.longValue();
		byte[] res = certificateService.certif(certi);
		 return ResponseEntity.ok()
	             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Certificate.pdf") 
	             .contentType(MediaType.APPLICATION_PDF).body(res);
	}
}
