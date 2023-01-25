package org.daniel.prokop.dev.SERVICE;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.daniel.prokop.dev.SERVICE.wrappers.BookWrapper;

import java.util.List;
import java.util.Optional;

public interface BooksService {
    List<Books> findAll();

    List<Books> findAllByAuthorfname(String authorfname);

    Books save(Books book);

    Optional<Books> findBooksByTitle(String title);

    Optional<Books> findBooksById(Long id);

    BookWrapper findById(Long id);

    List<Books> findBooksByAuthorlname(String authorlname);

    Optional<Books> findBooksByPrice(Integer price);

    List<Books> findAllByPriceOrderByTitle(Integer price);

    List<Books> findAllByGenreOrderByTitle(BookType genre);

    Books updateAuthorfname(Books book, String newAuthorfname);

    Books updateAuthorlname(Books book, String newAuthorlname);

    Books updateStatus(Books book, BookStatus newBookStatus);

    Books updatePrice(Books book, Integer newPrice);

    Books updateGenre(Books book, BookType newBookType);

    Integer getCount(Books book);

    void delete(Books book);

    void updateBookByTitle(Long id,String title,String authorfname,String authorlname,
                                      Integer price,BookStatus status,BookType bookType,String detaileddescription,Integer amount,String notes);
}
