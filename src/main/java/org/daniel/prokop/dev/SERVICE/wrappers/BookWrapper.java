package org.daniel.prokop.dev.SERVICE.wrappers;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;

import java.util.HashSet;
import java.util.Set;

public class BookWrapper {

    private Long id;
    private String title;
    private String AuthorfName;
    private String AuthorlName;
    private String detailedDescription;
    private Integer price;
    private BookStatus status;
    private BookType Genre;
    private String notes;
    private Set<Person> persons = new HashSet<>();

    private boolean empty = true;

    public BookWrapper() {
    }

    public BookWrapper(Books book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.AuthorfName = book.getAuthorfname();
        this.AuthorlName = book.getAuthorlname();
        this.detailedDescription = book.getDetailedDescription();
        this.price = book.getPrice();
        this.status = book.getStatus();
        this.Genre = book.getGenre();
        this.notes = book.getNotes();
        this.persons = book.getPersons();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorfName() {
        return AuthorfName;
    }

    public void setAuthorfName(String authorfName) {
        AuthorfName = authorfName;
    }

    public String getAuthorlName() {
        return AuthorlName;
    }

    public void setAuthorlName(String authorlName) {
        AuthorlName = authorlName;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public BookType getGenre() {
        return Genre;
    }

    public void setGenre(BookType genre) {
        Genre = genre;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }
}
