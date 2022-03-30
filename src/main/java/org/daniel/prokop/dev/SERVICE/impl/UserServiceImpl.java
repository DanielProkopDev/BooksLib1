package org.daniel.prokop.dev.SERVICE.impl;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.REPO.UserRepo;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public List<Users> findAll() {
        return userRepo.findAll();
    }

    @Override
    public long countUsers() {
        return userRepo.count();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepo.findUserById(id);
    }

    @Override
    public Users save(Users user) {
        return userRepo.save(user);
    }

    @Override
    public Users updateFirstName(Users user, String newFirstname) {
        return userRepo.save(user);
    }

    @Override
    public Users updateLastName(Users user, String newLastname) {
        return userRepo.save(user);
    }

    @Override
    public void delete(Users user) {
      userRepo.delete(user);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public Optional<Users> findUserByCompleteName(String firstName, String lastName) {
        return userRepo.findUserByCompleteName(firstName,lastName);
    }

    @Override
    public String getUserAsHtml(String username) {
        final StringBuilder sb = new StringBuilder();
        userRepo.findUserByUsername(username).ifPresentOrElse(
                p -> sb.append("<p>First Name: ").append(p.getFirstName()).append(" </p>")
                        .append("<p>Last Name: ").append(p.getLastName()).append(" </p>"),
                () -> sb.append("<p>None found with username ").append(username).append(" </p>")
        );
        return sb.toString();
    }

    @Override
    public List<Users> findUsersByFirstName(String firstName) {
        return userRepo.findUsersByFirstName(firstName);
    }

    @Override
    public List<Users> findUsersByFirstNameLike(String firstName) {
        return userRepo.findUsersByFirstNameLike(firstName);
    }

    @Override
    public List<Users> findUsersByLastName(String lastName) {
        return userRepo.findUsersByLastName(lastName);
    }

    @Override
    public List<Users> findUsersByLastNameLike(String lastName) {
        return userRepo.findUsersByLastNameLike(lastName);
    }

    @Override
    public List<Users> findUsersByRegisterDate(LocalDateTime date) {
        return userRepo.findUsersByRegisterDate(date);
    }

    @Override
    public Optional<Users> updateStorageByUsername(String username, Set<Books> newList) {
        return Optional.empty();
    }
}
