package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //JPA Query
    @Query(value = "SELECT a FROM Author a WHERE LOWER(a.nationality) LIKE LOWER(CONCAT('%', :nationality, '%'))")
    List<Author> findByNationality(@Param("nationality") String nationality);

}
