package tn.esprit.spring.service;

import java.util.List;

import tn.esprit.spring.entities.Appointment;

public interface IAppointment  {
	public void addRdv(Appointment apt ,Long serviceId , Long userId,Long expert_id);
	public void updateRdv(Appointment apt ,Long serviceId,Long appointmentId);
	public List<Appointment> affichRdv();
	public void deleteAppoitment(Long appointmentId);
	public void NombresCaseSolved();
}
