package F28PAAssignment2026.src;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library(List<Book> books, List<Member> members){
        this.books = books;
        this.members = members;
    }

    public Book findBook(String callNumber){
        for (Book book : books){
            if (book.getBookCode().equalsIgnoreCase(callNumber.trim())) {
                return book;
            }
        }
        return null;
    }

    public Member findMember(String memberID){
        for (Member member : members){
            if (member.getMemberID().equals(memberID)){
                return member;
            }
        }
        return null;
    }

    public List<Book> getBorrowedBooks(Member member){
        List<Book> borrowedBooks = new ArrayList<>();

        for (String bookCode : member.getBorrowedBook()){
            Book book = findBook(bookCode);

            if (book != null) {
                borrowedBooks.add(book);
            } else {
                System.out.println("Book not found: " + bookCode);
            }
        }
        return borrowedBooks;
    }

    public List<Book> getBooks(){
        return books;
    }

    public List<Member> getMembers(){
        return members;
    }

}