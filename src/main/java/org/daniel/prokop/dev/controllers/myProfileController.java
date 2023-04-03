package org.daniel.prokop.dev.controllers;


import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.daniel.prokop.dev.SERVICE.BooksService;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping("/MyProfile")

public class myProfileController {

    private BooksService booksService;

    private AuthorService authorService;

    private UserService userService;

    private Users oldUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger =  LoggerFactory.getLogger(UserController.class);

    public myProfileController(BooksService booksService,AuthorService authorService, UserService userService){
        this.booksService = booksService;
        this.authorService = authorService;
        this.userService = userService;

    }

    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model, @AuthenticationPrincipal User activeUser) {
        Optional<Users> userOpt = userService.findById(id);
        if(userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            throw new NotFoundException(Users.class, id);
        }
        return "MyProfile/myProfile";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUsers(@PathVariable(value = "id") Long id,Model model) {
        Users user = userService.findById(id).orElseThrow(() -> new NotFoundException(Users.class, id));
        oldUser = user;
        model.addAttribute("user", user);
        return "MyProfile/edit";
    }

    @RequestMapping(value = "/edit/editUser",method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") Users user, Model model,@AuthenticationPrincipal User activeUser){

        System.out.println("Updating:"+ oldUser);
        System.out.println("New Author:" + user);
        userService.updateUser(oldUser.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(),passwordEncoder.encode(user.getPassword()));

        logger.info("Populating model with list...");
        Users users =  userService.findByUsername(activeUser.getUsername()).get();
        System.out.println(users);
        model.addAttribute("user", users);
        System.out.println(model.getAttribute("user"));
        return "MyProfile/myProfile";
    }


}
