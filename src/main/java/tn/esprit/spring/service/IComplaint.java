package tn.esprit.spring.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Complaint;

public interface IComplaint {
	public ResponseEntity<?> addComplaint(Complaint complaint, Long userId);
	public void updatereclamation(Complaint complaint,Long complaintId);
	public void deletreclamation(Long idUser ,Long complaintId);
	public List<Complaint> showclamation();
}
