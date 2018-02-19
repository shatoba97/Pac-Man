package pacman.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class PacManAPI implements IPacManAPI {

    private OutputStreamWriter output;
    private BufferedReader input;

    public enum GameType {SINGLE, VERSUS, VIEW};

    private GameInfo sendRequestCode(int requestCode) {
        GameInfo gameInfo;
        try {
            output.write(requestCode);
            String responseString = input.readLine();
            // Обработка responseString
            // Сборка объекта GameInfo с помощью GoogleSON (JSON)
            gameInfo = new GameInfo();
            gameInfo.responseCode = 200;
        } catch (IOException ignored) {
            gameInfo = new GameInfo();
            gameInfo.responseCode = 404;
        }
        return gameInfo;
    }

    private GameInfo checkResponseCode(GameInfo gameInfo) {
        if (gameInfo.responseCode == 200) {
            return gameInfo;
        } else {
            return null;
        }
    }

    @Override
    public boolean connection(String IP, int port, GameType gameType) {
        try {
            InetAddress ip = InetAddress.getByName(IP);
            Socket socket = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            return false;
        }

        int type = 0;
        switch (gameType) {
            case SINGLE: type = RequestCodes.SINGLE_GAME; break;
            case VERSUS: type = RequestCodes.VERSUS_GAME; break;
            case VIEW: type = RequestCodes.VIEW_GAME; break;
            default: type = RequestCodes.SINGLE_GAME; break;
        }

        return sendRequestCode(type).responseCode == 200;
    }

    @Override
    public GameInfo disconnect(boolean isWait) {
        if (isWait) {
            return sendRequestCode(RequestCodes.DISCONNECT_WITH_RESULT);
        } else {
            sendRequestCode(RequestCodes.DISCONNECT_WITHOUT_RESULT);
            return null;
        }
    }

    @Override
    public GameInfo getInfoAboutLeftPlayer() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER);
        return checkResponseCode(gameInfo);
    }

    @Override
    public GameInfo getInfoAboutRightPlayer() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER);
        return checkResponseCode(gameInfo);
    }

    @Override
    public GameInfo getPlayingGameRoomList() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_LIST_OF_GAME_ROOM);
        return checkResponseCode(gameInfo);
    }

    @Override
    public boolean chooseGameRoom(int id) {
        if(sendRequestCode(RequestCodes.NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID).responseCode == 200) {
            return sendRequestCode(id).responseCode == 200;
        } else {
            return false;
        }
    }

    @Override
    public boolean toUp() {
        return sendRequestCode(RequestCodes.CMD_UP).responseCode == 200;
    }

    @Override
    public boolean toDown() {
        return sendRequestCode(RequestCodes.CMD_DOWN).responseCode == 200;
    }

    @Override
    public boolean toLeft() {
        return sendRequestCode(RequestCodes.CMD_LEFT).responseCode == 200;
    }

    @Override
    public boolean toRight() {
        return sendRequestCode(RequestCodes.CMD_RIGHT).responseCode == 200;
    }
}