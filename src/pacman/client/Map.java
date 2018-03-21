package pacman.client;

import pacman.api.ViewProperties;

import java.awt.*;

class Map {

    private static int[][] smap;
    private static int height = 10;
    private static int weight = 10;

    public static void setViewProperties(ViewProperties viewProperties) {
        height = viewProperties.heightRect;
        weight = viewProperties.weightRect;
    }

    public static void setMap(int[][] map) {
        smap = map;
    }

    public static void draw(Graphics2D g) {
        for (int i = 0; i < smap.length; ++i) {
            for (int j = 0; j < smap[i].length; ++j) {
                if (smap[i][j] == 0) {
                    Rect.setSize(i * weight, j * height,
                            weight, height, Color.BLACK);
                    Rect.draw(g);
                } else if (smap[i][j] == 1) {
                    Rect.setSize(i * weight, j * height,
                            weight, height, Color.BLACK);
                    Rect.draw(g);
                    g.setColor(Color.ORANGE);
                    g.fillOval(i * weight + (weight - (weight / 3)) / 2,
                            j * height + (height - (height / 3)) / 2,
                            weight / 3, height / 3);
                } else {
                    Rect.setSize(i * weight, j * height,
                            weight, height, new Color(10, 19, 111));
                    Rect.draw(g);
                }
            }
        }
    }
}
