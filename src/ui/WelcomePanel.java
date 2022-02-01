package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class WelcomePanel extends JPanel {

    private int screenWidth;
    private int screenHeight;


    public WelcomePanel(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWelcomePanel(g);
    }

    public void drawWelcomePanel(Graphics g) {
        g.drawImage(new ImageIcon("images" + File.separator + "background.png").getImage(), 0, 0, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Welcome to", ((screenWidth - fontMetrics.stringWidth("Welcome to")) / 2), screenHeight / 4);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("Space Invaders", ((screenWidth - fontMetrics2.stringWidth("Space Invaders")) / 2), screenHeight / 3);

        Image invaderCover = new ImageIcon("images" + File.separator + "invaderCover.png").getImage();
        g.drawImage(invaderCover, (screenWidth - invaderCover.getWidth(null))/2, screenHeight /2, null);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        FontMetrics fontMetrics3 = getFontMetrics(g.getFont());
        g.drawString("developed by Szymon Przygodzki", ((screenWidth - fontMetrics3.stringWidth("developed by Szymon Przygodzki")) / 2), 12 * screenHeight / 15);
        g.drawString("2022", ((screenWidth - fontMetrics3.stringWidth("2022")) / 2), 14 * screenHeight / 15);
    }
}
