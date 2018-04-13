package pacman.api;

public class Ghost extends Entity {

    public int startX;
    public int startY;

    public void spawn() {
        x = startX;
        y = startY;
    }
}