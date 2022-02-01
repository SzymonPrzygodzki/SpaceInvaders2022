package ui;

import entry.Entry;
import figures.Invader;
import figures.Missile;
import figures.Tank;
import input.UserInput;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {

    private final static int TANK_WIDTH = 40;
    private final static int INVADER_WIDTH = 50;
    private final static int DELAY = 1000;

    private int screenWidth;
    private int screenHeight;
    private String username;
    private boolean running;
    private Tank tank;
    private ArrayList<Invader> invaders = new ArrayList<>();
    private ArrayList<Missile> missiles = new ArrayList<>();
    private Timer timer;
    private Random random;
    private Thread gameThread;
    private int score;


    public GamePanel(int screenWidth, int screenHeight, String username) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.username = username;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new UserInput(this));

        this.running = true;

        startGame();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGame() {
        newTank();
        generateInvaders();
    }

    public void newTank() {
        int tankYOffset = 50;
        tank = new Tank((screenWidth - TANK_WIDTH)/2, screenHeight - tankYOffset);
    }

    public void generateInvaders() {
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                random = new Random();
                invaders.add(new Invader(random.nextInt(screenWidth - INVADER_WIDTH), 0));
            }
        });
        timer.start();
    }

    public void newMissile() {
        missiles.add(new Missile(tank.getTankX() + TANK_WIDTH/2, tank.getTankY()));
    }

    @Override
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon("images" + File.separator + "background.png").getImage(), 0, 0, null);
            drawGame(g);
    }

    public void drawGame(Graphics g) {
        if (running) {
            tank.draw(g);
            for (Invader i : invaders) {
                i.drawInvader(g);
                if (i.getIsDown()) {
                    i.drawInvaderDown(g);
                }
            }
            for (Missile m : missiles) {
                m.drawMissile(g);
            }
            showScore(g);
        } else {
            gameOver(g);
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tank.move();
                for (Invader i : invaders) {
                    i.moveInvader();
                }
                for (Missile m : missiles) {
                    m.moveMissile();
                }
                checkCollision();
                removeInvaderDown();
                repaint();
                delta--;
            }
        }

        saveToFile(username, score);
    }

    public void checkCollision() {
        tankCollision();
        invaderCollision();
        removeMissileIfOut();
        invaderShotDown();
    }

    public void tankCollision() {

        if (tank.getTankX() < 0) {
            tank.setTankX(0);
        }
        if (tank.getTankX() + TANK_WIDTH > screenWidth) {
            tank.setTankX(screenWidth - TANK_WIDTH);
        }
    }

    public void invaderCollision() {
        for (int i = 0; i < invaders.size(); i++) {

            Invader invader = invaders.get(i);
            // Invader reaches a left bound.
            if (invader.getInvaderX() <= 0) {
                invader.changeInvaderXDirection();
            }
            // Invader reaches a right bound.
            if (invader.getInvaderX() + invader.getInvaderImage().getWidth(this) >= screenWidth) {
                invader.changeInvaderXDirection();
            }
            // Invader reaches a bottom bound.
            if (invader.getInvaderY() + invader.getInvaderImage().getHeight(this)  >= screenHeight && !invader.getIsDown()) {
                running = false;
                timer.stop();
            }
            // Not-down invader collides with the tank.
            if (!invader.getIsDown() && ((invader.getInvaderX() >= tank.getTankX() && invader.getInvaderX() <= tank.getTankX() + TANK_WIDTH && invader.getInvaderY() + 50 >= tank.getTankY()) || (invader.getInvaderX() + 50 >= tank.getTankX() && invader.getInvaderX() + 50 <= tank.getTankX() + TANK_WIDTH && invader.getInvaderY() + 50>= tank.getTankY()))) {
                running = false;
                timer.stop();
            }
        }
    }

    public void removeMissileIfOut() {
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.getMissileY() < 0) {
                missiles.remove(missile);
            }
        }
    }

    public void invaderShotDown() {
        int missileWidth = 10;
        int invaderWidthAndHight = 50;

        for (int i = 0; i < missiles.size(); i++) {
            for (int j = 0; j < invaders.size(); j++) {
                if (missiles.get(i).getMissileX() + missileWidth/2 >= invaders.get(j).getInvaderX() && missiles.get(i).getMissileX() + missileWidth/2 <= invaders.get(j).getInvaderX() + invaderWidthAndHight && missiles.get(i).getMissileY() <= invaders.get(j).getInvaderY() + invaderWidthAndHight && missiles.get(i).getMissileY() >= invaders.get(j).getInvaderY())  {
                    addPoint(invaders.get(j));
                    invaders.get(j).invaderDown();
                    missiles.remove(i);
                    break;
                }
            }
        }
    }

    public void removeInvaderDown() {
        for (int i = 0; i < invaders.size(); i++) {
            if (invaders.get(i).getInvaderY() > screenHeight) {
                invaders.remove(invaders.get(i));
            }
        }
    }

    public void addPoint(Invader invader) {
        if (!invader.getIsDown()) {
            score++;
        }
    }

    public void showScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("YOUR SCORE: " + score, 10, 20);
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", ((screenWidth - fontMetrics.stringWidth("GAME OVER")) / 2), screenHeight / 3);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Your score: " + score, screenWidth - fontMetrics.stringWidth("Your score **"), screenHeight / 2);
    }

    public void saveToFile(String username, int score) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("gameScore.txt"));
            Entry[] entries = Entry.readFile(bufferedReader);
            bufferedReader.close();

            for (Entry entry : entries) {
                entry.setMonth(entry.getMonth() - 1);
            }

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());

            for (int i = 0; i < entries.length; i++) {
                if (score > entries[i].getScore()) {
                    for (int j = (entries.length - 1); j > i; j--) {
                        entries[j] = entries[j - 1];
                    }
                    entries[i] = new Entry(username, score, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                    break;
                }
            }
            try {
                PrintWriter printWriter = new PrintWriter("gameScore.txt");
                Entry.addToFile(entries, printWriter);

                printWriter.close();

            } catch (IOException e) {

                e.getMessage();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void stopGame() {
        running = false;
        timer.stop();
        gameThread.stop();
    }

    // Getters
    public Tank getTank() {
        return tank;
    }

    public boolean isRunning() {
        return running;
    }
}