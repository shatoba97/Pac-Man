package pacman.server;

import pacman.api.Pacman;

import java.io.*;
import java.util.Scanner;

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
        int height = 31;
        int weight = 28;
        map = new int[weight][height];
        try {
            File file = new File("Map.txt");
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < weight; ++j) {
                    map[j][i] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException ignored) {
            ignored.printStackTrace();
        }
        return map;
    }
}