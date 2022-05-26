import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CRChServer {

    private static ServerSocket server; // серверсокет



    public static void main(String[] args) {
        Socket clientSocket;
        ConnectedClient client;
        BufferedReader in; // поток чтения из сокета
        BufferedWriter out; // поток записи в сокет
        Messenger messenger = new Messenger();

        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                //   объявить о своем запуске
                while (!server.isClosed()) {
                    clientSocket = server.accept(); // accept() будет ждать пока
                    client = new ConnectedClient(clientSocket);

                    new ConnectionThread(client, messenger).start();
                    //кто-нибудь не захочет подключиться
                    System.out.println("Подключен клиент");
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
