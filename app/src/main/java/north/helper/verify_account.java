package north.helper;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;


public class verify_account extends Thread {

    private final String NO_SUCH_USER = "1bfec375889456a00f9e76208df9f09d";
    private final String WRONG_PWD = "2f220fbd0cea24ae2f32f66436e5fe47";
    private final String SUCCESS = "d0749aaba8b833466dfcbb0428e4f89c";

    private final String host = "49.232.138.247";
    private final int chat_port = 9991;
    private Inet4Address ip = null;
    private InetSocketAddress chat_add = null;
    private Socket chat_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private String[] account = new String[2];
    private login_callback callback;

    public verify_account(String[] account, login_callback callback) {
        super();
        this.account[0] = account[0];
        this.account[1] = account[1];
        this.callback = callback;
    }

    @Override
    public void run() {
        super.run();
        ini_net();
        to_server();
    }

    private void ini_net() {
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.chat_add = new InetSocketAddress(this.ip, this.chat_port);
            this.chat_socket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void to_server() {
        try {
            this.chat_socket.connect(chat_add);
            this.out = new ObjectOutputStream(this.chat_socket.getOutputStream());
            this.out.flush();
            this.in = new ObjectInputStream(this.chat_socket.getInputStream());

            this.out.writeObject(account);
            String answer = (String) this.in.readObject();

            switch (answer) {
                case NO_SUCH_USER:
                    this.callback.on_no_user();
                    break;

                case WRONG_PWD:
                    this.callback.on_wrong_pwd();
                    break;

                case SUCCESS:
                    this.callback.on_success((ArrayList<String>) this.in.readObject(), this.account[0]);
                    break;
            }


        } catch (Exception e) {

        }
    }

}
