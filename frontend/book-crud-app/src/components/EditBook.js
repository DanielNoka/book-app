import React, { useState } from 'react';
import BookService from '../services/BookService';

const EditBook = ({ currentBook, onBookUpdated }) => {
  const [book, setBook] = useState(currentBook);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook({ ...book, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    BookService.updateBook(book.id, book)
      .then(() => onBookUpdated())
      .catch((error) => console.error(error));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Edit Book</h2>
      <input type="text" name="title" value={book.title} onChange={handleChange} required />
      <input type="text" name="author" value={book.author} onChange={handleChange} required />
      <input type="date" name="publishYear" value={book.publishYear} onChange={handleChange} required />
      <button type="submit">Update</button>
    </form>
  );
};

export default EditBook;
