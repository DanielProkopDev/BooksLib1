package org.daniel.prokop.dev.DAO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.daniel.prokop.dev.DAO.util.DateProcessor;
import org.daniel.prokop.dev.DAO.util.Salt;
import org.daniel.prokop.dev.DAO.util.UserRole;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
@SequenceGenerator(name = "seqPersonGen", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = Users.FIND_USER_BY_COMPLETE_NAME, query = "select u from Users u where u.firstName=:fn and u.lastName=:ln"),
        @NamedQuery(name = Users.FIND_USER_BY_LAST_NAME, query = "select u from Users u where u.lastName= ?1")
})
@JsonIgnoreProperties(value="password", allowSetters = true)
public class Users extends AbstractEntity {
    public static final String FIND_USER_BY_COMPLETE_NAME = "findUserByCompleteName";
    public static final String FIND_USER_BY_LAST_NAME = "findUserByLastName";

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
    @JoinTable(name = "user_list", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Books> userSet = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 4, max = 50)
    @Column( name = "password",nullable = false)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Column(nullable = false)
    private String salt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Column(name = "roles",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole roles;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateProcessor.DATE_FORMAT)
    @NotNull(groups = BasicValidation.class)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    @Column(nullable = false)
    private LocalDateTime registerDate;


    @JsonIgnore
    @Transient
    private String newPassword;

    @JsonIgnore
    @Transient
    Salt salter= new Salt();

    public Users() {
        super();
        registerDate = LocalDateTime.now();
        salt= salter.generateSalt(5).get();
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<Books> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<Books> userSet) {
        this.userSet = userSet;
    }

    public UserRole getRoles() {
        return roles;
    }

    public void setRoles(UserRole roles) {
        this.roles = roles;
    }

    public void addBook(Books books){
        userSet.add(books);
        books.getUsers().add(this);
    }
    public void removeBook(Books books){
        userSet.remove(books);
        books.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var user = (Users) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(registerDate.toLocalDate(), user.registerDate.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, registerDate.toLocalDate());
    }

    @Override
    public String toString() {
        return String.format("User[username='%s', firstName='%s', lastName='%s', registerDate='%s']\n",
                username, firstName, lastName, registerDate == null? "" : registerDate.toString());

    }
}
