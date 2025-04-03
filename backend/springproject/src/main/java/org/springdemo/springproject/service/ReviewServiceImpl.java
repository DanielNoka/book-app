package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.ReviewDTO;
import org.springdemo.springproject.dto.ReviewResponseDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.entity.BookReview;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.BookRepository;
import org.springdemo.springproject.repository.ReviewRepository;
import org.springdemo.springproject.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ReviewResponseDTO addReview(Long bookId, ReviewDTO reviewDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        // Check if user has alredy done a review
        if (reviewRepository.existsByUserIdAndBookId(user.getId(), bookId)) {
            throw new EntityNotFoundException("User has already reviewed this book.");
        }

        BookReview review = modelMapper.map(reviewDTO, BookReview.class);
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);

        //to update reviews in Book table after saving them
        updateBookAverageRating(book);

        return new ReviewResponseDTO(username, reviewDTO.getComment(),  reviewDTO.getRating());
    }

    @Override
    public List<ReviewResponseDTO> findReviewsByBookId(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException("Book not found");
        }
        return reviewRepository.findReviewsByBookId(bookId);
    }

    @Override
    public Page<BookReview> findAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public void deleteReview(Long reviewId) {
        BookReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewRepository.delete(review);
        log.info("Review with id {} has been deleted.", reviewId);
    }

    private void updateBookAverageRating(Book book) {
        Double avgRating = reviewRepository.calculateAverageRating(book.getId());
        // Apply rounding
        Double roundedRating = (avgRating == null) ? null :
                BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.DOWN).doubleValue();

        book.setOverallRating(roundedRating);
        bookRepository.save(book);
    }

}
