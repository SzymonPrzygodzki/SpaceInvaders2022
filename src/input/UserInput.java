package input;

import ui.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserInput extends KeyAdapter {

    GamePanel gamePanel;


    public UserInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.getTank().keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.newMissile();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gamePanel.getTank().keyReleased(e);
    }
}
