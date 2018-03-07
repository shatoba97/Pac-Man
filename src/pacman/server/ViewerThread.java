package pacman.server;

public class ViewerThread extends Thread {

    private Player player;
    private ResponseBuilder responseBuilder;

    ViewerThread(Player player) {
        this.player = player;
        responseBuilder = ResponseBuilder.getInstance();
    }

    @Override
    public void run() {
        // Уведомление об успешном подключении к серверу
        player.sendResponse(responseBuilder.getOKResponse());
        while(true) {
            int request = player.getRequest();
            // todo
            // Обработка запроса зрителя
        }
    }
}