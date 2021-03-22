package edu.cscc.designpatterns.exercise;

import edu.cscc.designpatterns.singleton.Book;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryImplTest {

    Book book1 = new Book("Spring Web Essentials", "Jeffrey Allen Anderson", 15);
    Book book2 = new Book("Clean Code", "Robert C. Martin", 20);

    @Test
    void itStartsOutAsAnEmptyListTest() {
        BookRepository bookRepository = new BookRepositoryImpl();
        assertTrue(bookRepository.readAll().isEmpty());
    }

    @Test
    public void itAddsBookCorrectlyTest() {
        BookRepository bookRepository = new BookRepositoryImpl();
        assertTrue(bookRepository.readAll().isEmpty());
        Book savedBook = bookRepository.create(book1);
        assertFalse(bookRepository.readAll().isEmpty());
        assertNotNull(savedBook);
        assertNull(book1.getId());
        assertNotNull(savedBook.getId());
    }

    @Test
    public void itRetrievesBooksByIdTest() {
        BookRepository bookRepository = new BookRepositoryImpl();
        assertNull(bookRepository.read(UUID.randomUUID()));
        Book savedBook = bookRepository.create(book1);
        Book retrievedBook = bookRepository.read(savedBook.getId());
        assertNotNull(retrievedBook);
    }

    @Test
    public void itUpdatesBooksCorrectlyTest() {
        String newAuthor = "Homer Simpson";
        BookRepository bookRepository = new BookRepositoryImpl();
        assertTrue(bookRepository.readAll().isEmpty());
        Book savedBook = bookRepository.create(book1);
        savedBook.setAuthor(newAuthor);
        Book updatedBook = bookRepository.update(savedBook);
        assertEquals(newAuthor, updatedBook.getAuthor());
        Book updatedBook2 = bookRepository.update(book1);
        assertNull(updatedBook2);
    }

    @Test
    public void itActuallyDeletesThings() {
        BookRepository bookRepository = new BookRepositoryImpl();
        assertTrue(bookRepository.readAll().isEmpty());
        Book savedBook = bookRepository.create(book1);
        assertFalse(bookRepository.readAll().isEmpty());
        Book deletedBook = bookRepository.delete(savedBook);
        assertTrue(bookRepository.readAll().isEmpty());
    }
}