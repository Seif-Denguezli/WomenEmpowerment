package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.serviceInterface.user.UserService;

/**
 * @author sa
 * @date 29.10.2021
 * @time 15:21
 */
@RestController
@RequestMapping("api/admin")//pre-path
public class AdminController
{
    @Autowired
    private UserService userService;

    @GetMapping("all")//api/admin/all
    public ResponseEntity<?> findAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
