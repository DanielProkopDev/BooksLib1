package org.daniel.prokop.dev.REPO;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonRepo extends JpaRepository<Person,Long> {

    @Query("select p from Person p where p.id=:id")
    Optional<Person> findPersonById(@Param("id")Long id);

    @Query("select p from Person p where p.username=:un")
    Optional<Person> findByUsername(@Param("un")String username);

    @Query("select p from Person p where p.username like %?1%")
    List<Person> findByUsernameLike(String username);

    @Query("select p from Person p where p.firstName=:fn and p.lastName=:ln")
    Optional<Person> findByCompleteName(@Param("fn")String fn, @Param("ln")String lastName);

    @Query("select p from Person p where p.firstName=:fn")
    List<Person> findByFirstName(@Param("fn")String firstName);

    @Query("select p from Person p where p.firstName like %?1%")
    List<Person> findByFirstNameLike(String firstName);

    @Query("select p from Person p where p.lastName=:ln")
    List<Person> findByLastName( @Param("ln")String lastName);

    @Query("select p from Person p where p.lastName like %?1%")
    List<Person> findByLastNameLike(String lastName);

    @Query("select p from Person p where p.registerDate=:rd")
    List<Person> findByRegisterDate(@Param("rd") LocalDateTime date);

   /* @Query("select p from Person p where p.username=:un")
    Set<Books> findStorageByUsername(@Param("un") String username);*/

    @Modifying
    @Query("update Person  set storage = :ls    where username=:un")
    void updateStorageByUsername(@Param("un") String username,@Param("ls") Set<Books> newList);
}
