import axios from 'axios';

const BASE_URL = 'http://localhost:8080/book'; // Endpointi i backendit

class BookService {
  getAllBooks() {
    return axios.get(`${BASE_URL}/all`);
  }

  createBook(book) {
    return axios.post(BASE_URL, book);
  }

  getBookById(id) {
    return axios.get(`${BASE_URL}/${id}`);
  }

  updateBook(id, book) {
    return axios.put(`${BASE_URL}/${id}`, book);
  }

  deleteBook(id) {
    return axios.delete(`${BASE_URL}/${id}`);
  }
}

export default new BookService();
