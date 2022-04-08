package org.daniel.prokop.dev.REPO;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepo extends JpaRepository<Author,Long> {

    @Query("select a from Author a where a.id=:id")
    Optional<Author> findAuthorById(@Param("id")Long id);


    @Query("select a from Author a where a.firstName=:fn and a.lastName=:ln")
    Optional<Author> findAuthorByCompleteName(@Param("fn")String fn, @Param("ln")String lastName);

    @Query("select a from Author a where a.lastName=:ln")
    Optional<Author> findAuthorByLastName(@Param("ln")String lastname);

    @Query("select a from Author a where a.firstName=:fn")
    List<Author> findAuthorsByFirstName(@Param("fn")String firstName);

    @Query("select a from Author a where a.firstName like %?1%")
    List<Author> findAuthorsByFirstNameLike(String firstName);

    @Query("select a from Author a where a.lastName=:ln")
    List<Author> findAuthorsByLastName( @Param("ln")String lastName);

    @Query("select a from Author a where a.lastName like %?1%")
    List<Author> findAuthorsByLastNameLike(String lastName);

    @Query("select a from Author a where a.registerDate=:rd")
    List<Author> findAuthorsByRegisterDate(@Param("rd") LocalDateTime date);

    @Query("select a from Author a where a.birthDate=:bd")
    Optional<Author> findAuthorByBirthDate(@Param("bd") LocalDateTime date);

    @Modifying
    @Query("update Author set firstName=:fn where id=:id")
    void updateAuthorFirstName(@Param("fn")String firstName,@Param("id")Long id);

    @Modifying
    @Query("update Author set lastName=:ln where id=:id")
    void updateAuthorLastName(@Param("ln")String lastName,@Param("id")Long id);

    @Modifying
    @Query("update Author set birthDate=:bd where id=:id")
    void updateAuthorBirthDate(@Param("id")Long id,@Param("bd") LocalDateTime date);

    @Modifying
    @Query("update Author set firstName=:fn, lastName=:ln, birthDate=:bd where id=:id")
    void updateAuthor(@Param("id")Long id,@Param("fn")String firstName,@Param("ln")String lastName,@Param("bd") LocalDateTime date);


   /* @Query("select p from Person p where p.username=:un")
    Set<Books> findStorageByUsername(@Param("un") String username);*/

 /*   @Query("update Person  set storage = :ls    where username=:un")
    Optional<Person> updateStorageByUsername(@Param("un") String username,@Param("ls") Set<Books> newList);*/
}
