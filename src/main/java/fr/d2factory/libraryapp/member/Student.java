package fr.d2factory.libraryapp.member;

import static java.time.temporal.ChronoUnit.DAYS;
import java.time.LocalDate;

public class Student extends  Member {

    private Boolean IsFirstYrearStudent=false;

    private static final float COST =10.0f;
    private static final float PINALITE_COST =15.0f;

    public  void payBook(int numberOfDays) {


        if (this.IsFirstYrearStudent == true) {
            if (numberOfDays >15 && numberOfDays <=30 ) {
                this.setWallet(this.getWallet() - (COST * (numberOfDays-15)));

            }
            else if(numberOfDays > 30){
                this.setWallet(this.getWallet() - (PINALITE_COST * (numberOfDays-30)));
                this.setWallet(this.getWallet() - (COST * 15));
            }


        }else{
            if (numberOfDays > 30){
                this.setWallet(this.getWallet() - (PINALITE_COST * (numberOfDays-30)));
                this.setWallet(this.getWallet() - (COST * 30));
            }
            else
                this.setWallet(this.getWallet() - (COST * numberOfDays));
        }

    }

    @Override
    public Boolean canBorrowBooks() {
       Long nbrBookLate = this.getBorrowedIsbnBooks().entrySet().stream().filter(book ->{
            if(this.IsFirstYrearStudent)
                if(DAYS.between(book.getValue(), LocalDate.now()) > 45)return true;
             else
                if(DAYS.between(book.getValue(), LocalDate.now()) > 30)return true;
            return false;
        }).count();
       return nbrBookLate <= 0;
    }


    public Boolean getFirstYrearStudent() {
        return IsFirstYrearStudent;
    }

    public void setFirstYrearStudent(Boolean firstYrearStudent) {
        IsFirstYrearStudent = firstYrearStudent;
    }
}
