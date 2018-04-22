package pacman.api;

/**
 * @author Maxim Rozhkov
 */
public class Ghost extends Entity {

    public int startX;
    public int startY;

    public void spawn() {
        x = startX;
        y = startY;
    }
}