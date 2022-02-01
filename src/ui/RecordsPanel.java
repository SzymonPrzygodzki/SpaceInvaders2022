package ui;

import entry.Entry;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RecordsPanel extends JPanel {

    private final static int TABLE_X_OFFSET = 40;
    private final static int TABLE_Y_OFFSET = 160;
    private final static int TABLE_Y_MARGIN = 20;
    private int screenWidth;
    private int screenHeight;


    public RecordsPanel(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRecordPanel(g);
    }

    public void drawRecordPanel(Graphics g) {
        g.drawImage(new ImageIcon("images" + File.separator + "background.png").getImage(), 0 , 0, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Best of the best", (screenWidth - fontMetrics.stringWidth("Best of the best"))/2, screenHeight/4);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.CYAN);

        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("USERNAME", TABLE_X_OFFSET, screenHeight/3);
        g.drawString("SCORE", (screenWidth - fontMetrics2.stringWidth("SCORE"))/ 2, screenHeight/3);
        g.drawString("DATE", screenWidth - TABLE_X_OFFSET - fontMetrics2.stringWidth("DATE"), screenHeight/3);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        Entry[] entries = getScoreEntries();

        for (int i = 0; i < entries.length; i++) {
            String username = entries[i].getUsername();
            String score = String.valueOf(entries[i].getScore());
            String date = getDate(entries[i]);

            FontMetrics fontMetrics3 = getFontMetrics(g.getFont());
            g.drawString(username, TABLE_X_OFFSET, TABLE_Y_OFFSET + ((TABLE_Y_MARGIN + fontMetrics3.getHeight()) * (i+1)));
            g.drawString(score,  (screenWidth - fontMetrics3.stringWidth(score))/ 2, TABLE_Y_OFFSET + ((TABLE_Y_MARGIN + fontMetrics3.getHeight()) * (i+1)));
            g.drawString(date, screenWidth - TABLE_X_OFFSET - fontMetrics3.stringWidth(date), TABLE_Y_OFFSET + ((TABLE_Y_MARGIN + fontMetrics3.getHeight()) * (i+1)));
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        FontMetrics fontMetrics4 = getFontMetrics(g.getFont());
        g.drawString("2022", ((screenWidth - fontMetrics4.stringWidth("2022")) / 2), 14 * screenHeight / 15);
    }

    public Entry[] getScoreEntries() {
        Entry[] entries = new Entry[5];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("gameScore.txt"));
            entries = Entry.readFile(bufferedReader);
        } catch (IOException e) {
            e.getMessage();
        }
        return entries;
    }

    public String getDate(Entry entry) {
        String day;
        String month;

        if (entry.getDay() < 10) {
            day = "0" + entry.getDay();
        } else {
            day = String.valueOf(entry.getDay());
        }

        if (entry.getMonth() < 10) {
            month = "0" + entry.getMonth();
        } else {
            month = String.valueOf(entry.getMonth());
        }
        return day + "/" + month + "/" + entry.getYear();
    }
}
