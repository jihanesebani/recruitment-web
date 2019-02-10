package fr.d2factory.libraryapp.library;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LibraryTest {
    private static Library library ;
    private static BookRepository bookRepository;
    private static  Resident residentWithLateBook;




    @BeforeClass
    public static void setup() {

        residentWithLateBook=new Resident();

        library = new LibraryImp();
        bookRepository = new BookRepository();
        List<Book> books = null;
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("books.json");
        try {
            books = mapper.readValue(is, new TypeReference<List<Book>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookRepository.addBooks(books);
    }

    @Test
    public void a_member_can_borrow_a_book_if_book_is_available()throws HasLateBooksException, BorrowedBookException
    {
       Student student1 = new Student();
        student1.setWallet(200);
        student1.setFirstYrearStudent(true);
        //Book b=  library.borrowBook(46578964513L,student1,LocalDate.now());
        Book b=  library.borrowBook(46578964513L,student1,LocalDate.of(2019 ,01,01));
        assertNotNull(b);


    }

    @Test(expected = BorrowedBookException.class)
    public void b_borrowed_book_is_no_longer_available(){
        Student student = new Student();
        student.setWallet(200);
        student.setFirstYrearStudent(true);
        Book b=  library.borrowBook(46578964513L,student,LocalDate.now());

    }

    @Test
    public void c_residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Resident resident=new Resident();
        resident.setWallet(200);
        Book b= library.borrowBook(3326456467846L,resident,LocalDate.now().minusDays(10));
        library.returnBook(b,resident);
        assertEquals((long)resident.getWallet() ,100);
    }

    @Test
    public void d_students_pay_10_cents_the_first_30days(){
        Student student=new Student();
        student.setWallet(1000);
        student.setFirstYrearStudent(true);
        Book b= library.borrowBook(3326456467846L,student,LocalDate.now().minusDays(30));
        library.returnBook(b,student);
        assertEquals((long)student.getWallet(),700 );
    }

   @Test
   public void e_students_in_1st_year_are_not_taxed_for_the_first_15days(){
       Student student=new Student();
       student.setWallet(1000);
       student.setFirstYrearStudent(true);
       Book b= library.borrowBook(465789453199L,student,LocalDate.now().minusDays(15));
       library.returnBook(b,student);
       assertEquals((long)student.getWallet(),1000);
    }

    @Test
   public void f_students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days(){
        Student student=new Student();
        student.setWallet(1000);
        student.setFirstYrearStudent(false);
        Book b= library.borrowBook(465789453333L,student,LocalDate.now().minusDays(40));
        library.returnBook(b,student);
        assertEquals((long)student.getWallet(),550 );
    }

   @Test
   public void g_residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
       residentWithLateBook.setWallet(1000);
       Book b= library.borrowBook(465789453149L,residentWithLateBook,LocalDate.now().minusDays(70));
       library.returnBook(b,residentWithLateBook);
       assertEquals((long)residentWithLateBook.getWallet(),200);
    }

  @Test(expected = HasLateBooksException.class)
  public void members_cannot_borrow_book_if_they_have_late_books(){
      residentWithLateBook.setWallet(1000);
      Book b= library.borrowBook(465789459999L,residentWithLateBook,LocalDate.now().minusDays(70));

  }







 }