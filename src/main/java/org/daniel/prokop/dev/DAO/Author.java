package org.daniel.prokop.dev.DAO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.daniel.prokop.dev.DAO.util.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="author")
@SequenceGenerator(name = "seqPersonGen", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = Author.FIND_AUTHOR_BY_COMPLETE_NAME, query = "select a from Author a where a.firstName=:fn and a.lastName=:ln"),
        @NamedQuery(name = Author.FIND_AUTHOR_BY_LAST_NAME, query = "select a from Author a where a.lastName= ?1")
})
@JsonIgnoreProperties(value="password", allowSetters = true)
public class Author extends AbstractEntity {
    public static final String FIND_AUTHOR_BY_COMPLETE_NAME = "findAuthorByCompleteName";
    public static final String FIND_AUTHOR_BY_LAST_NAME = "findAuthorByLastName";

    public interface BasicValidation{}


    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(name="firstname",nullable = false)
    private String firstName;

    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(name="lastname",nullable = false)
    private String lastName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "author_list", joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Books> authorSet = new HashSet<>();



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateProcessor.DATE_FORMAT)
    @NotNull(groups = BasicValidation.class)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    @Column(nullable = false)
    private LocalDateTime birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateProcessor.DATE_FORMAT)
    @NotNull(groups = BasicValidation.class)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    @Column(nullable = false)
    private LocalDateTime registerDate;


    public Author() {
        super();
        registerDate = LocalDateTime.now();
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

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Books> getAuthorSet() {
        return authorSet;
    }

    public void setAuthorSet(Set<Books> authorSet) {
        this.authorSet = authorSet;
    }

    public void addBook(Books books){
        authorSet.add(books);
        books.getAuthors().add(this);
    }
    public void removeBook(Books books){
        authorSet.remove(books);
        books.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var author = (Author) o;
        return Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(registerDate.toLocalDate(), author.registerDate.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, registerDate.toLocalDate());
    }

    @Override
    public String toString() {
        return String.format("Author[, firstName='%s', lastName='%s', registerDate='%s']\n",
                firstName, lastName, registerDate == null? "" : registerDate.toString());

    }
}
