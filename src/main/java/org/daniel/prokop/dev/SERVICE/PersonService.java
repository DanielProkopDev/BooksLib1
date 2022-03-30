package org.daniel.prokop.dev.SERVICE;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonService {
    List<Person> findAll();

    long countPersons();

    Optional<Person> findById(Long id);

    Person save(Person person);

    Person updateFirstName(Person person, String newFirstname);

    Person updateLastName(Person person, String newLastname);

    void delete(Person person);

    Optional<Person> findByUsername(String username);

    Optional<Person> findByCompleteName(String firstName, String lastName);

    String getPersonAsHtml(String username);

    List<Person> findByUsernameLike(String username);

    List<Person> findByFirstName(@Param("fn")String firstName);

    List<Person> findByFirstNameLike(String firstName);

    List<Person> findByLastName( @Param("ln")String lastName);

    List<Person> findByLastNameLike(String lastName);

    List<Person> findByRegisterDate(@Param("rd") LocalDateTime date);

   /* Set<Books> findStorageByUsername(String username);*/

    void updateStorageByUsername(String username, Set<Books> newList);

   // List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException;

    //Person updatePassword(Person person, String password)throws MailSendingException;
}
