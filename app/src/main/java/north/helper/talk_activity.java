package north.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import north.other.MessageBox;

import static north.helper.main_activity.in_mb;
import static north.helper.main_activity.msg_box;

public class talk_activity extends AppCompatActivity {

    private String talk_to = "";
    public LinearLayout talk_layout;
    private TextView tv_talk;

    private Handler h;
    private in_update iu;
    private EditText e;



    @Override
    protected void onStart() {
        super.onStart();
        in_mb.put(talk_to, new ArrayList<MessageBox>());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        in_mb.remove(talk_to);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.talk_to = getIntent().getStringExtra("id");
        this.setContentView(R.layout.talk_activity);
        this.talk_layout = findViewById(R.id.talk);
        this.e = findViewById(R.id.et_write_msg);
        this.tv_talk = findViewById(R.id.tv_talk);


        this.tv_talk.setText(String.format("%s%s%s", "和 ", talk_to, " 对话中..."));

        this.append_msg();

        this.h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        MessageBox temp = (MessageBox) msg.obj;
                        append_text_o(temp);
                        break;

                }
            }
        };

        this.iu = new in_update(this.h, talk_to);
        iu.start();


        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = e.getText().toString().trim();
                if (msg.length() != 0) {
                    new send_msg(main_activity.login_id, talk_to, msg).start();
                    talk_activity.this.append_text(msg);

                    e.setText("");

                } else {
                    e.setText("");
                    Toast.makeText(talk_activity.this, "无内容！", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void append_text(String msg) {
        LinearLayout sentence = (LinearLayout) View.inflate(talk_activity.this, R.layout.talk_item, null);
        TextView date = sentence.findViewById(R.id.talk_date);
        TextView message = sentence.findViewById(R.id.talk_msg);
        date.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        message.setText(msg);
        talk_layout.addView(sentence);
    }

    private void append_text_o(MessageBox temp){
        LinearLayout sentence = (LinearLayout) View.inflate(talk_activity.this, R.layout.talk_item_other, null);
        TextView date = sentence.findViewById(R.id.talk_date);
        TextView message = sentence.findViewById(R.id.talk_msg);
        date.setText(temp.date);
        message.setText(temp.str_msg);
        talk_layout.addView(sentence);
    }


    public void append_msg() {

        if (msg_box.containsKey(talk_to)) {
            for (int i = 1; i <= msg_box.get(talk_to).size(); i++) {
                MessageBox temp = msg_box.get(talk_to).get((i - 1));

                if (temp.sender.matches(talk_to)) {
                    LinearLayout sentence = (LinearLayout) View.inflate(talk_activity.this, R.layout.talk_item_other, null);
                    TextView date = sentence.findViewById(R.id.talk_date);
                    TextView message = sentence.findViewById(R.id.talk_msg);
                    date.setText(temp.date);
                    message.setText(temp.str_msg);
                    talk_layout.addView(sentence);

                } else {
                    LinearLayout sentence = (LinearLayout) View.inflate(talk_activity.this, R.layout.talk_item, null);
                    TextView date = sentence.findViewById(R.id.talk_date);
                    TextView message = sentence.findViewById(R.id.talk_msg);
                    date.setText(temp.date);
                    message.setText(temp.str_msg);
                    talk_layout.addView(sentence);
                }
            }
        } else {

        }
    }


}

