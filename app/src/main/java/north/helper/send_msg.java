package north.helper;

import java.util.ArrayList;

import north.other.MessageBox;

import static north.helper.main_activity.msg_box;
import static north.helper.main_activity.out;

public class send_msg extends Thread {

    private String sender = "";
    private String receiver = "";
    private String msg = "";

    public send_msg(String sender, String receiver, String msg) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }

    @Override
    public void run() {
        super.run();
        process();
    }

    private void process() {
        try {
            MessageBox temp =new MessageBox(sender, receiver, msg);

            if (msg_box.containsKey(temp.receiver)) {
                msg_box.get(temp.receiver).add(temp);
            } else {
                msg_box.put(temp.receiver, new ArrayList<MessageBox>());
                msg_box.get(temp.receiver).add(temp);
            }


            out.writeObject(temp);
            out.flush();

        } catch (Exception e) {

        }
    }



}
