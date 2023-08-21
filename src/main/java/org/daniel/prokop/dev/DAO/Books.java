package org.daniel.prokop.dev.DAO;


import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.DAO.util.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="books")
public class Books extends AbstractEntity{


    @NotEmpty
    @Column(name="title", unique = true, nullable = false)
    private String title;

    @NotNull
    @Column(name="authorfname")
    private String authorfname;

    @NotEmpty
    @Column(name="authorlname")
    private String authorlname;

    @Transient
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    private LocalDateTime birthDate;

    @Column(name="detaileddescription")
    private String detailedDescription;

    @NotNull
    @Column(name="price")
    private BigDecimal price;


    @Column(name="amount")
    private Integer amount;
    @NotNull
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @NotNull
    @Column(name ="genre")
    @Enumerated(EnumType.STRING)
    private BookType Genre;

    //very big text
    @Column(name="notes")
    private String notes;

    @ManyToMany(mappedBy = "storage",fetch = FetchType.EAGER)
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(mappedBy = "userSet",fetch = FetchType.EAGER)
    private Set<Users> users = new HashSet<>();

    @ManyToMany(mappedBy = "authorSet",fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorfname() {
        return authorfname;
    }

    public void setAuthorfname(String authorfname) {
        this.authorfname = authorfname;
    }

    public String getAuthorlname() {
        return authorlname;
    }

    public void setAuthorlname(String authorlname) {
        this.authorlname = authorlname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }


    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public void addPerson(Person person){
        persons.add(person);
        person.getStorage().add(this);
    }

    public void removePerson(Person person){
        persons.remove(person);
        person.getStorage().remove(this);
    }

    public void addUser(Users user){
        users.add(user);
        user.getUserSet().add(this);
    }
    public void removeUser(Users user){
        users.remove(user);
        user.getUserSet().remove(this);
    }
    public void addAuthor(Author author){
        authors.add(author);
        author.getAuthorSet().add(this);
    }
    public void removeAuthor(Author author){
        authors.remove(author);
        author.getAuthorSet().remove(this);
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString(){
        return String.format("Books[id='%d%n', title='%s',createdAt='%s', modifiedAt='%s', version='%d%n']",
                id,title, DateProcessor.toString(createdAt), DateProcessor.toString(modifiedAt), version);
    }
}
