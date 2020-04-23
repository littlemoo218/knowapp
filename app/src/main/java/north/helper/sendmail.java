package north.helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;



public class sendmail extends Thread {

    private final String SEND_CODE = "399a37da20427b7cfdfbf5394c9f4434";

    private String email;

    private final String host = "49.232.138.247";
    private final int login_port = 9990;
    private Inet4Address ip = null;
    private InetSocketAddress login_add = null;
    private Socket login_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private code_callback code_callback;

    public sendmail(String email, code_callback code_callback) {
        super();
        this.email = email;
        this.code_callback = code_callback;
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

            this.out.writeObject(SEND_CODE);
            this.out.flush();
            this.out.writeObject(email);
            out.flush();

            this.code_callback.set_code((int)this.in.readObject());


        } catch (Exception e) {

        }
    }


}
