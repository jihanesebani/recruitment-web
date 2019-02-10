package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.time.Period;
import static java.time.temporal.ChronoUnit.DAYS;



public class LibraryImp implements Library {

    private static BookRepository repo=new BookRepository();
    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws  HasLateBooksException,BorrowedBookException {
        Book book=repo.findBook(isbnCode);
        if(!member.canBorrowBooks()) throw new HasLateBooksException("member has late books");
        if(book.getBorrowed()) throw new BorrowedBookException("book already borrowed");
        repo.saveBookBorrow(book,borrowedAt);
        member.getBorrowedIsbnBooks().put(book.getIsbn().getIsbnCode(),borrowedAt);
        return book;
    }

    @Override
    public void returnBook(Book book, Member member) {
        int nbrDays = (int) DAYS.between(repo.findBorrowedBookDate(book),LocalDate.now());
        member.payBook(nbrDays);
        repo.returnBookBorrow(book);
    }
}
