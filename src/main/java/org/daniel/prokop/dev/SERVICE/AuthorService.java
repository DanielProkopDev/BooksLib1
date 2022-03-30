package org.daniel.prokop.dev.SERVICE;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    List<Author> findAll();

    long countAuthors();

    Optional<Author> findById(Long id);

    Author save(Author author);

    void updateAuthorFirstName(String newFirstname, Long id);

    void updateAuthorLastName(String newLastname, Long id);

    void delete(Author author);

    Optional<Author> findAuthorByCompleteName(String firstName, String lastName);

    String getAuthorAsHtml(String username);

    List<Author> findAuthorsByRegisterDate(@Param("rd") LocalDateTime date);

    Optional<Author> findAuthorByBirthDate(@Param("bd") LocalDateTime date);

    List<Author> findAuthorsByLastNameLike(String lastName);

    List<Author> findAuthorsByLastName( @Param("ln")String lastName);

    List<Author> findAuthorsByFirstNameLike(String firstName);

    List<Author> findAuthorsByFirstName(@Param("fn")String firstName);
    // List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException;

}
