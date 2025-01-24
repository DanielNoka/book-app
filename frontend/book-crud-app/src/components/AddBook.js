import React, { useState } from 'react';
import BookService from '../services/BookService';

const AddBook = ({ onBookAdded }) => {
  const [book, setBook] = useState({ title: '', author: '', publishYear: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook({ ...book, [name]: value });
  };

  const handleSubmit = (e) => {
   // e.preventDefault();
    BookService.createBook(book)
      .then(() => {
        onBookAdded();
        alert("Libri u shtua me sukses")
        setBook({ title: '', author: '', publishYear: '' });
      })
      .catch((error) => console.error(error));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Book</h2>
      <input type="text" name="title" placeholder="Title" value={book.title} onChange={handleChange} required />
      <input type="text" name="author" placeholder="Author" value={book.author} onChange={handleChange} required />
      <input type="date" name="publishYear" placeholder="Publish Year" value={book.publishYear} onChange={handleChange} required />
      <button type="submit">Add</button>
    </form>
  );
};

export default AddBook;
