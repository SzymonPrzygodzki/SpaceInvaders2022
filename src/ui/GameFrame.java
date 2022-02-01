package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import static java.awt.event.ActionEvent.META_MASK;

public class GameFrame extends JFrame {

    private final static int SCREEN_WIDTH = 400;
    private final static int SCREEN_HEIGHT = 500;

    private JLabel nameLabel;
    private JButton loginButton;
    private JButton newGameButton;
    private JButton recordsButton;
    private JButton exitButton;
    private JMenuBar menuBar;

    private JMenuItem logInItem;
    private JMenuItem newGameItem;
    private JMenuItem recordsItem;
    private JMenuItem exitItem;

    private WelcomePanel welcomePanel;
    private GamePanel gamePanel;
    private RecordsPanel recordsPanel;

    private String username = "";
    private String activePanel = "welcomePanel";


    public GameFrame() {
        this.setTitle("Space Invaders");
        this.setBounds(0,0,900,600);

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menuFile = menuBar.add(new JMenu("File"));

        GroupLayout layout = new GroupLayout(this.getContentPane());

        Action logInAction = new Action("Log in", "Click to log in", KeyStroke.getKeyStroke(KeyEvent.VK_L, META_MASK), layout);
        Action newGameAction = new Action("New Game", "Click to start the game", KeyStroke.getKeyStroke(KeyEvent.VK_S, META_MASK), layout);
        Action recordsAction = new Action("Records", "Click to check the records", KeyStroke.getKeyStroke(KeyEvent.VK_R, META_MASK), layout);
        Action exitAction = new Action("Exit", "Click to exit", KeyStroke.getKeyStroke(KeyEvent.VK_E, META_MASK), layout);

        logInItem = menuFile.add(logInAction);
        newGameItem = menuFile.add(newGameAction);
        recordsItem = menuFile.add(recordsAction);
        menuFile.addSeparator();
        exitItem = menuFile.add(exitAction);

        nameLabel = new JLabel("  Log in to play!");
        loginButton = new JButton(logInAction);
        newGameButton = new JButton(newGameAction);
        recordsButton = new JButton(recordsAction);
        exitButton = new JButton(exitAction);
        welcomePanel = new WelcomePanel(SCREEN_WIDTH, SCREEN_HEIGHT);

        loginButton.setEnabled(true);
        newGameButton.setEnabled(false);
        recordsButton.setEnabled(true);
        exitButton.setEnabled(true);

        logInItem.setEnabled(true);
        newGameItem.setEnabled(false);
        recordsItem.setEnabled(true);
        exitItem.setEnabled(true);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(welcomePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(nameLabel)
                                        .addGap(100,100,100)
                                        .addComponent(loginButton)
                                        .addComponent(newGameButton)
                                        .addComponent(recordsButton)
                                        .addComponent(exitButton))
                        .addContainerGap(50,50));

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(welcomePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                        .addGap(20)
                                        .addComponent(nameLabel)
                                        .addGap(20)
                                        .addComponent(loginButton)
                                        .addComponent(newGameButton)
                                        .addComponent(recordsButton)
                                        .addContainerGap(10, Short.MAX_VALUE)
                                        .addComponent(exitButton)));

        this.getContentPane().setLayout(layout);

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GameFrame().setVisible(true);
    }

    private class Action extends AbstractAction {
        GroupLayout layout;

        public Action(String name, String description, KeyStroke keyStroke, GroupLayout layout) {
            this.putValue(AbstractAction.NAME, name);
            this.putValue(AbstractAction.SHORT_DESCRIPTION, description);
            this.putValue(AbstractAction.ACCELERATOR_KEY, keyStroke);
            this.layout = layout;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Log in")) {

                String input = "";

                while (input.equals("")) {
                    input = (String) JOptionPane.showInputDialog(null, "Enter your name", "Log in", JOptionPane.QUESTION_MESSAGE, new ImageIcon("images" + File.separator + "userIcon.png"),null, null);
                    if (input == null) {
                        input = "cancel";
                    }
                    if (input.equals("")) {
                        JOptionPane.showMessageDialog(null, "Username field cannot be empty. Try again.", "Warning", JOptionPane.WARNING_MESSAGE, new ImageIcon("images" + File.separator + "warningIcon.png"));
                    }
                }
                if (!input.equals("cancel")) {
                    username = input;
                    nameLabel.setText("  Logged as: " + username);

                    if (!username.equals("")) {
                        loginButton.setEnabled(false);
                        logInItem.setEnabled(false);
                    } else {
                        loginButton.setEnabled(true);
                        logInItem.setEnabled(true);
                    }

                    if (activePanel.equals("recordsPanel")) {
                        recordsButton.setEnabled(false);
                        recordsItem.setEnabled(false);
                    } else {
                        recordsButton.setEnabled(true);
                        recordsItem.setEnabled(true);
                    }
                    newGameButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    newGameItem.setEnabled(true);
                    exitItem.setEnabled(true);
                }

            } else if (e.getActionCommand().equals("New Game")) {
                gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGHT, username);

                if (activePanel.equals("welcomePanel")) {
                    layout.replace(welcomePanel, gamePanel);
                }
                if (activePanel.equals("recordsPanel")) {
                    layout.replace(recordsPanel, gamePanel);
                }
                activePanel = "gamePanel";
                gamePanel.requestFocus();

                loginButton.setEnabled(false);
                newGameButton.setEnabled(false);
                recordsButton.setEnabled(true);
                exitButton.setEnabled(true);

                logInItem.setEnabled(false);
                newGameItem.setEnabled(false);
                recordsItem.setEnabled(true);
                exitItem.setEnabled(true);


            } else if (e.getActionCommand().equals("Records")) {
                recordsPanel = new RecordsPanel(SCREEN_WIDTH, SCREEN_HEIGHT);

                if (activePanel.equals("gamePanel")) {
                    layout.replace(gamePanel, recordsPanel);
                    if (gamePanel.isRunning()) {
                        gamePanel.stopGame();
                    }
                }
                if (activePanel.equals("welcomePanel")) {
                    layout.replace(welcomePanel, recordsPanel);
                }
                activePanel = "recordsPanel";

                if (!username.equals("")) {
                    newGameButton.setEnabled(true);
                    newGameItem.setEnabled(true);
                    loginButton.setEnabled(false);
                    logInItem.setEnabled(false);
                } else {
                    newGameButton.setEnabled(false);
                    newGameItem.setEnabled(false);
                    loginButton.setEnabled(true);
                    logInItem.setEnabled(true);
                }
                recordsButton.setEnabled(false);
                recordsItem.setEnabled(false);
                exitButton.setEnabled(true);
                exitItem.setEnabled(true);

            } else if (e.getActionCommand().equals("Exit")) {
                System.exit(0);
            }
        }
    }
}
