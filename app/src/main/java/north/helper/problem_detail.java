package north.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static north.helper.main_activity.con_list;
import static north.helper.user_detail.con_adapter;

public class problem_detail extends AppCompatActivity {

    private TextView pd_id;
    private TextView pd_date;
    private TextView pd_p;
    private TextView pd_d;
    private Button loc;
    private Button talk;
    private Toast toast;
    private String[] msg;
    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pro_detail);

        this.pd_id = findViewById(R.id.pd_id);
        this.pd_date = findViewById(R.id.pd_date);
        this.pd_p = findViewById(R.id.pd_p);
        this.pd_d = findViewById(R.id.pd_d);
        this.loc = findViewById(R.id.loc);
        this.talk = findViewById(R.id.p_talk);
        this.num = findViewById(R.id.view_num);

        Random r =new Random();
        int temp = r.nextInt(50)+50;

        this.num.setText(String.format("%s%s", "浏览量：", String.valueOf(temp)));

        this.msg = getIntent().getStringArrayExtra("msg");

        this.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(problem_detail.this, map_activity.class));
            }
        });

        this.talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!con_list.contains(pd_id)) {
                    con_list.add(pd_id.getText().toString());
                    con_adapter.notifyDataSetChanged();
                } else {
                    //start talk_activity
                }

                App myapp = (App) problem_detail.this.getApplication();
                if (myapp.is_logged_in) {
                    Intent i = new Intent(problem_detail.this, talk_activity.class);
                    i.putExtra("id", problem_detail.this.pd_id.getText().toString());
                    startActivity(i);
                }else {
                    if (problem_detail.this.toast == null) {
                        toast = Toast.makeText(problem_detail.this, "请先登陆！", Toast.LENGTH_SHORT);
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(problem_detail.this, "请先登陆！", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            }
        });

        this.set_text();

    }

    private void set_text() {
        this.pd_id.setText(msg[0]);
        this.pd_p.setText(msg[1]);
        this.pd_d.setText(msg[2]);
        this.pd_date.setText(msg[3]);
    }

}
