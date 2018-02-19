package pacman.api;

public class PacManAPI implements IPacManAPI {
    @Override
    public boolean connection(String IP, int port, int gameType) {
        return false;
    }

    @Override
    public GameInfo disconnect(boolean isWait) {
        return null;
    }

    @Override
    public GameInfo sendRequest(int requestCode) {
        return null;
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }

    @Override
    public void toLeft() {

    }

    @Override
    public void toRight() {

    }
}