package pacman.server;

import pacman.api.Pacman;

import java.util.Random;

class Game {
    boolean isPlaying;
    int[][] map;
    Pacman pacman;

    Game() {
        map = loadMapFromFile("SomePath");

        pacman = new Pacman();
        pacman.x = 27;
        pacman.y = 120;
        pacman.direction = 3;
        pacman.score = -100;

        isPlaying = true;
    }

    private int[][] loadMapFromFile(String path) {
        // todo
        int height = 20;
        int weight = 20;
        Random random = new Random();
        map = new int[height][weight];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < weight; ++j) {
                map[i][j] = random.nextInt() % 3;
            }
        }
        return map;
    }
}