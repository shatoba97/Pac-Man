package pacman.server;

import pacman.api.Direction;
import pacman.api.Ghost;
import pacman.api.PacMan;
import pacman.api.ViewProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Alex Shatoba
 */
class Game {

    boolean isPlaying;
    ViewProperties viewProperties;
    int[][] map;
    PacMan pacMan;
    int eat = 0;
    ArrayList<Ghost> ghosts;

    Game(String path, int ghostCount) {
        viewProperties = new ViewProperties();

        pacMan = new PacMan();
        pacMan.direction = Direction.NOTING;
        pacMan.nextDirection = Direction.NOTING;
        pacMan.score = 0;
        pacMan.deathCounter = 0;

        if (ghostCount > 0) {
            ghosts = new ArrayList<>();
            Ghost ghost = new Ghost();
            ghost.direction = Direction.NOTING;
            ghost.startX = 240;
            ghost.startY = 220;
            ghost.x = ghost.startX;
            ghost.y = ghost.startY;
            ghosts.add(ghost);
            if (ghostCount > 1) {
                ghost = new Ghost();
                ghost.direction = Direction.NOTING;
                ghost.startX = 320;
                ghost.startY = 220;
                ghost.x = ghost.startX;
                ghost.y = ghost.startY;
                ghosts.add(ghost);
                if (ghostCount > 2) {
                    ghost = new Ghost();
                    ghost.direction = Direction.NOTING;
                    ghost.startX = 320;
                    ghost.startY = 320;
                    ghost.x = ghost.startX;
                    ghost.y = ghost.startY;
                    ghosts.add(ghost);
                    if (ghostCount > 3) {
                        ghost = new Ghost();
                        ghost.direction = Direction.NOTING;
                        ghost.startX = 240;
                        ghost.startY = 320;
                        ghost.x = ghost.startX;
                        ghost.y = ghost.startY;
                        ghosts.add(ghost);
                    }
                }
            }
        }

        isPlaying = true;

        map = loadMapFromFile(path);
    }

    private int[][] loadMapFromFile(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            int hMap = scanner.nextInt();
            int wMap = scanner.nextInt();

            viewProperties.heightRect = scanner.nextInt();
            viewProperties.weightRect = scanner.nextInt();
            viewProperties.weightScreen = scanner.nextInt();
            viewProperties.heightScreen = scanner.nextInt();

            pacMan.x = scanner.nextInt();
            pacMan.y = scanner.nextInt();

            map = new int[hMap][wMap];

            for (int i = 0; i < hMap; ++i) {
                for (int j = 0; j < wMap; ++j) {
                    map[i][j] = scanner.nextInt();
                }
            }

        } catch (FileNotFoundException ignored) {
            ignored.printStackTrace();
        }

        return map;
    }
}