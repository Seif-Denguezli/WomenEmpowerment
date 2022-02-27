package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import tn.esprit.spring.enumerations.Job;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class User implements Serializable{
	
	// Basic attributes for all actors
	
	public User(Long id, String name, String username, String password, Collection<Role> roles) {
		this.userId = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;
	
	String name;
	
	String email;
	
	@Temporal(TemporalType.DATE)
	Date birthDate;
	
	String username;
	
	String password;
	
	@OneToOne
	Media profilPicture;
	
	@OneToOne
	Subscription subscription;
	
	@ManyToMany(fetch = FetchType.EAGER)
	Collection<Role> roles = new ArrayList<>();
	
	@OneToOne
	User woman; // Reflexive association
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
	Set<Candidacy> candidacies; //Candidatures postulées;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<Appointment> appointments; // Booked appointments
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Complaint> complaints; // Created complaints
	
	@ManyToMany(cascade = CascadeType.ALL)
	Set<Event> joinedEvents; // Events that user joined
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "donor")
	Set<Donation> donations;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Advertising> advertising;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<Certificate> obtainedCertificates; // Certificates obtained after joining courses


	
	//******************************************************************//
	
	// Specefic Expert Attributes
	
	Long nbCasesSolved;
	
	@Enumerated(EnumType.STRING)
	Job job;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Service> createdServices;
	
	
	//******************************************************************//
	
	// Specefic Former Attributes
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Course> createdCourses; // Courses created By the former
	
	
	//******************************************************************//
	
	// Specefic Company Attributes
	
	String activityDomain;
	
	String address;
	
	@Temporal(TemporalType.DATE)
	Date establishmentDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Offer> createdOffers;
	
	
	//******************************************************************//
	
	// Specefic Association Attributes
	
	int nbEventsCreated;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Event> createdEvents;
	
	
	//******************************************************************//
	
	// Specefic Forum Attributes
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Post> posts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostLike> postLikes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostDislike> postDislikes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostComment> postComments;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<CommentLike> commentLikes;
	
	
	//******************************************************************//


	
	}
