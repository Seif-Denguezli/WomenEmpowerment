package tn.esprit.spring.controllers;

import tn.esprit.spring.entities.PasswordResetToken;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.exceptions.EmailExist;
import tn.esprit.spring.exceptions.EmailNotExist;
import tn.esprit.spring.exceptions.ResetPasswordException;
import tn.esprit.spring.exceptions.ResetPasswordTokenException;
import tn.esprit.spring.exceptions.UsernameExist;
import tn.esprit.spring.exceptions.UsernameNotExist;
import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.serviceInterface.user.AuthenticationService;
import tn.esprit.spring.serviceInterface.user.JwtRefreshTokenService;
import tn.esprit.spring.serviceInterface.user.UserService;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import net.bytebuddy.utility.RandomString;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("api/authentication")//pre-path
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @PostMapping("sign-up")//api/authentication/sign-up
    public ResponseEntity<User> signUp(@RequestBody User user) throws UsernameNotExist, UsernameExist, EmailExist, MessagingException
    {
        /*if (userService.findByUsername(user.getUsername()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);*/
    	userService.saveUser(user);
    	return new ResponseEntity<>(user, HttpStatus.CREATED);

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
    public PasswordResetToken generatePasswordResetToken(@RequestParam String email) throws EmailNotExist {
    	return authenticationService.generatePasswordResetToken(email);
    }
    
    @PostMapping("/reset-password/new")
    public void updatePassword(@RequestParam String token, @RequestParam String newPassword) throws ResetPasswordException, ResetPasswordTokenException{
    	authenticationService.updatePassword(token, newPassword);
    }

}
