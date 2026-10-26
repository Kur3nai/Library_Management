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

    public String getMemberID() {
        return memberID;
    }
    public String getMemberName(){
        return memberName;
    }
    public String getMemberType(){
        return "";
    }
    public int getNumberOfBooksBorrowed() {
        return borrowedBook.size();
    }
    public List<String> getBorrowedBook(){
        return borrowedBook;
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

    public String toFileString() {
        StringBuilder result = new StringBuilder();

        result.append(memberID).append(", ")
                .append(memberName).append(", ")
                .append(getMemberType()).append(", ")
                .append(borrowedBook.size());

        for (String bookCode : borrowedBook) {
            result.append(", ").append(bookCode);
        }

        return result.toString();
    }

    @Override
    public String toString(){
        return "MemberID: " + memberID +
                "\nMember Name: " + memberName;
    }
}