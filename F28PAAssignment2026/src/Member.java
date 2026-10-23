package F28PAAssignment2026.src;

import java.util.List;

public class Member {
    private String memberID;
    private String memberName;
    private int numberOfBooksBorrowed;
    private List<String> borrowedBook;

    Member(String memberID, String memberName, List<String> borrowedBook){
        this.memberID = memberID;
        this.memberName = memberName;
        this.numberOfBooksBorrowed = 0;
        this.borrowedBook = borrowedBook;
    }

    public int getNumberOfBooksBorrowed() {
        return borrowedBook.size();
    }

    public void addBook(String bookID) {
        if (borrowedBook.size() < 2) {
            borrowedBook.add(bookID);
            numberOfBooksBorrowed = borrowedBook.size();
            System.out.println(bookID + " added successfully.");
        } else {
            System.out.println("Cannot borrow more than 2 books at a time.");
        }
    }

    public void returnBook(String bookID) {
        if (borrowedBook.contains(bookID)) {
            borrowedBook.remove(bookID);
            numberOfBooksBorrowed = borrowedBook.size();
            System.out.println(bookID + " returned successfully.");
        } else {
            System.out.println("Book not found in borrowed list.");
        }
    }

    public void borrowedBook() {

    }

    public void renewBook(){

    }
}