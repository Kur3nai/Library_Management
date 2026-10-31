package F28PAAssignment2026_ABZ4005;
import F28PAAssignment2026_ABZ4005.src.*;

// Adam Zikri Bin Zaini
// ABZ4005

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static F28PAAssignment2026_ABZ4005.src.FileUtil.*;

public class Tester {
    private static final Scanner user_in = new Scanner(System.in);
    private static Library library;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String MEMBER_FILE = "F28PAAssignment2026_ABZ4005/src/data/member.txt";
    private static final String BOOK_FILE = "F28PAAssignment2026_ABZ4005/src/data/book.txt";
    private static final String DATA_FILE = "F28PAAssignment2026_ABZ4005/src/data/data.txt";
    private static final String template = "===================================================";

    public static void main(String[] args) throws IOException {
        String library_name;

        try{
            List<Member> members = readMembers(MEMBER_FILE);
            List<Book> books = readBooks(BOOK_FILE);
            library = new Library(books, members);

            try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))){
                library_name = br.readLine();
                if(library_name == null || library_name.isBlank()){
                    library_name = "Library";
                }
            }
        } catch (IOException e){
            System.out.println("Could not load library data: " + e.getMessage());
            return;
        } catch (RuntimeException e){
            System.out.println("Data files are formatted differently: " + e.getMessage());
            return;
        }

        boolean status = false;

        while (!status) {
            try{
                main_menu(library_name);
                int choice = verifyInput(null);

                switch(choice){
                    case 1 -> {
                        System.out.println();
                        System.out.println("Members Lists: ");
                        int number = 1;
                        for (Member member : library.getMembers()){
                            System.out.println(number+ ". " + member.getMemberName() + " - " + member.getMemberType() + " - " + member.getMemberID());
                            number++;
                        }
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Book Lists: ");
                        int number = 1;
                        for (Book book : library.getBooks()){
                            System.out.println(number+ ". " + book.getTitle() + " - " + book.getBookCode() + " - " + book.getPublishedYear());
                            number++;
                        }
                    }
                    case 3 -> addBook();
                    case 4 -> removeBook();
                    case 5 -> addMember();
                    case 6 -> removeMember();
                    case 7 -> searchBook();
                    case 8 -> selectUser(library_name);
                    case 9 -> checkAvailableCopies();
                    case 0 -> status = true;
                    default -> System.out.println("Invalid Choice. Please pick a number from menu.");
                }
            } catch (NoSuchElementException e){
                System.out.println("\nInput Closed. Exiting.");
                status = true;
            } catch (RuntimeException e){
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }

        FileUtil.saveBooks(BOOK_FILE, library.getBooks());
        FileUtil.saveMembers(MEMBER_FILE, library.getMembers());
        System.out.println("Library System Shutting Down");

    }

    private static int verifyInput(String prompt){
        while(true) {
            if (prompt != null) System.out.println(prompt);
            String line = user_in.nextLine().trim();
            try{
                return Integer.parseInt(line);
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please Enter a Number.");
            }
        }
    }

    private static int verifyInput(String prompt, int min, int max) {
        while (true) {
            int value = verifyInput(prompt);
            if (value >= min && value <= max) return value;
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = user_in.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be empty.");
        }
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
        System.out.println("6. Display Books");
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
        System.out.println("9. Check Available Copies");
        System.out.println("0. Exit");
        System.out.println("Enter Choice: ");
    }

    private static void displayReminders(){
        LocalDate today = LocalDate.now();
        boolean found = false;

        for (Book book : library.getBooks()){
            try {
                String reminder = book.reminderMsg(today);
                if (reminder != null){
                    System.out.println(reminder);
                    found=true;
                }
            } catch (RuntimeException e){
                System.out.println("Could not check reminder for book " + book.getBookCode());
            }
        }

        if (!found) {
            System.out.println("No reminders for today or next week.");
        }
    }

    private static void checkAvailableCopies() {
        List<String> shown = new ArrayList<>();
        boolean any = false;

        System.out.println(template);

        for (Book book : library.getBooks()) {
            String title = book.getTitle();
            String book_code = book.getBookCode();

            if (shown.contains(title.toLowerCase())) {
                continue;
            }
            shown.add(title.toLowerCase());

            int copies = library.availableCopies(title);
            if (copies > 0) {
                System.out.println(title + " - " + book_code + " - " + copies + " available");
                any = true;
            }
        }

        System.out.println(template);

        if (!any) {
            System.out.println("No books are currently available.");
        }
    }

    private static void selectUser(String library_name){
        String member_id = readString("Enter a Member ID: ");
        Member member = library.findMember(member_id);

        if (member == null) {
            System.out.println("User Does not exist.");
            return;
        }

        boolean user_menu_status = false;
        while (!user_menu_status) {
            try {
                user_menu(library_name, member.getMemberName());
                int choice = verifyInput(null);

                switch (choice) {
                    case 1 -> borrowBook(member);
                    case 2 -> returnBook(member);
                    case 3 -> renewBook(member);
                    case 4 -> {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println(member);
                    }
                    case 5 -> showBorrowedBooks(member);
                    case 6 -> checkAvailableCopies();
                    case 0 -> user_menu_status = true;
                    default -> System.out.println("Invalid choice. Please pick a number from the menu.");
                }
            } catch (NoSuchElementException e) {
                throw e;
            } catch (RuntimeException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private static void borrowBook(Member member) {
        String book_code = readString("Please Enter Book Code: ");
        Book book = library.findBook(book_code);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("This book is not available for borrowing.");
            return;
        }
        if (member.getNumberOfBooksBorrowed() >= 2) {
            System.out.println("Cannot Borrow More than 2 Books at a time.");
            return;
        }

        String dueDate = LocalDate.now().plusDays(30).format(FORMAT);
        book.borrowBook(dueDate);
        member.addBook(book_code);
        System.out.println("Book borrowed successfully. Due date: " + dueDate);
    }

    private static void returnBook(Member member) {
        String book_code = readString("Please Enter Book Code: ");
        Book book = library.findBook(book_code);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!member.getBorrowedBook().contains(book_code)) {
            System.out.println("You have not borrowed this book.");
            return;
        }

        book.returnBook();
        member.returnBook(book_code);
        System.out.println("Book returned successfully.");
    }

    private static void renewBook(Member member) {
        String book_code = readString("Please Enter Book Code: ");
        Book book = library.findBook(book_code);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!member.getBorrowedBook().contains(book_code)) {
            System.out.println("You have not borrowed this book.");
            return;
        }
        if (!book.isLoaned()) {
            System.out.println("This book is not currently borrowed");
            return;
        }

        int renewal_limit;
        if (member instanceof Student) {
            renewal_limit = 1;
        } else if (member instanceof Staff) {
            renewal_limit = 2;
        } else if (member instanceof Lecturer) {
            renewal_limit = Integer.MAX_VALUE;
        } else {
            renewal_limit = 0;
        }

        if (book.getRenewalCount() >= renewal_limit) {
            System.out.println("You have Reached the Renewal Limit.");
            return;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(book.getDueDate(), FORMAT);
            String new_due_date = parsedDate.plusDays(30).format(FORMAT);
            book.renewBook(new_due_date);
            System.out.println("Book renewed. New due date: " + new_due_date);
        } catch (DateTimeParseException | NullPointerException e) {
            System.out.println("Error: Invalid date format. Cannot renew book.");
        }
    }

    private static void showBorrowedBooks(Member member) {
        List<Book> borrowed = library.getBorrowedBooks(member);
        System.out.println();

        int counter = 1;
        if (member.getBorrowedBook().isEmpty()) {
            System.out.println("You have not borrowed any books.");
            return;
        }

        System.out.println("Books Borrowed: ");
        for (Book book : borrowed){
            System.out.println(counter + ". " + book.getTitle() + " - " + book.getBookCode() + " - Return by: " + book.getDueDate());
            counter++;
        }
    }

    private static void addBook() {
        String book_type;
        while (true) {
            book_type = readString("Book type (N/R): ").toUpperCase();
            if (book_type.equals("N") || book_type.equals("R")) break;
            System.out.println("Book type must be N or R.");
        }

        String title = readString("Book Title: ");
        int count = verifyInput("Number of Authors: ", 1, 20);

        List<String> authors = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            authors.add(readString("Author " + i + " : "));
        }

        int currentYear = LocalDate.now().getYear();
        int published_year = verifyInput("Published Year: ", 1000, currentYear);

        String publisher = readString("Publisher : ");
        String book_code = readString("Book Code: ");

        if (library.findBook(book_code) != null) {
            System.out.println("A Book with that call number already exists.");
            return;
        }

        Book book = new Book(
                book_type, title, count, authors, published_year, publisher, book_code, false, null
        );

        library.getBooks().add(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        String book_code = readString("Enter a Book Code: ");
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
        String member_id = readString("Member ID: ");

        if (library.findMember(member_id) != null){
            System.out.println("Member ID already exist");
            return;
        }

        String member_name = readString("Member Name: ");
        String member_type = readString("Member type (S = Staff, L = Lecturer, s = Students): ");

        List<String> books_borrowed = new ArrayList<>();
        Member member;

        switch (member_type){
            case "S" -> member = new Staff(member_id,member_name, books_borrowed);
            case "L" -> member = new Lecturer(member_id, member_name, books_borrowed);
            case "s" -> member = new Student(member_id, member_name, books_borrowed);
            default -> {
                System.out.println("Invalid member type.");
                return;
            }
        }

        library.getMembers().add(member);
        System.out.println("Member Added Successfully.");
    }

    private static void removeMember() {
        String member_id = readString("Enter Member ID that you wish to delete: ");
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

        int choice = verifyInput("Enter Choice: ");
        boolean found = false;

        switch (choice){
            case 1 -> {
                String book_title = readString("Enter title or keyword: ".toLowerCase());

                for (Book book : library.getBooks()){
                    if (book.getTitle().toLowerCase().contains(book_title)){
                        System.out.println(book);
                        found = true;
                    }
                }
            }
            case 2 -> {
                String book_author = readString("Enter Author: ").toLowerCase();

                for (Book book : library.getBooks()) {
                    for (String name : book.getAuthors()) {

                        if (name.toLowerCase().contains(book_author)) {
                            System.out.println(book);
                            found = true;
                            break;
                        }
                    }
                }
            }
            case 3 -> {
                int starting_year = verifyInput("Enter Starting Year (format - yyyy): ");
                int ending_year = verifyInput("Enter Ending Year (format - yyyy): ");

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
            }
            case 4 -> {
                return;
            }
        }
        if (!found){
            System.out.println("No Matching books found");
        }
    }
}