package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE YEAR(b.publishYear) = :year")
    List<Book> findByYear(@Param("year") Integer year);

    Page<Book> findAll(Pageable pageable);

}

