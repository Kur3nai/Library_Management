package F28PAAssignment2026;
import F28PAAssignment2026.src.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static F28PAAssignment2026.src.FileUtil.*;

public class Tester {
    private static final Scanner user_in = new Scanner(System.in);
    private static Library library;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws IOException {
        List<Member> members = readMembers("F28PAAssignment2026/src/data/member.txt");
        List<Book> books = readBooks("F28PAAssignment2026/src/data/book.txt");
        library = new Library(books, members);
        String member_id;
        int user_choice_1;
        int user_choice_2;
        boolean status = false;
        boolean user_status = false;
        BufferedReader br = new BufferedReader(new FileReader("F28PAAssignment2026/src/data/data.txt"));
        String library_name = br.readLine();

        while (!status) {
            main_menu(library_name);
            String selection = user_in.nextLine();
            try{
                user_choice_1 = Integer.parseInt(selection);
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Please Enter a Number.");
                continue;
            }
            switch (user_choice_1) {
                case 1 -> {
                    for (Member member : members) {
                        System.out.println(member);
                    }
                }
                case 2 -> {
                    for (Book book : books) {
                        System.out.println(book);
                    }
                }
                case 3 -> addBook();
                case 4 -> removeBook();
                case 5 -> addMember();
                case 6 -> removeMember();
                case 7 -> searchBook();
                case 8 -> {
                    System.out.println("Enter a Member ID: ");
                    member_id = user_in.next();
                    boolean found = false;
                    for (Member member : members) {
                        if (member.getMemberID().equals(member_id)) {
                            found = true;
                            user_status = false;
                            while (!user_status) {
                                user_menu(library_name, member.getMemberName());
                                selection = user_in.nextLine();
                                try{
                                    user_choice_2 = Integer.parseInt(selection);
                                } catch (NumberFormatException e){
                                    System.out.println("Invalid Input. Please Enter a Number.");
                                    continue;
                                }
                                user_in.nextLine();
                                switch (user_choice_2) {
                                    case 1 -> {
                                        System.out.println("Please Enter Book Code: ");
                                        String book_code = user_in.nextLine();
                                        boolean book_found = false;

                                        for (Book book : books){
                                            if (book.getBookCode().equals(book_code)){
                                                book_found = true;

                                                if(!book.isAvailable()){
                                                    System.out.println("This book is not available for borrowing.");
                                                    break;
                                                }

                                                if (member.getNumberOfBooksBorrowed() >= 2){
                                                    System.out.println("Cannot Borrow More than 2 Books at a time.");
                                                    break;
                                                }

                                                String dueDate = LocalDate.now().plusDays(30).format(FORMAT);
                                                book.borrowBook(dueDate);
                                                member.addBook(book_code);
                                                break;
                                            }
                                        }
                                        if (!book_found) {
                                            System.out.println("Book not found.");
                                        }
                                    }
                                    case 2 -> {
                                        System.out.println("Please Enter Book Code: ");
                                        String book_code = user_in.nextLine();
                                        boolean book_found = false;

                                        for (Book book : books){
                                            if (book.getBookCode().equals(book_code)){
                                                book_found = true;
                                                book.returnBook();
                                                member.returnBook(book_code);
                                                break;
                                            }
                                        }

                                        if (!book_found) {
                                            System.out.println("Book not found.");
                                        }
                                    }
                                    case 3 -> {
                                        System.out.println("Please Enter Book Code: ");
                                        String book_code = user_in.nextLine();
                                        boolean book_found = false;

                                        for (Book book : books) {
                                            if (book.getBookCode().equals(book_code)){
                                                book_found = true;

                                                if (!member.getBorrowedBook().contains(book_code)){
                                                    System.out.println("You have not borrowed this book.");
                                                    break;
                                                }

                                                if (!book.isLoaned()){
                                                    System.out.println("This book is not currently borrowed");
                                                    break;
                                                }

                                                int renewal_limit;

                                                if (member instanceof Student){
                                                    renewal_limit = 1;
                                                } else if (member instanceof Staff){
                                                    renewal_limit = 2;
                                                } else if (member instanceof Lecturer){
                                                    renewal_limit = Integer.MAX_VALUE;
                                                } else {
                                                    renewal_limit =0;
                                                }

                                                if (book.getRenewalCount() >= renewal_limit) {
                                                    System.out.println("You have Reached the Renewal Limit.");
                                                    break;
                                                }

                                                try {
                                                    LocalDate parsedDate = LocalDate.parse(
                                                            book.getDueDate(), FORMAT
                                                    );

                                                    String new_due_date = parsedDate
                                                            .plusDays(30)
                                                            .format(FORMAT);

                                                    book.renewBook(new_due_date);

                                                } catch (java.time.format.DateTimeParseException e) {
                                                    System.out.println(
                                                            "Error: Invalid date format. Cannot renew book."
                                                    );
                                                }
                                                break;
                                            }
                                        }
                                        if (!book_found) {
                                            System.out.println("Book not found.");
                                        }
                                    }
                                    case 4 -> {
                                        System.out.println(member);
                                    }
                                    case 5 -> {
                                        for (String book_code : member.getBorrowedBook()){
                                            for (Book book : library.getBooks()){
                                                if (book.getBookCode().equals(book_code)){
                                                    System.out.println(book);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    case 0 -> user_status = true;
                                }
                            }
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("User Does not exist.");
                    }
                }
                case 0 -> {
                    status = true;
                }
            }
        }
        FileUtil.saveBooks("F28PAAssignment2026/src/data/book.txt", books);
        FileUtil.saveMembers("F28PAAssignment2026/src/data/member.txt",members);
        System.out.println("Library System Shutting Down");
    }

    static void banner(String library_name) {
        System.out.printf("\n===========================%s MANAGEMENT SYSTEM===========================\n", library_name);
    }

    static void banner(String library_name, String user_ID) {
        System.out.printf("\n===========================%s MANAGEMENT SYSTEM===========================\n", library_name);
        System.out.println("Current User: " + user_ID);
    }

    static void user_menu(String library_name, String user_ID) {
        banner(library_name, user_ID);
        System.out.println("1. Borrow a Book");
        System.out.println("2. Return a Book");
        System.out.println("3. Renew Book");
        System.out.println("4. Check Member Details");
        System.out.println("5. Check Borrow Books");
        System.out.println("0. Exit");
        System.out.println("Enter Choice: ");
    }

    static void main_menu(String library_name) {
        banner(library_name);
        displayReminders();
        System.out.println("==================================================================================");
        System.out.println("1. Display All Members");
        System.out.println("2. Display All Books");
        System.out.println("3. Add a Book");
        System.out.println("4. Remove a Book");
        System.out.println("5. Add a Member");
        System.out.println("6. Remove a Member");
        System.out.println("7. Search Books");
        System.out.println("8. Select User");
        System.out.println("0. Exit");
        System.out.println("Enter Choice: ");
    }

    private static void displayReminders(){
        LocalDate today = LocalDate.now();
        boolean found = false;

        for (Book book : library.getBooks()){
            String reminder = book.reminderMsg(today);
            if (reminder != null){
                System.out.println(reminder);
                found=true;
            }
        }

        if (!found) {
            System.out.println("No reminders for today or next week.");
        }
    }

    private static void addBook() {
        System.out.println("Book type (N/R): ");
        String book_type = user_in.nextLine().trim();

        System.out.println("Book Title: ");
        String title = user_in.nextLine().trim();

        System.out.println("Number of Authors: ");
        int count = Integer.parseInt(user_in.nextLine().trim());

        List<String> authors = new ArrayList<>();

        for (int i = 1; i <= count; i++){
            System.out.printf("\nAuthor %d : ", i);
            authors.add(user_in.nextLine().trim());
        }
        System.out.println("Published Year: ");
        int published_year = Integer.parseInt(user_in.nextLine().trim());

        System.out.println("Publisher : ");
        String publisher = user_in.nextLine().trim();

        System.out.println("Book Code: ");
        String book_code = user_in.nextLine().trim();

        if (library.findBook(book_code) != null) {
            System.out.println("A Book with that call number already exists.");
            return;
        }

        Book book = new Book(
                book_type, title, count, authors, published_year, publisher, book_code, false,null
        );

        library.getBooks().add(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.println("Enter a Book Code: ");
        String book_code = user_in.nextLine().trim();

        Book book = library.findBook(book_code);

        if (book == null){
            System.out.println("Book Not Found In Library.");
        } else if (book.isLoaned()){
            System.out.println("Cannot remove a borrowed book.");
        } else {
            library.getBooks().remove(book);
            System.out.println("Book removed successfully.");
        }

    }

    private static void addMember() {
        System.out.println("Member ID: ");
        String member_id = user_in.nextLine().trim();

        if (library.findMember(member_id) != null){
            System.out.println("Member ID already exist");
            return;
        }

        System.out.println("Member Name: ");
        String member_name = user_in.nextLine().trim();

        System.out.println("Member type (S = Staff, L = Lecturer, s = Students): ");
        String member_type = user_in.nextLine().trim();

        List<String> books_borrowed = new ArrayList<>();
        Member member;

        switch (member_type){
            case "S" -> {
                member = new Staff(member_id,member_name, books_borrowed);
                break;
            }
            case "L" -> {
                member = new Lecturer(member_id, member_name, books_borrowed);
                break;
            }
            case "s" -> {
                member = new Student(member_id, member_name, books_borrowed);
                break;
            }
            default -> {
                System.out.println("Invalid member type.");
                return;
            }
        }

        library.getMembers().add(member);
        System.out.println("Member Added Successfully.");
    }

    private static void removeMember() {
        System.out.println("Enter Member ID that you wish to delete: ");
        String member_id = user_in.nextLine().trim();

        Member member = library.findMember(member_id);

        if (member == null){
            System.out.println("Member not found.");
        } else if (!member.getBorrowedBook().isEmpty()){
            System.out.println("Cannot remove a member who has borrowed books.");
        } else {
            library.getMembers().remove(member);
            System.out.println("Member Removed Successfully.");
        }
    }

    private static void searchBook() {
        System.out.println("\n==================================Filter Search==================================");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publication Year Range");
        System.out.println("4. Exit");
        System.out.println("Enter Choice: ");
        int choice = user_in.nextInt();
        boolean found = false;
        user_in.nextLine();

        switch (choice){
            case 1 -> {
                System.out.println("Enter title or keyword: ");
                String book_title = user_in.nextLine().toLowerCase().trim();

                for (Book book : library.getBooks()){
                    if (book.getTitle().toLowerCase().contains(book_title)){
                        System.out.println(book);
                        found = true;
                    }
                }
                break;
            }
            case 2 -> {
                System.out.println("Enter Author: ");
                String book_author = user_in.nextLine().toLowerCase().trim();

                for (Book book : library.getBooks()){
                    for (String name : book.getAuthors()){
                        if (name.toLowerCase().contains(book_author)){
                            System.out.println(book);
                            found = true;
                            break;
                        }
                    }
                }
                break;
            }
            case 3 -> {
                System.out.println("Enter Starting Year (format - yyyy): ");
                int starting_year = Integer.parseInt(user_in.nextLine().trim());
                System.out.println("Enter Ending Year (format - yyyy): ");
                int ending_year = Integer.parseInt(user_in.nextLine().trim());

                if (starting_year > ending_year){
                    System.out.println("Invalid Year Range.");
                    return;
                }

                for(Book book : library.getBooks()){
                    int year = book.getPublishedYear();

                    if (year >= starting_year && year <= ending_year) {
                        System.out.println(book);
                        found = true;
                    }
                }
                break;
            }
            case 4 -> {
                break;
            }
            default -> {
                System.out.println("Invalid Search Choice.");
                return;
            }
        }
        if (!found){
            System.out.println("No Matching books found");
        }
    }

}