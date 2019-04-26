package com.example.demo.web;

import com.example.demo.persistence.Book;
import com.example.demo.web.exceptions.BookIdMismatchException;
import com.example.demo.web.exceptions.BookNotFoundException;
import com.example.demo.persistence.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public Book findOne(@PathVariable Long id) {
		return bookRepository.findById(id)
				.orElseThrow(BookNotFoundException::new);
	}

	@GetMapping("/title/{bookTitle}")
	public List findByTitle(@PathVariable String bookTitle) {
		return bookRepository.findByTitle(bookTitle);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		bookRepository.findById(id)
				.orElseThrow(BookNotFoundException::new);
		bookRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book.getId() != id) {
			throw new BookIdMismatchException();
		}
		bookRepository.findById(id)
				.orElseThrow(BookNotFoundException::new);
		return bookRepository.save(book);
	}
}
