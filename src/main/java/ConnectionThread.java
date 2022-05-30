import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ConnectionThread extends Thread{

    private ConnectedClient connectedClient;
    private BufferedReader in;
    private BufferedWriter out;

    private Messenger messenger;

    ConnectionThread(ConnectedClient connectedClient, Messenger messenger){
        this.connectedClient = connectedClient;
this.messenger = messenger;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(connectedClient.getClientSocket().getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(connectedClient.getClientSocket().getOutputStream()));
            messenger.addClient(connectedClient);
        } catch (IOException e) {
            e.printStackTrace();
        }



        while (connectedClient.getClientSocket().isConnected() ){
            String word = null;
            try {
                word = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (word != null) {
                System.out.println("Запрос от клиента: "+word);
                try{
                    JSONObject jsonObject = new JSONObject(word);
                    switch (jsonObject.getString("requestType")){
                        case ("sendMessage"): messenger.send(word);
                        break;
                        case ("getLastMessages"): sendLastMessages();
                        break;
                        case ("ping"):sendPong();
                    }




                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                }
            }
        System.out.println("Client is offline");
        }

    private void sendPong() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestType","pong");
        try {
            this.connectedClient.getOut().write(jsonObject.toString()+"\n");
            this.connectedClient.getOut().flush();
            System.out.println("Sending pong");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendLastMessages() {
        ArrayList<String> messages = messenger.getMessages();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestType","lastMessages");
        if(messages.size()<100){
            jsonObject.put("lastMessagesCount", messages.size());
        }
        else {
            jsonObject.put("lastMessagesCount", 100);
        }
        for(int i = 0; i<messages.size();i++){
            JSONObject object = new JSONObject(messages.get(i));
            jsonObject.put("name"+Integer.toString(i),object.getString("userName"));
            jsonObject.put("message"+Integer.toString(i),object.getString("message"));
        }
        try {
            this.connectedClient.getOut().write(jsonObject.toString()+"\n");
            this.connectedClient.getOut().flush();
            System.out.println("Sending last messages");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
