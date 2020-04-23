package north.helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class upload_thread extends Thread {


    private final String host = "49.232.138.247";
    private final int que_port = 9994;
    private Inet4Address ip = null;
    private InetSocketAddress que_add = null;
    private Socket que_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    //private short choice;
    //private String[] ques_box;
    private HashMap<Integer, String[]> question = new HashMap<>();


    public upload_thread(int choice, String[] ques_box) {
        //this.choice = choice;
        //this.ques_box = ques_box;
        question.put(choice, ques_box);
    }

    @Override
    public void run() {
        this.initialize_network();
        this.upload();
    }

    private void initialize_network() {
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.que_add = new InetSocketAddress(this.ip, this.que_port);
            this.que_socket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload() {
        try {
            this.que_socket.connect(que_add);
            this.out = new ObjectOutputStream(this.que_socket.getOutputStream());
            this.out.flush();
            this.in = new ObjectInputStream(this.que_socket.getInputStream());
            this.out.writeObject(question);


            this.out.close();
            this.in.close();
            this.que_socket.close();

        } catch (Exception e) {

        }
    }


}
