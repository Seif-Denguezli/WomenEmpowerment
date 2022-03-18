package tn.esprit.spring.controllers;

import tn.esprit.spring.entities.Media;
import tn.esprit.spring.entities.PasswordResetToken;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.exceptions.EmailExist;
import tn.esprit.spring.exceptions.EmailNotExist;
import tn.esprit.spring.exceptions.ResetPasswordException;
import tn.esprit.spring.exceptions.ResetPasswordTokenException;
import tn.esprit.spring.exceptions.UsernameExist;
import tn.esprit.spring.exceptions.UsernameNotExist;
import tn.esprit.spring.repository.MediaRepo;
import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.serviceInterface.user.AuthenticationService;
import tn.esprit.spring.serviceInterface.user.JwtRefreshTokenService;
import tn.esprit.spring.serviceInterface.user.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.utility.RandomString;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("api/authentication")//pre-path
public class AuthenticationController
{
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads/";
	
	ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
    
    @Autowired
    MediaRepo mediaRepository;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @PostMapping(value="sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})//api/authentication/sign-up
    public ResponseEntity<User> signUp(@RequestPart("user") String user, @RequestPart("file") MultipartFile file) throws UsernameNotExist, UsernameExist, EmailExist, MessagingException, IOException
    {
    	//upload file
    	
    	File convertFile = new File(uploadDirectory+file.getOriginalFilename());
    	convertFile.createNewFile();
    	FileOutputStream fout = new FileOutputStream(convertFile);
    	fout.write(file.getBytes());
    	fout.close();
    	Media profilPicture = new Media();
    	profilPicture.setImagenUrl(uploadDirectory+file.getOriginalFilename());
    	profilPicture = mediaRepository.save(profilPicture);
    	User userData = objectMapper.readValue(user, User.class);
    	userData.setProfilPicture(profilPicture);
    	
    	
    	//////
    	
    	userService.saveUser(userData);
    	return new ResponseEntity<>(userData, HttpStatus.CREATED);

    }

    @PostMapping("sign-in")//api/authentication/sign-in
    public ResponseEntity<?> signIn(@RequestBody User user)
    {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")//api/authentication/refresh-token?token=
    public ResponseEntity<?> refreshToken(@RequestParam String token)
    {
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> generatePasswordResetToken(@RequestParam String email) throws EmailNotExist {
    	return new ResponseEntity<>(authenticationService.generatePasswordResetToken(email), HttpStatus.OK);
    }
    
    @PostMapping("/reset-password/new")
    public ResponseEntity<?> updatePassword(@RequestParam String token, @RequestBody String newPassword) throws ResetPasswordException, ResetPasswordTokenException{
    	authenticationService.updatePassword(token, newPassword);
    	return new ResponseEntity<>("Your password has been successfully updated !", HttpStatus.OK);
    }

}
