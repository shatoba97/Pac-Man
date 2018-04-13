package pacman.server;

import pacman.api.Ghost;
import pacman.api.PacMan;
import pacman.api.ViewProperties;

import java.io.*;
import java.util.Scanner;

class Game {

    ViewProperties viewProperties;
    boolean isPlaying;
    int[][] map;
    PacMan pacMan;
    Ghost ghost;

    Game(String path) {
        viewProperties = new ViewProperties();

        pacMan = new PacMan();
        pacMan.direction = 4;
        pacMan.nextDirection = 4;
        pacMan.score = 0;

        ghost = new Ghost();
        ghost.direction = 3;
        ghost.x = 100;
        ghost.y = 100;

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