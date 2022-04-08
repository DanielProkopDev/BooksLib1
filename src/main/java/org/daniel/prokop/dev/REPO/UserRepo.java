package org.daniel.prokop.dev.REPO;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.DAO.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<Users,Long> {

    @Query("select u from Users u where u.id=:id")
    Optional<Users> findUserById(@Param("id")Long id);

    @Query("select u from Users u where u.username=:un")
    Optional<Users> findUserByUsername(@Param("un")String username);

    @Query("select u from Users u where u.username like %?1%")
    List<Users> findUsersByUsernameLike(String username);

    @Query("select u from Users u where u.firstName=:fn and u.lastName=:ln")
    Optional<Users> findUserByCompleteName(@Param("fn")String fn, @Param("ln")String lastName);

    @Query("select u from Users u where u.firstName=:fn")
    List<Users> findUsersByFirstName(@Param("fn")String firstName);

    @Query("select u from Users u where u.firstName like %?1%")
    List<Users> findUsersByFirstNameLike(String firstName);

    @Query("select u from Users u where u.lastName=:ln")
    List<Users> findUsersByLastName( @Param("ln")String lastName);

    @Query("select u from Users u where u.lastName like %?1%")
    List<Users> findUsersByLastNameLike(String lastName);

    @Query("select u from Users u where u.registerDate=:rd")
    List<Users> findUsersByRegisterDate(@Param("rd") LocalDateTime date);

    @Modifying
    @Query("update Users set username=:un, firstName=:fn, lastName=:ln, password=:pw where id=:id")
    void updateUser(@Param("id")Long id,@Param("un")String username,@Param("fn")String firstName,@Param("ln") String lastName,@Param("pw") String password);

   /* @Query("select p from Person p where p.username=:un")
    Set<Books> findStorageByUsername(@Param("un") String username);*/

   /* @Query("update Person  set storage = :ls    where username=:un")
    Optional<Person> updateStorageByUsername(@Param("un") String username,@Param("ls") Set<Books> newList);*/
}