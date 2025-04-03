package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.ReviewDTO;
import org.springdemo.springproject.dto.ReviewResponseDTO;
import org.springdemo.springproject.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(Long bookId, ReviewDTO review);
    List<ReviewResponseDTO> findReviewsByBookId(Long bookId);
    Page<BookReview> findAllReviews(Pageable pageable);
    void deleteReview(Long reviewId);

}
