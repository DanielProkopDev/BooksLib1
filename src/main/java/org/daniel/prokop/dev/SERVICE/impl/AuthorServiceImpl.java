package org.daniel.prokop.dev.SERVICE.impl;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.REPO.AuthorRepo;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    AuthorRepo authorRepo;

    public AuthorServiceImpl(AuthorRepo authorRepo){
        this.authorRepo=authorRepo;
    }
    @Override
    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    @Override
    public long countAuthors() {
        return authorRepo.count();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepo.findAuthorById(id);
    }

    @Override
    public Author save(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public void updateAuthorFirstName(String newFirstname,Long id) {
        authorRepo.updateAuthorFirstName(newFirstname, id);
    }

    @Override
    public void updateAuthorLastName(String newLastname,Long id) {
        authorRepo.updateAuthorLastName(newLastname, id);
    }

    @Override
    public void delete(Author author) {
        authorRepo.delete(author);
    }

    @Override
    public Optional<Author> findAuthorByCompleteName(String firstName, String lastName) {
        return authorRepo.findAuthorByCompleteName(firstName,lastName);
    }

    @Override
    public String getAuthorAsHtml(String lastName) {
        final StringBuilder sb = new StringBuilder();
        authorRepo.findAuthorByLastName(lastName).ifPresentOrElse(
                p -> sb.append("<p>First Name: ").append(p.getFirstName()).append(" </p>")
                        .append("<p>Last Name: ").append(p.getLastName()).append(" </p>"),
                () -> sb.append("<p>None found with lastName ").append(lastName).append(" </p>")
        );
        return sb.toString();
    }

    @Override
    public List<Author> findAuthorsByRegisterDate(LocalDateTime date) {
        return authorRepo.findAuthorsByRegisterDate(date);
    }

    @Override
    public Optional<Author> findAuthorByBirthDate(LocalDateTime date) {
        return authorRepo.findAuthorByBirthDate(date);
    }

    @Override
    public List<Author> findAuthorsByLastNameLike(String lastName) {
        return authorRepo.findAuthorsByLastNameLike(lastName);
    }

    @Override
    public List<Author> findAuthorsByLastName(String lastName) {
        return authorRepo.findAuthorsByLastName(lastName);
    }

    @Override
    public List<Author> findAuthorsByFirstNameLike(String firstName) {
        return authorRepo.findAuthorsByFirstNameLike(firstName);
    }

    @Override
    public List<Author> findAuthorsByFirstName(String firstName) {
        return authorRepo.findAuthorsByFirstName(firstName);
    }
}
