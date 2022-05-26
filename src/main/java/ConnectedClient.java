import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectedClient {
    private Socket clientSocket;
    private BufferedWriter out;

    public ConnectedClient(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedWriter getOut() {
        return out;
    }
}
