package org.daniel.prokop.dev.controllers;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private Logger logger =  LoggerFactory.getLogger(UserController.class);

    private UserService userService;


    public UserController(UserService userService) {
        this.userService=userService;
    }

    /**
     * Handles requests to list all users.
     */
    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
        List<Users> users =  userService.findAll();
        System.out.println(users);
        users.sort(COMPARATOR_BY_ID);
        model.addAttribute("users", users);
        System.out.println(model.getAttribute("users"));
        return "users/list";
    }

    /**
     * Handles requests to show detail about one user.
     */
    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
        Optional<Users> userOpt = userService.findById(id);
        if(userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            throw new NotFoundException(Users.class, id);
        }
        return "users/show";
    }
}

