📚 Book App
This project is a Spring Boot application that provides a RESTful API for managing books, authors, categories, and user authentication. It includes essential features such as:

- CRUD operations for books, authors, and categories.
  
- Review system where users can review a book once, and view the overall rating when returning a book.

- JWT authentication and role-based security (USER can read, update, and post; ADMIN can also delete).
  
- Profile update allowing users to modify their details using their JWT for authentication.

- Pagination, filtering, and sorting for efficient data retrieval.
  
- Password reset with OTP: Users can request a password reset by providing their email, with OTP sent via Gmail using Redis for optimal performance.

- Swagger UI for easy API documentation access.

- Mail service for email notifications (e.g., new book registrations).

- Logging system that stores logs and errors in the database for better monitoring and debugging.
  
- Environment variables for securing sensitive configurations such as email service credentials.

This project provides a scalable, secure, and efficient backend foundation, designed for real-world applications.
