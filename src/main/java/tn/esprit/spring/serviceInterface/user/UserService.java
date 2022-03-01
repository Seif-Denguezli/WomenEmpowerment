package tn.esprit.spring.serviceInterface.user;



import java.util.List;
import java.util.Optional;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;

/**
 * @author sa
 * @date 29.10.2021
 * @time 12:03
 */
public interface UserService
{
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);

    List<User> findAllUsers();
}
