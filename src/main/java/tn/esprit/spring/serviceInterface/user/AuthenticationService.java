package tn.esprit.spring.serviceInterface.user;

import tn.esprit.spring.entities.User;

/**
 * @author sa
 * @date 29.10.2021
 * @time 13:55
 */
public interface AuthenticationService
{
    User signInAndReturnJWT(User signInRequest);
}
