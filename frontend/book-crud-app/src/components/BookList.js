import React, { useEffect, useState } from 'react';
import BookService from '../services/BookService';

const BookList = ({ onEdit }) => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    BookService.getAllBooks()
      .then((response) => setBooks(response.data))
      .catch((error) => console.error(error));
  }, []);

  const handleDelete = (id) => {
    BookService.deleteBook(id)
      .then(() => setBooks(books.filter((book) => book.id !== id)))
      .catch((error) => console.error(error));
  };

  return (
    <div>
      <h1>Book Management App</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Publish Year</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.map((book) => (
            <tr key={book.id}>
              <td>{book.id}</td>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.publishYear}</td>
              <td>
                <button onClick={() => onEdit(book)}>Edit</button>
                <button onClick={() => handleDelete(book.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookList;
