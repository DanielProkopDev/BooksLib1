package org.daniel.prokop.dev.SERVICE.impl;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.REPO.PersonRepo;
import org.daniel.prokop.dev.SERVICE.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Override
    public long countPersons() {
        return personRepo.count();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findPersonById(id);
    }

    @Override
    public Person save(Person person) {
        return personRepo.save(person);
    }

    @Override
    public Person updateFirstName(Person person, String newFirstname) {
        return personRepo.save(person);
    }

    @Override
    public Person updateLastName(Person person, String newLastname) {
        return personRepo.save(person);
    }

    @Override
    public void delete(Person person) {
         personRepo.delete(person);
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepo.findByUsername(username);
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        return personRepo.findByCompleteName(firstName,lastName);
    }

    @Override
    public String getPersonAsHtml(String username) {
        final StringBuilder sb = new StringBuilder();
        personRepo.findByUsername(username).ifPresentOrElse(
                p -> sb.append("<p>First Name: ").append(p.getFirstName()).append(" </p>")
                        .append("<p>Last Name: ").append(p.getLastName()).append(" </p>"),
                () -> sb.append("<p>None found with username ").append(username).append(" </p>")
        );
        return sb.toString();
    }

    @Override
    public List<Person> findByUsernameLike(String username) {
        return personRepo.findByUsernameLike(username);
    }

    @Override
    public List<Person> findByFirstName(String firstName) {
        return personRepo.findByFirstName(firstName);
    }

    @Override
    public List<Person> findByFirstNameLike(String firstName) {
        return personRepo.findByFirstNameLike(firstName);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return personRepo.findByLastName(lastName);
    }

    @Override
    public List<Person> findByLastNameLike(String lastName) {
        return personRepo.findByLastNameLike(lastName);
    }

    @Override
    public List<Person> findByRegisterDate(LocalDateTime date) {
        return personRepo.findByRegisterDate(date);
    }

  /*  @Override
    public Set<Books> findStorageByUsername(String username) {
        return personRepo.findStorageByUsername(username);

    }*/

    @Override
    public void updateStorageByUsername(String username, Set<Books> newList) {
        personRepo.updateStorageByUsername(username,newList);
    }
}
