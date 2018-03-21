package pacman.client;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PeopleViewWindow extends JFrame {

    private PacManViewPanel gamePanel;

    public PeopleViewWindow(IPacManAPI api, PacManAPI.GameType gameType) {

        ViewProperties viewProperties = api.getViewProperties();

        setTitle("Testing");

        if (gameType == PacManAPI.GameType.SINGLE) {
            setSize(viewProperties.weightScreen,
                    viewProperties.heightScreen + 25);
        } else {
            setSize(viewProperties.weightScreen * 2 + 30,
                    viewProperties.heightScreen + 25);
        }

        setResizable(false);
        setLocationRelativeTo(null);

        setAutoRequestFocus(true);

        gamePanel = new PacManViewPanel();
        gamePanel.setViewProperties(viewProperties);

        add(gamePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    api.toLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    api.toRight();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    api.toUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    api.toDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    api.disconnect(false);
                    System.exit(404);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                GameInfo leftGameInfo;
                GameInfo rightGameInfo = null;
                while (true) {
                    leftGameInfo = api.getInfoAboutLeftPlayer();
                    if (gameType == PacManAPI.GameType.VERSUS) {
                        rightGameInfo = api.getInfoAboutRightPlayer();
                    }
                    gamePanel.repaint(leftGameInfo, rightGameInfo);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();

        setVisible(true);
    }
}