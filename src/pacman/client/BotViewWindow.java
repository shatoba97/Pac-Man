package pacman.client;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BotViewWindow extends JFrame {

    private PacManViewPanel gamePanel;

    public BotViewWindow(IPacManAPI api, PacManAPI.GameType gameType) {
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

        gamePanel = new PacManViewPanel();
        gamePanel.setViewProperties(viewProperties);

        setContentPane(gamePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    api.disconnect(false);
                    System.exit(404);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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
            }
        }).start();

        setVisible(true);
    }
}
