public class Main {
    public static void main(String[] args) {
        IPacManAPI pacManAPI = new PacManAPI();
        if (pacManAPI.connection("127.0.0.1", 8080, RequestCodes.CODE_NAME1)); {
            IBOT bot = new MyBot();
            bot.exec(pacManAPI);
        }
    }
}