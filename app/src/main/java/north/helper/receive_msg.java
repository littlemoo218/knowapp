package north.helper;

import android.os.Message;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import android.os.Handler;

import north.other.MessageBox;

import static north.helper.main_activity.chat_socket;
import static north.helper.main_activity.msg_box;
import static north.helper.main_activity.in_mb;
import static north.helper.main_activity.out;
import static north.helper.main_activity.in;


public class receive_msg extends Thread {

    private final String host = "49.232.138.247";
    private final int chat_port = 9993;
    private Inet4Address ip = null;
    private InetSocketAddress chat_add = null;
    private Handler h;


    public receive_msg(Handler h) {
        super();
        this.h = h;
    }

    @Override
    public void run() {
        super.run();
        ini_net();
        process();
    }

    private void ini_net() {
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.chat_add = new InetSocketAddress(this.ip, this.chat_port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process() {
        try {
            chat_socket.connect(chat_add);
            out = new ObjectOutputStream(chat_socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(chat_socket.getInputStream());

            out.writeObject(main_activity.login_id);
            out.flush();

            while (true) {
                MessageBox temp = (MessageBox) in.readObject();
                Message message = new Message();
                message.what = 1;
                message.obj = temp;

                this.h.sendMessage(message);

                if (msg_box.containsKey(temp.sender)) {
                    msg_box.get(temp.sender).add(temp);
                } else {
                    msg_box.put(temp.sender, new ArrayList<MessageBox>());
                    msg_box.get(temp.sender).add(temp);
                }

                if (in_mb.containsKey(temp.sender)){
                    in_mb.get(temp.sender).add(temp);
                }
            }

        } catch (Exception e) {

        }
    }


}
