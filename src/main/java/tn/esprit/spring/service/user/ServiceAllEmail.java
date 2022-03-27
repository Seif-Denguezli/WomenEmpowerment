 package tn.esprit.spring.service.user;

import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Message.RecipientType.CC;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPTransport;

import tn.esprit.spring.entities.User;


@Service
public class ServiceAllEmail {
	
	//private JavaMailSender javaMailSender;
	
    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport("smtps");
        smtpTransport.connect("smtp.gmail.com", "womenempowermentapp@gmail.com", "womenempowerment1*");
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(String firstName, String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress("womenempowermentapp@gmail.com"));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse("seifeddine.denguezli@esprit.tn", false));
        message.setSubject("Women Empowerment - New Password");
        message.setText("Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team"+"\n From Les Elites Dev Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        return Session.getInstance(properties, null);
    }
    
    
    //-------------------EventDonation-------------------------------------------------
    private Message createEmailForEvent(String EventName, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress("womenempowermentapp@gmail.com"));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        //message.setRecipients(CC, InternetAddress.parse("bdtcourse@gmail.com", false));
        message.setSubject("Women Empowerment - Event");
        message.setText(  " EventName: " + EventName + "\n \n The Support Team"+"\n From Les Elites Dev Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    public void sendNewEventCreatedByUser(String EventName, String email) throws MessagingException {
        Message message = createEmailForEvent(EventName, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport("smtps");
        smtpTransport.connect("smtp.gmail.com", "womenempowermentapp@gmail.com", "womenempowerment1*");
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    
    
    //Reset Password Email -----------------------------------------------------------------------------------
    private MimeMessage createresetPasswordMail(String token, String email) throws MessagingException {
        MimeMessage message = new MimeMessage(getEmailSession());
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(new InternetAddress("womenempowermentapp@gmail.com"));
        helper.setTo(email);
        helper.setCc("seifeddine.denguezli@esprit.tn");
        helper.setSubject("Women Empowerment - Password Reset");
        helper.setText("<p>Your password reset request have been processed</p>"
        		+ "<p> <a href=\"http://localhost:8087/SpringMVC/api/authentication/reset-password/new?token=" + token +" \">Click here to change your password </a> </p>"
        		+ "Les Elites Dev Team"
        		+"<hr>"
        		+ "\n<p><u>If you did not request a password reset, please ignore this mail or contact an admin</u></p>", true);
        helper.setSentDate(new Date());
        return message;
    }

    public void sendNewResetPasswordMail(String token, String email) throws MessagingException {
        Message message = createresetPasswordMail(token, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport("smtps");
        smtpTransport.connect("smtp.gmail.com", "womenempowermentapp@gmail.com", "womenempowerment1*");
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    //Reset Password Email -----------------------------------------------------------------------------------
    
    
    public void sendCandidacyEmail(String firstName, String title, String email, String candidacyState) throws MessagingException {
        Message message = createCandidacyEmail( firstName,  title,  email,  candidacyState);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport("smtps");
        smtpTransport.connect("smtp.gmail.com", "womenempowermentapp@gmail.com", "womenempowerment1*");
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createCandidacyEmail(String firstName, String title, String email, String candidacyState) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress("womenempowermentapp@gmail.com"));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        //message.setRecipients(CC, InternetAddress.parse("bdtcourse@gmail.com", false));
        message.setSubject("Women Empowerment - Candidacy ");
        message.setText("Hello " + firstName + ", \n \n  We want to inform you that your Candidacy for the offer " + title + " is "+candidacyState+"."+"\n \n The Support Team"+"\n From Les Elites Dev Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }





}
