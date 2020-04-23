package north.helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;



public class new_user_to_server extends Thread {

    private final String ADD_NEW_USER = "f03bb1951d4ceb76a8d82487017a931c";
    private final String ADD_SUCCESS = "e55c44fbd41784b7731f71e2789cad65";
    private final String USER_ALREADY_EXIST = "5616734680340e61424747919a29abfe";

    private final String host = "49.232.138.247";
    private final int login_port = 9990;
    private Inet4Address ip = null;
    private InetSocketAddress login_add = null;
    private Socket login_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private String[] id_pwd = new String[3];

    public add_new_user_callback new_user_callback;


    public new_user_to_server(String[] id_pwd, add_new_user_callback new_user_callback) {
        super();
        this.id_pwd[0] = id_pwd[0];
        this.id_pwd[1] = id_pwd[1];
        this.id_pwd[2] = id_pwd[2];

        this.new_user_callback = new_user_callback;
    }

    private void ini(){
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.login_add = new InetSocketAddress(this.ip, this.login_port);
            this.login_socket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        ini();
        to_server();
    }

    private void to_server(){
        try {
            this.login_socket.connect(login_add);
            this.out = new ObjectOutputStream(this.login_socket.getOutputStream());
            this.out.flush();
            this.in = new ObjectInputStream(this.login_socket.getInputStream());

            this.out.writeObject(ADD_NEW_USER);
            this.out.flush();
            this.out.writeObject(id_pwd);
            out.flush();

            String ans = (String) this.in.readObject();

            if(ans.matches(ADD_SUCCESS)){
                this.new_user_callback.new_user_success();
            }
            else if(ans.matches(USER_ALREADY_EXIST)){
                this.new_user_callback.user_exist();
            }



        } catch (Exception e) {

        }
    }


}
