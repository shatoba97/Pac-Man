package pacman.client;

import pacman.api.ViewProperties;

import java.awt.*;

 class Map {

    private static int[][] sMap;
    private static int sShift;
    private static int height = 10;
    private static int weight = 10;

    public static void setViewProperties(ViewProperties viewProperties) {
        height = viewProperties.heightRect;
        weight = viewProperties.weightRect;
    }

    public static void setMap(int[][] map, int shift) {
        sMap = map;
        sShift = shift;
    }

    public static void draw(Graphics2D g) {
        for (int i = 0; i < sMap.length; ++i) {
            for (int j = 0; j < sMap[i].length; ++j) {
                if (sMap[i][j] == 0) {
                    Rect.setSize(i * weight + sShift, j * height,
                            weight, height, Color.BLACK);
                    Rect.draw(g);
                } else if (sMap[i][j] == 1) {
                    Rect.setSize(i * weight + sShift, j * height,
                            weight, height, Color.BLACK);
                    Rect.draw(g);
                    g.setColor(Color.ORANGE);
                    g.fillOval(i * weight + (weight - (weight / 3)) / 2 + sShift,
                            j * height + (height - (height / 3)) / 2,
                            weight / 3, height / 3);
                } else {
                    Rect.setSize(i * weight + sShift, j * height,
                            weight, height, new Color(10, 19, 111));
                    Rect.draw(g);
                }
            }
        }
    }
}
