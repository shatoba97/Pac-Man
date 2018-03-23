package pacman.client;

import pacman.api.GameInfo;
import pacman.api.ViewProperties;

import javax.swing.*;
import java.awt.*;

class PacManViewPanel extends JPanel {

    private GameInfo leftGameInfo;
    private GameInfo rightGameInfo;

    private static int height = 20;
    private static int weight = 20;
    private static int weightScreen = 300;

    void setViewProperties(ViewProperties viewProperties) {
        Map.setViewProperties(viewProperties);
        height = viewProperties.heightRect;
        weight = viewProperties.weightRect;
        weightScreen = viewProperties.weightScreen;
    }

    void repaint(GameInfo leftGameInfo, GameInfo rightGameInfo) {
        this.leftGameInfo = leftGameInfo;
        this.rightGameInfo = rightGameInfo;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (leftGameInfo != null) {
            draw(leftGameInfo, 0, graphics2D);
        }

        if (rightGameInfo != null) {
            draw(rightGameInfo, weightScreen + 30, graphics2D);
        }
    }

    private void draw(GameInfo gameInfo, int shift, Graphics2D graphics2D) {
        Map.setMap(gameInfo.map, shift);
        Map.draw(graphics2D);
        graphics2D.setColor(Color.YELLOW);
        graphics2D.fillOval(gameInfo.pacman.x + shift, gameInfo.pacman.y,
                weight, height);
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(gameInfo.ghosts.get(0).x + shift, gameInfo.ghosts.get(0).y,
                weight, height);
    }
}
