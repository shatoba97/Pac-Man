package pacman.client;

import pacman.api.GameInfo;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.*;

class PacManViewPanel extends JPanel {

    private GameInfo gameInfo;

    private static int height = 20;
    private static int weight = 20;

    void setViewProperties(ViewProperties viewProperties) {
        Map.setViewProperties(viewProperties);
        height = viewProperties.heightRect;
        weight = viewProperties.weightRect;
    }

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
                                                        weight, height);
        }
    }
}
