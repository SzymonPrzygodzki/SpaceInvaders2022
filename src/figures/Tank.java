package figures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Tank {

    private static Image tankImage;
    private int tankX;
    private int tankY;
    private int tankSpeed = 5;
    private int tankVelocity;


    public Tank(int xPos, int yPos) {
        this.tankImage = new ImageIcon("images" + File.separator + "tank.png").getImage();
        this.tankX = xPos;
        this.tankY = yPos;
    }

    public void move() {
        tankX = tankX + tankVelocity;
    }

    public void draw(Graphics g) {
        g.drawImage(this.getTankImage(), this.getTankX(), this.getTankY(),null);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setTankVelocity(-tankSpeed);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setTankVelocity(tankSpeed);
            move();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setTankVelocity(0);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setTankVelocity(0);
            move();
        }
    }

    // Getters
    public static Image getTankImage() {
        return tankImage;
    }

    public int getTankX() {
        return tankX;
    }

    public int getTankY() {
        return tankY;
    }

    // Setters
    public void setTankVelocity(int setTankVelocity) {
        this.tankVelocity = setTankVelocity;
    }

    public void setTankX(int tankX) {
        this.tankX = tankX;
    }


}
