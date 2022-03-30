package org.daniel.prokop.dev.controllers;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.SERVICE.BooksService;
import org.daniel.prokop.dev.SERVICE.wrappers.BookWrapper;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.daniel.prokop.dev.DAO.util.Functions.COMPARATOR_BY_ID;

@Controller
@RequestMapping("/books")
public class BooksController {

    private Logger logger = LoggerFactory.getLogger(BooksController.class);

    private BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    private Books oldBook;

    /**
     * Handles requests to list all books.
     */
    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
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
    public String show(@PathVariable Long id, Model model) {
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

    @ModelAttribute(value = "status")
        public List<BookStatus> getStatusList(){
        List<BookStatus> bookStatusList = new ArrayList<>();
        bookStatusList.add(BookStatus.SALE);
        bookStatusList.add(BookStatus.AVAILABLE);
        bookStatusList.add(BookStatus.SOLD);
        bookStatusList.add(BookStatus.OUTOFSTOCK);
        return bookStatusList;
    }


    @RequestMapping( value = "/addBook",method = RequestMethod.POST)
    public String addBooks( @ModelAttribute("books1")Books book, Model model){

        System.out.println(book);
        booksService.save(book);

        logger.info("Populating model with list...");
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);

        return "redirect:/books/list";
    }

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

    @RequestMapping(value = "/edit/editBook",method = RequestMethod.POST)
    public String updateBooks(@ModelAttribute("books") Books book, Model model){

        System.out.println("Updating:"+ oldBook);
        System.out.println("New Book:" + book);
        booksService.updateBookByTitle(oldBook.getId(),book.getTitle(),book.getAuthorfname(),
                book.getAuthorlname(),book.getPrice(),book.getStatus(),book.getGenre(),
                book.getDetailedDescription(),book.getNotes());

        logger.info("Populating model with list...");
        List<Books> books =  booksService.findAll();
        System.out.println(books);
        books.sort(COMPARATOR_BY_ID);

        model.addAttribute("books", books);

        return "redirect:/books/list";
    }


}

