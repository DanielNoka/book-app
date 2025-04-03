package org.springdemo.springproject.repository;

import org.springdemo.springproject.dto.ReviewResponseDTO;
import org.springdemo.springproject.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("SELECT new org.springdemo.springproject.dto.ReviewResponseDTO(" +
            "r.user.username, r.comment, r.rating) " +
            "FROM BookReview r " +
            "WHERE r.book.id = :bookId")
    List<ReviewResponseDTO> findReviewsByBookId(@Param("bookId") Long bookId);

    @Query("SELECT AVG(r.rating) FROM BookReview r WHERE r.book.id = :bookId")
    Double calculateAverageRating(@Param("bookId") Long bookId);

    boolean existsByUserIdAndBookId(Long userId,Long bookId);

}
