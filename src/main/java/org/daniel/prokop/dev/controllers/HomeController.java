package org.daniel.prokop.dev.controllers;


import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model,@AuthenticationPrincipal User activeUser) {

        Users user = userService.findByUsername(activeUser.getUsername()).get();
        System.out.println(user);
        model.addAttribute("user",user);
        model.addAttribute("message", "Spring MVC example with JSP");

        return "home";
    }


}
