package pacman.server;

public class GameRoom extends Thread {

    public Game firstGame;
    public Game secondGame;
    public boolean firstPlayerIsReady = true;
    public boolean secondPlayerIsReady = true;

    GameRoom(Player player) {
        firstGame = GameFactory.createGame();
        firstPlayerIsReady = false;
        new PlayerThread(player, true, this).start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer) {
        firstGame = GameFactory.createGame();
        secondGame = GameFactory.createGame();
        firstPlayerIsReady = false;
        secondPlayerIsReady = false;
        new PlayerThread(firstPlayer, true, this).start();
        new PlayerThread(secondPlayer, false, this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                stepGame(firstGame);
                if (secondGame != null) {
                    stepGame(secondGame);
                    if (!firstGame.isPlaying && !secondGame.isPlaying) {
                        break;
                    }
                } else {
                    if (!firstGame.isPlaying) {
                        break;
                    }
                }
                sleep(200 / firstGame.viewProperties.heightRect);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void stepGame(Game game) {

        int h = game.viewProperties.heightRect;
        int w = game.viewProperties.weightRect;

        if (game.pacman.x % w == 0 && game.pacman.y % h == 0) {
            switch (game.pacman.nextDirection) {
                case 0: {
                    if (game.map[(game.pacman.x - 1) / w][(game.pacman.y + h / 2) / h] != 2){
                        game.pacman.direction = game.pacman.nextDirection;
                    }
                    break;
                }
                case 1: {
                    if (game.map[(game.pacman.x + w / 2) / w][(game.pacman.y - 1) / h] != 2){
                        game.pacman.direction = game.pacman.nextDirection;
                    }
                    break;
                }
                case 2: {
                    if (game.map[(game.pacman.x + w) / w][(game.pacman.y + h / 2) / h] != 2){
                        game.pacman.direction = game.pacman.nextDirection;
                    }
                    break;
                }
                case 3: {
                    if (game.map[(game.pacman.x + w / 2) / w][(game.pacman.y + h) / h] != 2){
                        game.pacman.direction = game.pacman.nextDirection;
                    }
                    break;
                }
            }
        }


        if (game.map[game.pacman.x / w][game.pacman.y / h] == 1) {
            game.map[game.pacman.x / w][game.pacman.y / h] = 0;
            game.pacman.score += 10;
        }

        switch (game.pacman.direction) {
            case 0: {
                if (game.map[(game.pacman.x - 1) / w][(game.pacman.y + h / 2) / h] != 2) {
                    game.pacman.x -= 1;
                }
                break;
            }
            case 1: {
                if (game.map[(game.pacman.x + w / 2) / w][(game.pacman.y - 1) / h] != 2) {
                    game.pacman.y -= 1;
                }
                break;
            }
            case 2: {
                if (game.map[(game.pacman.x + w) / w][(game.pacman.y + h / 2) / h] != 2) {
                    game.pacman.x += 1;
                }
                break;
            }
            case 3: {
                if (game.map[(game.pacman.x + w / 2) / w][(game.pacman.y + h) / h] != 2) {
                    game.pacman.y += 1;
                }
                break;
            }
        }
    }
}