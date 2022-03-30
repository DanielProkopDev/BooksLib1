package org.daniel.prokop.dev.controllers;


import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.daniel.prokop.dev.SERVICE.PersonService;
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
@RequestMapping(value = "/authors")
public class AuthorController {

    private Logger logger =  LoggerFactory.getLogger(AuthorController.class);

    private AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Handles requests to list all authors.
     */
    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
        List<Author> authors =  authorService.findAll();
        System.out.println(authors);
        authors.sort(COMPARATOR_BY_ID);
        model.addAttribute("authors", authors);
        System.out.println(model.getAttribute("authors"));
        return "authors/list";
    }

    /**
     * Handles requests to show detail about one author.
     */
    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
        Optional<Author> authorOpt = authorService.findById(id);
        if(authorOpt.isPresent()) {
            model.addAttribute("author", authorOpt.get());
        } else {
            throw new NotFoundException(Author.class, id);
        }
        return "authors/show";
    }
}
