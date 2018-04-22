package pacman.client;

import pacman.api.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Denis Kostigin
 */
public class BotViewWindow extends JFrame {

    private PacManViewPanel gamePanel;
    private Thread updateThread;

    public BotViewWindow(IPacManAPI api, PacManAPI.GameType gameType) {
        ViewProperties viewProperties = api.getInfoAboutLeftPlayer().viewProperties;

        setTitle("Testing");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        /*addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                updateThread.stop();
                api.disconnect(false);
                System.exit(0);
            }
        });*/

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //updateThread.stop();
                    api.disconnect(false);
                    System.exit(0);
                }
            }
        });

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

        gamePanel = new PacManViewPanel();
        gamePanel.setViewProperties(viewProperties);

        setContentPane(gamePanel);

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GameInfo leftGameInfo;
                GameInfo rightGameInfo = null;
                while (true) {
                    leftGameInfo = api.getInfoAboutLeftPlayer();
                    if (leftGameInfo == null) break;
                    if (!leftGameInfo.isPlaying) {
                        GameInfo gameInfo = api.disconnect(true);
                        JOptionPane.showMessageDialog(BotViewWindow.this,
                                "My: " + gameInfo.gameResult.myScore + " Enemy: " +
                                        gameInfo.gameResult.enemyScore, "Result", JOptionPane.INFORMATION_MESSAGE);
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
                        Thread.sleep(25);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });

        updateThread.start();

        setVisible(true);
    }
}