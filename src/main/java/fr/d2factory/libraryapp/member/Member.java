package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
    private float wallet;

    private Map<Long, LocalDate> borrowedIsbnBooks = new HashMap<>();
    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public Map<Long, LocalDate> getBorrowedIsbnBooks() {
        return borrowedIsbnBooks;
    }

    public void setBorrowedIsbnBooks(Map<Long, LocalDate> borrowedIsbnBooks) {
        this.borrowedIsbnBooks = borrowedIsbnBooks;
    }

    public abstract Boolean canBorrowBooks();
}
