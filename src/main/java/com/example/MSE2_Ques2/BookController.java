package com.example.MSE2_Ques2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> bookList = new ArrayList<>();

    // (b) Add a new book using @PostMapping and @RequestBody
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookList.add(new Book(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getAvailableCopies()
        ));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Book added successfully!");
    }

    // (c) Retrieve all books using @GetMapping
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookList);
    }

    // (c) Retrieve a specific book by its ID using @GetMapping
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Integer id) {
        for (Book book : bookList) {
            if (book.getId() != null && book.getId().equals(id)) {
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book with ID " + id + " not found!");
    }

    // (d) Update a book's details using @PutMapping
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        for (Book book : bookList) {
            if (book.getId() != null && book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailableCopies(updatedBook.getAvailableCopies());
                return ResponseEntity.ok("Book with ID " + id + " updated successfully!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book with ID " + id + " not found!");
    }

    // (e) Delete a book by its ID using @DeleteMapping with proper HTTP responses
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        boolean removed = bookList.removeIf(book -> book.getId() != null && book.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Book with ID " + id + " deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book with ID " + id + " not found!");
    }
}
