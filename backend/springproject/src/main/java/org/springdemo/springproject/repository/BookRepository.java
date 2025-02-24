package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
    SELECT a 
    FROM Author a 
    JOIN a.bookAuthors ba 
    WHERE ba.book.id = :bookId
""")
    List<Author> findAuthorsByBookId(@Param("bookId") Long bookId);


}

