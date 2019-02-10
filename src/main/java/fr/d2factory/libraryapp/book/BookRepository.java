package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private static Map<Long, Book> availableBooks = new HashMap<>();
    private static Map<Long,LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
        books.forEach(book->availableBooks.put(new Long(book.getIsbn().getIsbnCode()),book));
    }

    public Book findBook(long isbnCode) {
       return  availableBooks.entrySet().stream().filter(book-> isbnCode == book.getValue().getIsbn().getIsbnCode()).map(book->book.getValue()).findFirst().get();
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        Book b=this.findBook(book.getIsbn().getIsbnCode());
        b.setBorrowed(true);
        availableBooks.put(b.getIsbn().getIsbnCode(),b);
        borrowedBooks.put(book.getIsbn().getIsbnCode(),borrowedAt);

    }

    public LocalDate findBorrowedBookDate(Book book) {
       return borrowedBooks.get(book.getIsbn().getIsbnCode());
    }


    public void returnBookBorrow(Book book){
        Book b=this.findBook(book.getIsbn().getIsbnCode());
        b.setBorrowed(false);
        borrowedBooks.remove(book.getIsbn().getIsbnCode());

    }


}

