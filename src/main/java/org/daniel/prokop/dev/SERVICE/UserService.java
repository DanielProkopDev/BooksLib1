package org.daniel.prokop.dev.SERVICE;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;

import org.daniel.prokop.dev.DAO.Users;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<Users> findAll();

    long countUsers();

    Optional<Users> findById(Long id);

    Users save(Users user);

    Users updateFirstName(Users user, String newFirstname);

    Users updateLastName(Users user, String newLastname);

    void delete(Users user);

    Optional<Users> findByUsername(String username);

    Optional<Users> findUserByCompleteName(String firstName, String lastName);

    String getUserAsHtml(String username);

    List<Users> findUsersByFirstName(@Param("fn")String firstName);

    List<Users> findUsersByFirstNameLike(String firstName);

    List<Users> findUsersByLastName( @Param("ln")String lastName);

    List<Users> findUsersByLastNameLike(String lastName);

    List<Users> findUsersByRegisterDate(@Param("rd") LocalDateTime date);

    /* Set<Books> findStorageByUsername(String username);*/

    void updateStorageByUsername(String username, Set<Books> newList);

    void updateUser(@Param("id")Long id,@Param("un")String username,@Param("fn")String firstName,@Param("ln") String lastName,@Param("pw") String password);

    // List<User> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException;

    //User updatePassword(Person person, String password)throws MailSendingException;
}
