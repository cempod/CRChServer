import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectedClient {

    private String username;
    private Socket clientSocket;
    private BufferedWriter out;

    public ConnectedClient(Socket clientSocket){
        this.username = "undefined";
        this.clientSocket = clientSocket;
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedWriter getOut() {
        return out;
    }
}
