package pacman.client;

import java.awt.*;

/**
 * @author Denis Kostigin
 */
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
