package north.helper;

import android.os.Handler;
import android.os.Message;

import north.other.MessageBox;

import static north.helper.main_activity.in_mb;

public class in_update extends Thread {
    private Handler h;
    private String talk_to;

    public in_update(Handler h, String t) {
        super();
        this.h = h;
        this.talk_to = t;
    }

    @Override
    public void run() {
        super.run();

        while (true){
            if (in_mb.containsKey(talk_to)){
                if (in_mb.get(talk_to).size()!=0){
                    MessageBox temp = in_mb.get(talk_to).get(0);

                    Message message = new Message();
                    message.what = 1;
                    message.obj = temp;

                    this.h.sendMessage(message);

                    in_mb.get(talk_to).remove(0);
                }
            }
        }

    }
}
