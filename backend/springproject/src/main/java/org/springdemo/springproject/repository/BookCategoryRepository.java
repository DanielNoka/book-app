package org.springdemo.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springdemo.springproject.entity.BookCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory,Long> {
    boolean existsByBookIdAndCategoryId(Long bookId, Long categoryId);
}
