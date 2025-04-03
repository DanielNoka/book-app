package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.entity.BookReview;
import org.springdemo.springproject.service.ReviewService;
import org.springdemo.springproject.util.ApiResponse;
import org.springdemo.springproject.util.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springdemo.springproject.util.Constants.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "This API is accessed only by ADMINS for managing reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Return reviews using pagination")
    public ApiResponse<PaginatedResponse<BookReview>> getAllReviews(
            @PageableDefault(size = 5, sort = "id") Pageable pageable){
        Page<BookReview> reviews = reviewService.findAllReviews(pageable);
        return ApiResponse.map(new PaginatedResponse<>(reviews), OK, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Admins can use this endpoint to delete reviews")
    public ApiResponse<HttpStatus> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ApiResponse.map(null, OK, HttpStatus.OK);
    }

}
