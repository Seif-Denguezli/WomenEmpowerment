package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import tn.esprit.spring.enumerations.Job;
import tn.esprit.spring.enumerations.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "users")
public class User implements Serializable{
	
	// Basic attributes for all actors


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "name", nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

	String email;
	
	@Temporal(TemporalType.DATE)
	Date birthDate;

    @Transient
    String accessToken;

    @Transient
    String refreshToken;
	
	@JsonIgnore
	@OneToOne
	Media profilPicture;
	
	@JsonIgnore
	@OneToOne
	Subscription subscription;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	Set<Notification> notifications;

	

	@OneToOne
	User woman; // Reflexive association
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
	Set<Candidacy> candidacies; //Candidatures postulées;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<Appointment> appointments; // Booked appointments
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Complaint> complaints; // Created complaints
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	Set<Event> joinedEvents; // Events that user joined
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "donor")
	Set<Donation> donations;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Advertising> advertising;
	

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<Certificate> obtainedCertificates; // Certificates obtained after joining courses


	
	//******************************************************************//
	
	// Specefic Expert Attributes
	
	Long nbCasesSolved;
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	Job job;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Service> createdServices;
	
	
	//******************************************************************//
	
	// Specefic Former Attributes
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Course> createdCourses; // Courses created By the former
	
	
	//******************************************************************//
	
	// Specefic Company Attributes
	
	String activityDomain;
	
	String address;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	Date establishmentDate;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Offer> createdOffers;
	
	
	//******************************************************************//
	
	// Specefic Association Attributes
	
	int nbEventsCreated;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	List<Event> createdEvents;
	
	
	//******************************************************************//
	
	// Specefic Forum Attributes
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	Set<Post> posts;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostLike> postLikes;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostDislike> postDislikes;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<PostComment> postComments;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	Set<CommentLike> commentLikes;
	
	
	//******************************************************************//


	
	}
