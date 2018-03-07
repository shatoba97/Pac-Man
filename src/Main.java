import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            new Window(api);
        }
    }
}

class Window extends JFrame {

    Window(IPacManAPI api) {
        setTitle("Testing!!!");
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setAutoRequestFocus(true);

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
                    System.out.println(gameInfo.pacman.x + " " + gameInfo.pacman.y);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();
    }
}