package org.daniel.prokop.dev.DAO;

import org.daniel.prokop.dev.DAO.util.DateProcessor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="person")
@SequenceGenerator(name = "seqPersonGen", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = Person.FIND_BY_COMPLETE_NAME, query = "select p from Person p where p.firstName=:fn and p.lastName=:ln"),
        @NamedQuery(name = Person.FIND_BY_LAST_NAME, query = "select p from Person p where p.lastName= ?1")
})
@JsonIgnoreProperties(value="password", allowSetters = true)
public class Person extends AbstractEntity {
    public static final String FIND_BY_COMPLETE_NAME = "findByCompleteName";
    public static final String FIND_BY_LAST_NAME = "findAllByLastName";

    public interface BasicValidation{}

    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(name="username",nullable = false, unique = true)
    private String username;

    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(name="firstname",nullable = false)
    private String firstName;

    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(name="lastname",nullable = false)
    private String lastName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "storage_list", joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Books> storage = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 4, max = 50)
    @Column(nullable = false)
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateProcessor.DATE_FORMAT)
    @NotNull(groups = BasicValidation.class)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    @Column(nullable = false)
    private LocalDateTime registerDate;


    @JsonIgnore
    @Transient
    private String newPassword;

    public Person() {
        super();
        registerDate = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Set<Books> getStorage() {
        return storage;
    }

    public void setStorage(Set<Books> storage) {
        this.storage = storage;
    }

    public void addBook(Books books){
        storage.add(books);
        books.getPersons().add(this);
    }
    public void removeBook(Books books){
        storage.remove(books);
        books.getPersons().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(registerDate.toLocalDate(), person.registerDate.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, registerDate.toLocalDate());
    }

    @Override
    public String toString() {
        return String.format("Person[username='%s', firstName='%s', lastName='%s', registerDate='%s']\n",
                username, firstName, lastName, registerDate == null? "" : registerDate.toString());

    }
}
