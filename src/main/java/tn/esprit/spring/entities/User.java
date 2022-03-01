package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
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
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

	
	String email;
	
	@Temporal(TemporalType.DATE)
	Date birthDate;

    @Transient
    private String accessToken;

    @Transient
    private String refreshToken;
	
	@OneToOne
	Media profilPicture;
	
	@OneToOne
	Subscription subscription;
	
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
