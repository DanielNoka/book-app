package org.springdemo.springproject.mapper;

import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublishYear(),
                book.getBookAuthors() != null
                        ? book.getBookAuthors().stream()
                        .map(bookAuthor -> bookAuthor.getAuthor().getId())
                        .collect(Collectors.toList())
                        : List.of() // Handle null case
        );
    }

    public Book toEntity(CreateBookDTO bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setPublishYear(bookDto.getPublishYear());
        return book;
    }
}
