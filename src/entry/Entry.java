package entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Entry {

    private String username;
    private int score;
    private int day;
    private int month;
    private int year;


    public Entry(String username, int score, int day, int month, int year) {

        this.username = username;
        this.score = score;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static void addToFile(Entry[] entries, PrintWriter outputStream) {

        outputStream.println(entries.length);
        for (int i = 0; i < entries.length; i++) {
            outputStream.println(entries[i].getUsername() + "|"
                    + entries[i].getScore() + "|"
                    + entries[i].getDay() + "|"
                    + (entries[i].getMonth() + 1) + "|"
                    + entries[i].getYear());
        }
    }

    public static Entry[] readFile(BufferedReader inputStream) throws IOException {

        int numOfLines = Integer.parseInt(inputStream.readLine());

        Entry[] entries = new Entry[numOfLines];

        for (int i = 0; i < numOfLines; i++) {
            String line = inputStream.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(line, "|");

            String username = stringTokenizer.nextToken();
            int score = Integer.parseInt(stringTokenizer.nextToken());
            int day = Integer.parseInt(stringTokenizer.nextToken());
            int month = Integer.parseInt(stringTokenizer.nextToken());
            int year = Integer.parseInt(stringTokenizer.nextToken());

            entries[i] = new Entry(username, score, day, month, year);
        }
        return entries;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
