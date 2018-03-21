package pacman.server;

import pacman.api.Pacman;
import pacman.api.ViewProperties;

import java.io.*;
import java.util.Scanner;

class Game {

    ViewProperties viewProperties;
    boolean isPlaying;
    int[][] map;
    Pacman pacman;

    Game(String path) {
        viewProperties = new ViewProperties();

        pacman = new Pacman();
        pacman.direction = 4;
        pacman.nextDirection = 4;
        pacman.score = 0;

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

            pacman.x = scanner.nextInt();
            pacman.y = scanner.nextInt();

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