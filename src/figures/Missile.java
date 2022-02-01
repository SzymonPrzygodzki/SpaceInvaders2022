package figures;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Missile {

    private static Image missileImage;
    private int missileX;
    private int missileY;
    private int missileVelocity = -5;


    public Missile(int xPos, int yPos) {
        this.missileImage = new ImageIcon("images" + File.separator + "missile.png").getImage();
        this.missileX = xPos - missileImage.getWidth(null)/2;
        this.missileY = yPos;
    }

    public void moveMissile() {
        missileY = missileY + missileVelocity;
    }

    public void drawMissile(Graphics g) {
        g.drawImage(this.getMissileImage(), this.getMissileX(), this.getMissileY(), null);
    }



    // Getters
    public static Image getMissileImage() {
        return missileImage;
    }

    public int getMissileX() {
        return missileX;
    }

    public int getMissileY() {
        return missileY;
    }
}
