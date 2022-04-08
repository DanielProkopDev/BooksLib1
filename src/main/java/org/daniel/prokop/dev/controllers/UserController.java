package org.daniel.prokop.dev.controllers;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private Logger logger =  LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    Users oldUser;

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

    @ModelAttribute(value = "users1")
    public Users getUserObject() {
        return new Users();
    }

    @RequestMapping( value = "/saveUsers",method = RequestMethod.GET)
    public String saveUsers() {
        return "users/saveUsers";
    }

    @RequestMapping( value = "/addUser",method = RequestMethod.POST)
    public String addUsers( @ModelAttribute("user1")Users user, Model model){


        System.out.println(user);
        userService.save(user);

        logger.info("Populating model with list...");
        List<Users> users =  userService.findAll();
        System.out.println(users);
        users.sort(COMPARATOR_BY_ID);
        model.addAttribute("users", users);
        System.out.println(model.getAttribute("users"));
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String deleteUsers(@PathVariable(value = "id") Long id,Model model){

        Users user = userService.findById(id).orElseThrow(() -> new NotFoundException(Users.class, id));

        System.out.println("Removing Author:" + user);

        userService.delete(user);


        logger.info("Populating model with list...");
        List<Users> users =  userService.findAll();
        System.out.println(users);
        users.sort(COMPARATOR_BY_ID);
        model.addAttribute("users", users);
        System.out.println(model.getAttribute("users"));
        return "redirect:/users/list";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUsers(@PathVariable(value = "id") Long id,Model model) {
        Users user = userService.findById(id).orElseThrow(() -> new NotFoundException(Users.class, id));
        oldUser = user;
        model.addAttribute("users", user);
        return "users/edit";
    }

    @RequestMapping(value = "/edit/editUser",method = RequestMethod.POST)
    public String updateAuthor(@ModelAttribute("users") Users user, Model model){

        System.out.println("Updating:"+ oldUser);
        System.out.println("New Author:" + user);
        userService.updateUser(oldUser.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(),user.getPassword());

        logger.info("Populating model with list...");
        List<Users> users =  userService.findAll();
        System.out.println(users);
        users.sort(COMPARATOR_BY_ID);
        model.addAttribute("users", users);
        System.out.println(model.getAttribute("users"));
        return "redirect:/users/list";
    }
}

