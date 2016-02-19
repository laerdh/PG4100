package test;

import no.westerdals.PG4100.innlevering2.model.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestDataProvider {
    private static String testDataResource = "testdata.txt";
    private static List<Book> testData;
    private static Book testBook;

    public static List<Book> getTestData() {
        testData = new ArrayList<>();

        try (Scanner in = new Scanner(new File(testDataResource))) {
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] data = text.split(":");
                if (data.length == 5) {
                    testData.add(new Book(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4])));
                }
            }
            return testData;
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find testdata resource: " + e.getMessage());
        }
        return null;
    }

    public static int getTestDataSize() {
        return testData.size();
    }

    public static Book getTestBook() {
        testBook = new Book("Lemaitre, Pierre", "Alex", "9788203370052", 379, 2015);
        return testBook;
    }

    public static String getTestBookAnswer() {
        return testBook.getAuthor();
    }
}
