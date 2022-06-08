import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Messenger {
    private ArrayList<ConnectedClient> clients;
    private ArrayList<String> messages;

    public Messenger(){
        this.clients = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
    public void addClient(ConnectedClient connectedClient){
        this.clients.add(connectedClient);
    }
    public void send(String message){
messages.add(message);
if(messages.size()>100){
    messages.remove(0);
}

        for(int i = 0; i<clients.size();i++){
            try {
                clients.get(i).getOut().write(message+ "\n");
                clients.get(i).getOut().flush();
            } catch (IOException e) {

                clients.remove(i);
                i--;
                e.printStackTrace();
            }
        }
        System.out.println("Clients online: "+ this.clients.size());
    }

    public ArrayList<String> getMessages() {
        return messages;
    }



    public void sendOnline(String name) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("requestType","sendOnline");
        jsonObject.put("userName",name);

        for(int i = 0; i<clients.size();i++){
            try {
                clients.get(i).getOut().write(jsonObject+ "\n");
                clients.get(i).getOut().flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public void sendOffline(String name) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("requestType","sendOffline");
        jsonObject.put("userName",name);

        for(int i = 0; i<clients.size();i++){
            try {
                clients.get(i).getOut().write(jsonObject+ "\n");
                clients.get(i).getOut().flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
