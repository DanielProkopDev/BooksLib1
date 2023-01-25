package org.daniel.prokop.dev.controllers;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.DAO.util.GenerateSalt;
import org.daniel.prokop.dev.DAO.util.UserRole;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {


    private Logger logger =  LoggerFactory.getLogger(RegisterController.class);

    private UserService userService;

    Users oldUser;



    public RegisterController(UserService userService) {
        this.userService=userService;
    }

    @ModelAttribute(value = "user3")
    public Users getUsersObject() {
        return new Users();
    }

    @GetMapping("/register")
    public String register(){

        return "/register";
    }
    @RequestMapping( value = "/addUser",method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user3") Users user, Model model) throws NoSuchAlgorithmException, NoSuchProviderException {

        System.out.println(user);
        System.out.println(user.getUsername());
        System.out.println(user.getLastName());

        String salt = GenerateSalt.getSalt();
        System.out.println(salt);

        Optional<Users> userOld= userService.findByUsername(user.getUsername());
        if (userOld.isPresent()){

        }else{
            Users userNew= new Users();
            userNew.setUsername(user.getUsername());
            userNew.setFirstName(user.getFirstName());
            userNew.setLastName(user.getLastName());
            userNew.setPassword(user.getPassword());
            userNew.setRoles(user.getRoles());
            userNew.setRegisterDate(LocalDateTime.now());
            userNew.setSalt(salt);
            userService.save(userNew);
            model.addAttribute("user3", user);
        }




        return "redirect:/login";
    }

    @ModelAttribute(value = "roles")
    public List<UserRole> getRolesList() {
        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(UserRole.ADMIN);
        userRoleList.add(UserRole.USER);
        userRoleList.add(UserRole.CUSTOMER);

        return userRoleList;
    }

}
