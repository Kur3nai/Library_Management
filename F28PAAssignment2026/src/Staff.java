package F28PAAssignment2026.src;

import java.util.List;

public class Staff extends Member{
    private int renewalCount;

    Staff(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }

    @Override
    public void borrowedBook() {
        super.borrowedBook();
    }

    @Override
    public void returnBook(String bookID) {
        super.returnBook(bookID);
    }

    @Override
    public void renewBook() {
        super.renewBook();
    }

    public int calculateFine(){
       int fine = 0;
       fine += 1;
        return fine;
    }
}
