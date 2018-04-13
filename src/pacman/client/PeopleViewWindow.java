package pacman.client;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PeopleViewWindow extends JFrame {

    private PacManViewPanel gamePanel;
    Thread updateThread;

    public PeopleViewWindow(IPacManAPI api, PacManAPI.GameType gameType) {

        ViewProperties viewProperties = api.getInfoAboutLeftPlayer().viewProperties;

        setTitle("Testing");

        if (gameType == PacManAPI.GameType.SINGLE_WITH_GHOST
                || gameType == PacManAPI.GameType.SINGLE_WITHOUT_GHOST) {
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameInfo gameInfo = api.disconnect(true);
                JOptionPane.showMessageDialog(PeopleViewWindow.this,
                        "My: " + gameInfo.gameResult.myScore + " Enemy: " +
                                gameInfo.gameResult.enemyScore, "Result", JOptionPane.INFORMATION_MESSAGE);
                updateThread.interrupt();
                System.exit(0);
            }
        });

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
                }
            }
        });

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GameInfo leftGameInfo;
                GameInfo rightGameInfo = null;
                while (true) {
                    leftGameInfo = api.getInfoAboutLeftPlayer();
                    if (leftGameInfo == null) break;
                    if (!leftGameInfo.isPlaying) {
                        api.disconnect(false);
                        System.exit(0);
                    }

                    setTitle("Score: " + leftGameInfo.pacMan.score + " Death: " + leftGameInfo.pacMan.deathCounter);
                    if (gameType == PacManAPI.GameType.VERSUS_WITH_GHOST ||
                            gameType == PacManAPI.GameType.VERSUS_WITHOUT_GHOST) {
                        rightGameInfo = api.getInfoAboutRightPlayer();
                        if (rightGameInfo == null) break;
                    }
                    gamePanel.repaint(leftGameInfo, rightGameInfo);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ignored) {}
                }
            }
        });
        updateThread.start();

        setVisible(true);
    }
}