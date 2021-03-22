package edu.cscc.designpatterns.singleton;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryTest {

    @Test
    void itIsASingletonTest() {
        Library instance1 = Library.getInstance();
        Library instance2 = Library.getInstance();
        assertEquals(instance1, instance2);
        Constructor[] allConstructors = Library.class.getDeclaredConstructors();
        assertEquals(1, allConstructors.length);
        assertEquals("private edu.cscc.designpatterns.singleton.Library()", allConstructors[0].toString());
    }

    @Test
    void itAddsBooksCorrectlyTest() {
        Library library = Library.getInstance();
        assertEquals(0,library.getBooks().size());
        Book book1 = new Book("Spring Web Essentials", "Jeffrey Allen Anderson", 15);
        Book book2 = new Book("Clean Code", "Robert C. Martin", 20);
        library.addBooks(Arrays.asList(book1));
        assertEquals(1,library.getBooks().size());
        library.addBooks(Arrays.asList(book2));
        assertEquals(2,library.getBooks().size());
        assertEquals(1,
                library.getBooks().stream().filter(book -> book.getName().equals(book1.getName())).count());
        assertEquals(1,
                library.getBooks().stream().filter(book -> book.getName().equals(book2.getName())).count());
    }
}
