package F28PAAssignment2026.src;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Book {
    private final String bookType;
    private final String title;
    private final List<String> authors;
    private final int publishedYear;
    private final String publisher;
    private final String bookCode;
    private boolean loaned;
    private String dueDate;
    private int renewal_Count = 0;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Book(String bookType, String title, int numberOfAuthors,
                List<String> authors, int publishedYear, String publisher,
                String callNumber, boolean loaned, String dueDate) {

        this.bookType = bookType;
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.publisher = publisher;
        this.bookCode = callNumber;
        this.loaned = loaned;
        this.dueDate = dueDate;
    }

    public String getBookCode() {
        return bookCode;
    }

    public List<String> getAuthors(){
        return authors;
    }

    public int getPublishedYear(){
        return publishedYear;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLoaned() {
        return loaned;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getRenewalCount(){
        return renewal_Count;
    }

    public boolean isReferenceBook() {
        return bookType.equalsIgnoreCase("R");
    }

    public boolean isAvailable() {
        return !loaned && !isReferenceBook();
    }

    public void borrowBook(String dueDate) {
        if (isReferenceBook()) {
            System.out.println("Reference books cannot be borrowed.");
        } else if (loaned) {
            System.out.println("This book is already borrowed.");
        } else {
            this.loaned = true;
            this.dueDate = dueDate;
            this.renewal_Count = 0;
            System.out.println(title + " borrowed successfully.");
        }
    }

    public void returnBook() {
        if (!loaned) {
            System.out.println("This book is not currently borrowed.");
        } else {
            this.loaned = false;
            this.dueDate = null;
            System.out.println(title + " returned successfully.");
        }
    }

    public void renewBook(String newDueDate){
        if(!loaned){
            System.out.println("This book is not currently borrowed.");
        } else {
            this.dueDate = newDueDate;
            this.renewal_Count++;
            System.out.println(title + " renewed successfully.");
        }
    }

    public String reminderMsg(LocalDate today){
        if (!loaned || dueDate == null){
            return null;
        }

        LocalDate due;
        try {
            due = LocalDate.parse(dueDate, FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }

        long daysUntilDue = ChronoUnit.DAYS.between(today,due);

        if (daysUntilDue < 0) {
            long daysLate = -daysUntilDue;
            double fine = daysLate * 1.0;
            return "OVERDUE: " + title + " is "
                    + daysLate + (daysLate == 1 ? " day" : " days")
                    + " late. Fine: RM " + String.format("%.2f", fine);

        } else if (daysUntilDue == 0) {
            return "Due Today: " + title + ". Please Return It Today.";
        } else if (daysUntilDue <= 7) {
            return "Reminder: " + title + " is due in "
                    + daysUntilDue + (daysUntilDue == 1 ? " day" : " days") + ".";
        }

        return null;
    }

    public String toFileString() {
        StringBuilder result = new StringBuilder();

        result.append(bookType.toUpperCase()).append(", ")
                .append(title).append(", ")
                .append(authors.size()).append(", ")
                .append(String.join(", ", authors)).append(", ")
                .append(publishedYear).append(", ")
                .append(publisher).append(", ")
                .append(bookCode).append(", ")
                .append(loaned).append(", ");

        if (loaned && dueDate != null && !dueDate.isBlank()) {
            result.append(dueDate);
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------------" +
                "\nTitle: " + title +
                "\nBook Type: " + bookType +
                "\nAuthors: " + authors +
                "\nPublished Year: " + publishedYear +
                "\nPublisher: " + publisher +
                "\nBook Code: " + bookCode +
                "\nLoaned: " + loaned +
                "\nDue Date: " + (dueDate == null ? "N/A" : dueDate) +
                "\n------------------------------------------------------";
    }
}