import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            ViewProperties viewProperties = api.getViewProperties();
            new Window(api, viewProperties);
        }
    }
}

class Window extends JFrame {

    private MainPanel mainPanel;

    Window(IPacManAPI api, ViewProperties viewProperties) {
        setTitle("Testing!!!");
        setUndecorated(true);
        setSize(viewProperties.weightScreen, viewProperties.heightScreen);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setAutoRequestFocus(true);

        mainPanel = new MainPanel();
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
                    mainPanel.repaint(gameInfo);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();
    }
}

class MainPanel extends JPanel {

    private GameInfo gameInfo;

    void repaint(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (gameInfo != null) {
            Map.setMap(gameInfo.map);
            Map.draw(graphics2D);
            graphics2D.setColor(Color.YELLOW);
            graphics2D.fillOval(gameInfo.pacman.x, gameInfo.pacman.y,
                    20, 20);
        }
    }
}

class Rect {

    private static int sx;
    private static int sy;
    private static int sw;
    private static  int sh;
    private static Color scolor;

    public static void setSize(int x, int y, int w, int h, Color color) {
        sx = x;
        sy = y;
        sw = w;
        sh = h;
        scolor = color;
    }

    public static void draw(Graphics2D g) {
        g.setColor(scolor);
        g.fillRect(sx, sy, sw, sh);
    }
}

class Map {

    private static int[][] smap;

    public static void setMap(int[][] map) {
        smap = map;
    }

    public static void draw(Graphics2D g) {
        for (int i = 0; i < smap.length; ++i) {
            for (int j = 0; j < smap[i].length; ++j) {
                if (smap[i][j] == 0) {
                    Rect.setSize(i * 20, j * 20, 20, 20, Color.WHITE);
                    Rect.draw(g);
                } else if (smap[i][j] == 1) {
                    Rect.setSize(i * 20, j * 20, 20, 20, Color.WHITE);
                    Rect.draw(g);
                    g.setColor(Color.BLUE);
                    g.fillOval(i * 20 + 7, j * 20 + 7, 6, 6);
                } else {
                    Rect.setSize(i * 20, j * 20, 20, 20, Color.BLACK);
                    Rect.draw(g);
                }
            }
        }
    }
}