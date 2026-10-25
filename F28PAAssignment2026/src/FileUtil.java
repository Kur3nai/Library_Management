package F28PAAssignment2026.src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {

    public static List<Book> readBooks(String path) throws IOException {
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            int totalBooks = Integer.parseInt(br.readLine().trim());

            for (int count = 0; count < totalBooks; count++) {
                String line = br.readLine();

                if (line == null) {
                    throw new IOException("Not enough book records in file.");
                }

                if (line.isBlank()) {
                    count--;
                    continue;
                }

                String[] f = line.split(",");

                for (int i = 0; i < f.length; i++) {
                    f[i] = f[i].trim();
                }

                int i = 0;

                String bookType = f[i++];
                String title = f[i++];
                int numberOfAuthors = Integer.parseInt(f[i++]);

                List<String> authors = new ArrayList<>();

                for (int a = 0; a < numberOfAuthors; a++) {
                    authors.add(f[i++]);
                }

                int publishedYear = Integer.parseInt(f[i++]);
                String publisher = f[i++];
                String callNumber = f[i++];
                boolean loaned = Boolean.parseBoolean(f[i++]);

                String dueDate = null;

                if (loaned && i < f.length) {
                    dueDate = f[i];
                }

                Book book = new Book(
                        bookType,
                        title,
                        numberOfAuthors,
                        authors,
                        publishedYear,
                        publisher,
                        callNumber,
                        loaned,
                        dueDate
                );

                books.add(book);
            }
        }

        return books;
    }

    public static List<Member> readMembers(String path) throws IOException {
        List<Member> members = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;

            String[] f = line.split(",");
            for (int i = 0; i < f.length; i++) {
                f[i] = f[i].trim();
            }

            String id = f[0];
            String name = f[1];
            String type = f[2];
            int count = Integer.parseInt(f[3]);

            List<String> borrowed = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                borrowed.add(f[4 + i]);
            }

            Member m = switch (type) {
                case "S" -> new Staff(id, name, borrowed);
                case "L" -> new Lecturer(id, name, borrowed);
                case "s" -> new Student(id, name, borrowed);
                default -> throw new IOException("Unknown member type: " + type);
            };

            members.add(m);
        }
        br.close();
        return members;
    }

    public static void saveBooks(String fileName, List<Book> books) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of(fileName))) {

            // Write the total number of books if book.txt requires it
            writer.write(String.valueOf(books.size()));
            writer.newLine();

            for (Book book : books) {
                writer.write(book.toFileString());
                writer.newLine();
            }

            System.out.println("Books saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public static void saveMembers(String fileName, List<Member> members) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of(fileName))) {

            for (Member member : members) {
                writer.write(member.toFileString());
                writer.newLine();
            }

            System.out.println("Members saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }
}