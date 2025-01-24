import React, { useState } from 'react';
import './App.css';
import BookList from './components/BookList';
import AddBook from './components/AddBook';
import EditBook from './components/EditBook';

const App = () => {
  const [editing, setEditing] = useState(false);
  const [currentBook, setCurrentBook] = useState(null);

  const handleEdit = (book) => {
    setEditing(true);
    setCurrentBook(book);
  };

  const handleBookAdded = () => setEditing(false);

  return (
    <div>
      {editing ? (
        <EditBook currentBook={currentBook} onBookUpdated={handleBookAdded} />
      ) : (
        <>
          <AddBook onBookAdded={handleBookAdded} />
          <BookList onEdit={handleEdit} />
        </>
      )}
    </div>
  );
};

export default App;
