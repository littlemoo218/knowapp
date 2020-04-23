package north.helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class get_tables extends Thread {

    private final String ALL_TABLE = "9694a73102fdaa177fdb91dcff4bf661";

    private final String host = "49.232.138.247";
    private final int sql_port = 9992;
    private Inet4Address ip = null;
    private InetSocketAddress sql_add = null;
    private Socket sql_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    public HashMap<String, ArrayList<String[]>> all_questions;


    public get_tables() {
    }

    @Override
    public void run() {
        this.initialize_network();
        get_sqldate(ALL_TABLE);
    }

    private void initialize_network() {
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.sql_add = new InetSocketAddress(this.ip, this.sql_port);
            this.sql_socket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get_sqldate(String type) {
        try {
            this.sql_socket.connect(sql_add);
            this.out = new ObjectOutputStream(this.sql_socket.getOutputStream());
            this.out.flush();
            this.in = new ObjectInputStream(this.sql_socket.getInputStream());
            this.out.writeObject(type);

            main_fragment.all_questions = (HashMap<String, ArrayList<String[]>>) this.in.readObject();

            this.out.close();
            this.in.close();
            this.sql_socket.close();

        } catch (Exception e) {

        }
    }


}
