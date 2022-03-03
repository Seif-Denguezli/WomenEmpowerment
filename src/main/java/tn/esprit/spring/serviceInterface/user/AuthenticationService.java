package tn.esprit.spring.serviceInterface.user;

import tn.esprit.spring.entities.User;


public interface AuthenticationService
{
    User signInAndReturnJWT(User signInRequest);
}
