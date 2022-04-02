package org.daniel.prokop.dev.controllers;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory;
import org.daniel.prokop.dev.DAO.Person;
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
@RequestMapping("/persons")
public class PersonController {



    private Logger logger =  LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    /**
     * Handles requests to list all persons.
     */
    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
        List<Person> persons =  personService.findAll();
        System.out.println(persons);
        persons.sort(COMPARATOR_BY_ID);
        model.addAttribute("persons", persons);
        System.out.println(model.getAttribute("persons"));
        return "persons/list";
    }

    /**
     * Handles requests to show detail about one person.
     */
    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            model.addAttribute("person", personOpt.get());
        } else {
            throw new NotFoundException(Person.class, id);
        }
        return "persons/show";
    }

   
}
