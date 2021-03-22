package edu.cscc.designpatterns.singleton;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookTests {

    UUID uuid = UUID.randomUUID();
    Book book = new Book(uuid, "name", "author", 10);

    @Test
    public void itHasAnIdTest() {
        assertEquals(uuid, book.getId());
    }

    @Test
    public void equalsWorksCorrectlyTest() {
        assertTrue(book.equals(book));
        assertFalse(book.equals(null));
        assertFalse(book.equals(new String()));
        Book testBook = new Book(uuid, "another name", "another author", 11);
        assertTrue(book.equals(testBook));
    }

    @Test
    public void isHashesCorrectly() {
        assertEquals(book.hashCode(), Objects.hash(uuid));

    }
}
