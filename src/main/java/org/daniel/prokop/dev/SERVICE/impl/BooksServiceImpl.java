package org.daniel.prokop.dev.SERVICE.impl;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.REPO.BooksRepo;
import org.daniel.prokop.dev.SERVICE.BooksService;
import org.daniel.prokop.dev.SERVICE.wrappers.BookWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksServiceImpl implements BooksService {

    BooksRepo booksRepo;

    public BooksServiceImpl(BooksRepo booksRepo) {
        this.booksRepo = booksRepo;
    }

    @Override
    public List<Books> findAll() {
        return booksRepo.findAll();
    }

    @Override
    public List<Books> findAllByAuthorfname(String authorfname) {
        return booksRepo.findBooksByAuthorfname(authorfname);
    }

    @Override
    public Books save(Books book) {
        return booksRepo.save(book);
    }

    @Override
    public Optional<Books> findBooksByTitle(String title) {
        return booksRepo.findBooksByTitle(title);
    }

    @Override
    public Optional<Books> findBooksById(Long id) {
        return booksRepo.findBooksById(id);
    }

    @Override
    public BookWrapper findById(Long id) {
        Optional<Books> detectiveOpt = booksRepo.findBooksById(id);
        return detectiveOpt.map(BookWrapper::new).orElseGet(BookWrapper::new);
    }

    @Override
    public List<Books> findBooksByAuthorlname(String authorlname) {
        return booksRepo.findBooksByAuthorlname(authorlname);
    }

    @Override
    public Optional<Books> findBooksByPrice(Integer price) {
        return booksRepo.findBooksByPrice(price);
    }

    @Override
    public List<Books> findAllByPriceOrderByTitle(Integer price) {
        return booksRepo.findAllByPriceOrderByTitle(price);
    }

    @Override
    public List<Books> findAllByGenreOrderByTitle(BookType genre) {
        return booksRepo.findAllByGenreOrderByTitle(genre);
    }

    @Override
    public Books updateAuthorfname(Books book, String newAuthorfname) {
        return booksRepo.save(book);
    }

    @Override
    public Books updateAuthorlname(Books book, String newAuthorlname) {
        return booksRepo.save(book);
    }

    @Override
    public Books updateStatus(Books book, BookStatus newBookStatus) {
        return booksRepo.save(book);
    }

    @Override
    public Books updatePrice(Books book, Integer newPrice) {
        return booksRepo.save(book);
    }

    @Override
    public Books updateGenre(Books book, BookType newBookType) {
        return booksRepo.save(book);
    }

    @Override
    public Integer getCount(Books book) {
        return booksRepo.findBooksById(book.getId()).get().getAmount();
    }

    @Override
    public void delete(Books book) {
         booksRepo.delete(book);
    }

    @Override
    public void updateBookByTitle(Long id, String title, String authorfname, String authorlname,
                                             Integer price, BookStatus status, BookType bookType, String detaileddescription,Integer amount, String notes) {
         booksRepo.updateBookByTitle(id,title,authorfname,authorlname,price,status,bookType,detaileddescription,amount,notes);
    }

    @Override
    public void updateBookAmountById(Integer amount, Long id) {
        booksRepo.updateBookAmountById(amount,id);
    }
}
