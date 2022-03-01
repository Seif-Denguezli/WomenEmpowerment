package tn.esprit.spring.serviceInterface.user;

import java.util.List;


import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;

public interface IUserService{
	
	User saveUser(User user);
	
	User modifyUser(String username, User user);
	
	void deleteUser(String username);
	
	Role saveRole(Role role);
	
	void deleteRole(Long roleId);
	
	void addRoleToUser(String username, String roleName);
	
	User getUser(String username);
	
	List<User> getUsers();

}
