package figures;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Invader {

    private final static int INVADER_SPEED = 3;

    private Image invaderImage;
    private int invaderX;
    private int invaderY;
    private int invaderVelocityX = INVADER_SPEED;
    private int invaderVelocityY = INVADER_SPEED;
    private boolean isDown;


    public Invader(int xPos, int yPos) {
        this.invaderImage = new ImageIcon("images" + File.separator + "invader.png").getImage();
        this.invaderX = xPos;
        this.invaderY = yPos;
        this.isDown = false;
    }

    public void moveInvader() {
        if (!isDown) {
            invaderX = invaderX + invaderVelocityX;
            invaderY = invaderY + invaderVelocityY;
        } else {
            invaderY = invaderY + 3 * invaderVelocityY;
        }
    }

    public void invaderDown() {
        isDown = true;
    }

    public void drawInvader(Graphics g) {
        g.drawImage(this.invaderImage, this.invaderX, this.invaderY, null);
    }

    public void drawInvaderDown(Graphics g) {
        this.invaderImage = new ImageIcon("images" + File.separator + "invaderDown.png").getImage();
        g.drawImage(this.invaderImage, this.invaderX, this.invaderY, null);
    }

    public void changeInvaderXDirection() {
        invaderVelocityX = -(invaderVelocityX);
    }

    // Getters
    public int getInvaderX() {
        return invaderX;
    }

    public int getInvaderY() {
        return invaderY;
    }

    public Image getInvaderImage() {
        return invaderImage;
    }

    public boolean getIsDown() {
        return isDown;
    }
}
