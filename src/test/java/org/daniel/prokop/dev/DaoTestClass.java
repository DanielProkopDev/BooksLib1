package org.daniel.prokop.dev;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.DAO.util.DateProcessor;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.daniel.prokop.dev.SERVICE.BooksService;
import org.daniel.prokop.dev.SERVICE.PersonService;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.config.DataSourceConfig;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

//@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
public class DaoTestClass {

    @Autowired
    PersonService personService;

    @Autowired
    BooksService booksService;

    @Autowired
    UserService userService;

    @Autowired
    AuthorService authorService;

    Person person;

    Books books;

    Set<Books> listOfBooks = new HashSet<>();
    List<Integer> listOfIds= new ArrayList<>();

    @Test
    void GETPerson(){
       person = personService.findByUsername("daniel123").get();
        System.out.println(person.getFirstName());
        System.out.println(person.getId());
        System.out.println(person.getCreatedAt());
        System.out.println(person.getStorage());
    }
    @Test
    void addPerson(){
        books = new Books();
        books.setAuthorfname("Kaliprostak");
        books.setAuthorlname("Bigos");
        books.setPrice(15);
        books.setTitle("Story of Kaliprostaks12345678");
        books.setGenre(BookType.FANTASY);
        books.setStatus(BookStatus.AVAILABLE);
        booksService.save(books);
        listOfBooks.add(books);


        person = new Person();
        person.setUsername("danielll");
        person.setPassword("john");
        person.setFirstName("daniel");
        person.setLastName("nowak");
        person.setStorage(listOfBooks);
       personService.save(person);
    }

    @Test
    void addBooks(){
        books = new Books();
        books.setAuthorfname("Kaliprostak");
        books.setAuthorlname("Bigos");
        books.setPrice(15);
        books.setTitle("Story of Kaliprostaks");
        books.setGenre(BookType.FANTASY);
        books.setStatus(BookStatus.AVAILABLE);
       // listOfBooks = booksService.findAll();

        if (!listOfBooks.contains(books)){
        booksService.save(books);}else System.out.println("book is there");
    }

    @Test
    void findBooks(){
        books = booksService.findBooksByTitle("Story of Kaliprostaks").get();
        System.out.println(books.getAuthorfname());
        System.out.println(books.getAuthorlname());
        System.out.println(books.getGenre());
        System.out.println(books.getStatus());
        System.out.println(books.getId());
    }

    /**
     * Test updateStorage() will fail because of Hibernate- I'm not 100% sure but so far from my research it won't work, but
     * there is a workaround by using personService.save().
     */

    @Test
    void updateStorage(){
        person= personService.findByUsername("daniel123456").get();
        Books book = booksService.findBooksByTitle("Story of Kaliprostaks").get();
        Set<Books> booksSet = new HashSet<>();
        booksSet.add(book);
        personService.updateStorageByUsername(person.getUsername(),booksSet);

    }

    @Test
    void findStorageByUsername(){
       // listOfBooks = personService.findStorageByUsername("daniel123456");
        person = personService.findByUsername("daniel123456").get();
        listOfBooks = person.getStorage();
        System.out.println(listOfBooks);
    }

    @Test
    void findAllTest(){
        List<Person> personList = personService.findAll();
        System.out.println(personList);
    }

    @Test
    void addUser(){
        Users user = new Users();
        user.setUsername("danielos");
        user.setFirstName("daniel");
        user.setLastName("prokop");
        user.setPassword("akuku");
        user.setSalt("abcd");

        System.out.println("Saving:"+user);
        userService.save(user);


    }
    @Test
    void getUser(){
        Users user=userService.findByUsername("danielos").get();

        System.out.println(user);

    }

    @Test
    void addAuthor(){
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("tolkien");
        author.setBirthDate(DateProcessor.toDate("1983-08-15 00:20"));

        System.out.println("Saving:" + author);
        authorService.save(author);
    }

    @Test
    void setUserBooks(){
        Users user = userService.findByUsername("danielos").get();
        Books book = booksService.findBooksByTitle("Story of Kaliprostaks").get();
        Set<Books> booksSet = new HashSet<>();
        booksSet.add(book);
        System.out.println(booksSet);
        user.setUserSet(booksSet);
        System.out.println(user.getUserSet());
        userService.save(user);
    }
    @Test
    void setAuthorBooks(){
       Author author = authorService.findAuthorByCompleteName("john","tolkien").get();
        Books book = booksService.findBooksByTitle("Story of Kaliprostaks").get();
        Set<Books> booksSet = new HashSet<>();
        booksSet.add(book);
        System.out.println(booksSet);
        author.setAuthorSet(booksSet);
        System.out.println(author.getAuthorSet());
        authorService.save(author);
    }

    @Test
    void updateAuthorFirstName(){
        Author author = authorService.findAuthorByCompleteName("john","tolkien").get();
        Long id = author.getId();
        System.out.println(id);
        authorService.updateAuthorFirstName("daniel",id);
    }


}
