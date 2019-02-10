package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class Resident extends Member {


        private static final float COST =10.0f;
        private static final float PINALITE_COST =20.0f;


        public  void payBook(int numberOfDays) {
                if (numberOfDays >60) {
                    this.setWallet(this.getWallet() - (COST  * 60));
                    this.setWallet(this.getWallet() - (PINALITE_COST * (numberOfDays-60)));
                }
                else
                    this.setWallet(this.getWallet() - (COST * numberOfDays));

        }

    @Override
    public Boolean canBorrowBooks() {
        Long nbrBookLate = this.getBorrowedIsbnBooks().entrySet().stream().filter(book ->{
            if(DAYS.between(book.getValue(), LocalDate.now()) > 60)return true;
            return false;
        }).count();
        return nbrBookLate <= 0;
    }
}
