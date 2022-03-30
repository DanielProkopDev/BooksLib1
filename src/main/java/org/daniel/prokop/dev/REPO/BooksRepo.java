package org.daniel.prokop.dev.REPO;

import org.daniel.prokop.dev.DAO.Books;
import org.daniel.prokop.dev.DAO.util.BookStatus;
import org.daniel.prokop.dev.DAO.util.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BooksRepo extends JpaRepository<Books,Long> {

    @Query("select b from Books b where b.id=:id")
    Optional<Books> findBooksById(@Param("id")Long id);

    @Query("select b from Books b where b.title=:tl")
    Optional<Books> findBooksByTitle(@Param("tl")String title);

    @Query("select b from Books b where b.authorfname=:fname")
    List<Books> findBooksByAuthorfname(@Param("fname")String firstname);

    @Query("select b from Books b where b.authorlname=:lname")
    List<Books> findBooksByAuthorlname(@Param("lname")String lastname);

    @Query("select b from Books b where b.price=:pr")
    Optional<Books> findBooksByPrice(@Param("pr")Integer price);

    @Query("select b from Books b where b.price=:pr")
    List<Books> findAllByPriceOrderByTitle(@Param("pr")Integer price);

    @Query("select b from Books b where b.Genre=:genre")
    List<Books> findAllByGenreOrderByTitle(@Param("genre") BookType genre);

    @Modifying
    @Query("update Books  set title =:tl, authorfname=:fname, authorlname=:lname, price=:pr, status=:status, Genre=:genre, detailedDescription=:dtds, notes=:notes  where id=:id")
    void updateBookByTitle(@Param("id")Long id,@Param("tl")String title, @Param("fname") String firstname, @Param("lname") String lastname,
                                      @Param("pr") Integer price, @Param("status")BookStatus status,@Param("genre") BookType genre,
                                      @Param("dtds") String detaileddescription,@Param("notes") String notes);
}
