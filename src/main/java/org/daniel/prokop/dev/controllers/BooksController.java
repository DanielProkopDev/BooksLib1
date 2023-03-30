package org.daniel.prokop.dev.controllers;

import org.daniel.prokop.dev.DAO.Author;
import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.SERVICE.AuthorService;
import org.daniel.prokop.dev.SERVICE.BooksService;
import org.daniel.prokop.dev.SERVICE.UserService;
import org.daniel.prokop.dev.SERVICE.wrappers.BookWrapper;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping("/books")
public class BooksController {

    private Logger logger = LoggerFactory.getLogger(BooksController.class);

    private BooksService booksService;

    private AuthorService authorService;

    private UserService userService;

    public BooksController(BooksService booksService, AuthorService authorService, UserService userService) {
        this.booksService = booksService;
        this.authorService = authorService;
        this.userService = userService;
    }

    private Books oldBook;

    /**
     * Handles requests to list all books.
     */
    @GetMapping(value = "/list")
    public String list(Model model, @AuthenticationPrincipal User activeUser) {
        logger.info("Populating model with list...");
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        model.addAttribute("user",user);
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);
        return "books/list";
    }

    /**
     * Handles requests to show detail about one book.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model, @AuthenticationPrincipal User activeUser) {
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        model.addAttribute("user",user);
        Books books = booksService.findBooksById(id).orElseThrow(() -> new NotFoundException(Books.class, id));
        model.addAttribute("books", books);
        return "books/show";
    }
    /**
     * Handles requests to show form to add new Books.
     */

    @RequestMapping( value = "/saveBooks",method = RequestMethod.GET)
    public String saveBooks(){
        return "books/saveBooks";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBooks(@PathVariable(value = "id") Long id,Model model){
        Books book = booksService.findBooksById(id).orElseThrow(() -> new NotFoundException(Books.class, id));
        oldBook=book;
        model.addAttribute("books",book);
        return "books/edit";
    }

    @ModelAttribute(value = "books1")
    public Books getBooksObject() {
        return new Books();
    }
    /** ArrayList of Enums which is used by jsp file to generate radio buttons*/
    @ModelAttribute(value = "genre")
    public List<BookType> getGenreList() {
       List<BookType> bookTypeList = new ArrayList<>();
       bookTypeList.add(BookType.COMEDY);
       bookTypeList.add(BookType.DRAMA);
        bookTypeList.add(BookType.DOCUMENT);
        bookTypeList.add(BookType.FANTASY);
        bookTypeList.add(BookType.HORROR);
        bookTypeList.add(BookType.SCIFI);
        return bookTypeList;
    }
/** ArrayList of Enums which is used by jsp file to generate radio buttons*/
    @ModelAttribute(value = "status")
        public List<BookStatus> getStatusList(){
        List<BookStatus> bookStatusList = new ArrayList<>();
        bookStatusList.add(BookStatus.SALE);
        bookStatusList.add(BookStatus.AVAILABLE);
        bookStatusList.add(BookStatus.SOLD);
        bookStatusList.add(BookStatus.OUTOFSTOCK);
        return bookStatusList;
    }

/** Adds book to database and checks if author already exist in db, if not it creates author - body needs trimming coming soon*/
    @RequestMapping( value = "/addBook",method = RequestMethod.POST)
    public String addBooks( @ModelAttribute("books1")Books book, Model model){

        System.out.println(book);
        System.out.println(book.getAuthorfname());
        System.out.println(book.getAuthorlname());
        System.out.println(book.getBirthDate());

        Optional<Author> author = (authorService.findAuthorByCompleteName(book.getAuthorfname(),book.getAuthorlname()));

        if(author.isPresent()) {

            booksService.save(book);
            Author author1 = authorService.findAuthorByCompleteName(book.getAuthorfname(),book.getAuthorlname()).get();
            author1.addBook(book);
            authorService.save(author1);

        logger.info("Populating model with list...");
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);
        }else{
            Author author1 = new Author();
            author1.setFirstName(book.getAuthorfname());
            author1.setLastName(book.getAuthorlname());
            author1.setBirthDate(book.getBirthDate());
            authorService.save(author1);
            booksService.save(book);
            author1.addBook(book);
            authorService.save(author1);

            logger.info("Populating model with list...");
            List<Books> books =  booksService.findAll();
            System.out.println(books);
            books.sort(COMPARATOR_BY_ID);

            model.addAttribute("books", books);
        }

        return "redirect:/books/list";

    }
/** Deletes book from db - body needs trimming*/
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String deleteBooks(@PathVariable(value = "id") Long id,Model model){

        Books book = booksService.findBooksById(id).orElseThrow(() -> new NotFoundException(Books.class, id));

        System.out.println("Removing Book:" + book);

        booksService.delete(book);

        logger.info("Populating model with list...");
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);

        return "redirect:/books/list";
    }
    /** Edits book from db - body needs trimming*/
    @RequestMapping(value = "/edit/editBook",method = RequestMethod.POST)
    public String updateBooks(@ModelAttribute("books") Books book, Model model){

        System.out.println("Updating:"+ oldBook);
        System.out.println("New Book:" + book);
        booksService.updateBookByTitle(oldBook.getId(),book.getTitle(),book.getAuthorfname(),
                book.getAuthorlname(),book.getPrice(),book.getStatus(),book.getGenre(),
                book.getDetailedDescription(),book.getAmount(),book.getNotes());

        logger.info("Populating model with list...");
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);

        return "redirect:/books/list";
    }

    /**FOR SOME REASON HIBERNATE ALWAYS OPENS SET<BOOKS> AND TRY TO PASS CONTENT INSTEAD OF WHOLE SET - Data Access Layer issue?
     * as well for some reason Books book doesnt pass var amount as for example books.getAmount() will return null, in function above it works and it is using same model "books" and in both cases var is exposed
     * in the view, model isn't empty because it actually adds the right book to the user(by id), needs debugging?
     //Set<Books> booksSet = user.getUserSet();
     //booksSet.add(book);
     //userService.updateStorageByUsername(activeUser.getUsername(), booksSet);*/

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.POST)
    public String buyBook(@ModelAttribute("books")Books book, @AuthenticationPrincipal User activeUser, Model model){

        Books someBook = booksService.findBooksById(book.getId()).get();
        System.out.println(someBook.getAmount());
        System.out.println(book.getAmount());
        Users user = userService.findByUsername(activeUser.getUsername()).get();
        user.addBook(book);
        userService.save(user);
        booksService.updateBookAmountById(someBook.getAmount()-1,someBook.getId());




        return "redirect:/books/list";
    }


}

