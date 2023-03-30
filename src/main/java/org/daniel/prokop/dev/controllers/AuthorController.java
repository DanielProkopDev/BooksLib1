package org.daniel.prokop.dev.controllers;


import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.daniel.prokop.dev.SERVICE.PersonService;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    private Logger logger =  LoggerFactory.getLogger(AuthorController.class);

    private AuthorService authorService;

    private UserService userService;

    Author oldAuthor;


    public AuthorController(AuthorService authorService, UserService userService) {
        this.authorService = authorService;
        this.userService = userService;
    }

    /**
     * Handles requests to list all authors.
     */
    @GetMapping(value = "/list")
    public String list(Model model, @AuthenticationPrincipal User activeUser) {
        logger.info("Populating model with list...");
        List<Author> authors =  authorService.findAll();
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        System.out.println(authors);
        authors.sort(COMPARATOR_BY_ID);
        model.addAttribute("authors", authors);
        model.addAttribute("user",user);
        System.out.println(model.getAttribute("authors"));
        return "authors/list";
    }

    /**
     * Handles requests to show detail about one author.
     */
    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser) {
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        model.addAttribute("user",user);
        Optional<Author> authorOpt = authorService.findById(id);
        if(authorOpt.isPresent()) {
            model.addAttribute("author", authorOpt.get());
        } else {
            throw new NotFoundException(Author.class, id);
        }
        return "authors/show";
    }

    @RequestMapping( value = "/saveAuthors",method = RequestMethod.GET)
    public String saveAuthors() {
        return "authors/saveAuthors";
    }

    @ModelAttribute(value = "author1")
    public Author getAuthorObject() {
        return new Author();
    }

    @RequestMapping( value = "/addAuthor",method = RequestMethod.POST)
    public String addAuthors( @ModelAttribute("author1")Author author, Model model){


        System.out.println(author);
        authorService.save(author);

        logger.info("Populating model with list...");
        List<Author> authors =  authorService.findAll();
        System.out.println(authors);
        authors.sort(COMPARATOR_BY_ID);
        model.addAttribute("authors", authors);
        System.out.println(model.getAttribute("authors"));
        return "redirect:/authors/list";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String deleteAuthors(@PathVariable(value = "id") Long id,Model model){

        Author author = authorService.findById(id).orElseThrow(() -> new NotFoundException(Books.class, id));

        System.out.println("Removing Author:" + author);

        authorService.delete(author);


        logger.info("Populating model with list...");
        List<Author> authors =  authorService.findAll();
        System.out.println(authors);
        authors.sort(COMPARATOR_BY_ID);
        model.addAttribute("authors", authors);
        System.out.println(model.getAttribute("authors"));
        return "redirect:/authors/list";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editAuthor(@PathVariable(value = "id") Long id,Model model,@AuthenticationPrincipal User activeUser) {
        Author author = authorService.findById(id).orElseThrow(() -> new NotFoundException(Author.class, id));
        oldAuthor = author;
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        model.addAttribute("authors", author);
        model.addAttribute("user",user);
        return "authors/edit";
    }

    @RequestMapping(value = "/edit/editAuthor",method = RequestMethod.POST)
    public String updateAuthor(@ModelAttribute("authors") Author author, Model model){

        System.out.println("Updating:"+ oldAuthor);
        System.out.println("New Author:" + author);
        authorService.updateAuthor(oldAuthor.getId(),author.getFirstName(),author.getLastName(),
                author.getBirthDate());

        logger.info("Populating model with list...");
        List<Author> authorList =  authorService.findAll();
        System.out.println(authorList);
        authorList.sort(COMPARATOR_BY_ID);

        model.addAttribute("authors", authorList);

        return "redirect:/authors/list";
    }
}
