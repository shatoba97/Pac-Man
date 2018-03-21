package pacman.client;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PeopleViewWindow extends JFrame {

    private PacManViewPanel mainPanel;

    public PeopleViewWindow(IPacManAPI api) {

        ViewProperties viewProperties = api.getViewProperties();

        setTitle("Score: 0");
        setSize(viewProperties.weightScreen, viewProperties.heightScreen + 25);
        setResizable(false);
        setLocationRelativeTo(null);

        setAutoRequestFocus(true);

        mainPanel = new PacManViewPanel();
        mainPanel.setViewProperties(viewProperties);

        setContentPane(mainPanel);

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
                while (true) {
                    GameInfo gameInfo = api.getInfoAboutLeftPlayer();
                    setTitle("Score: " + gameInfo.pacman.score);
                    mainPanel.repaint(gameInfo);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();

        setVisible(true);
    }
}