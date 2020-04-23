package north.helper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import north.other.MessageBox;


public class main_activity extends AppCompatActivity implements login_UI_change {

    public static String login_id = "";

    public static Socket chat_socket = new Socket();
    public static ObjectInputStream in;
    public static ObjectOutputStream out;


    public static ArrayList<String> con_list = new ArrayList<>();
    public static ArrayList<String> friends_list;
    public static ArrayList<String> myques_list = new ArrayList<>();

    public static HashMap<String, ArrayList<MessageBox>> msg_box;
    public static HashMap<String, ArrayList<MessageBox>> in_mb;

    private main_fragment ques_frag = new main_fragment();
    private user_fragment user_frag = new user_fragment(this);
    private user_detail u_detail = null;

    private RadioButton rb_left;
    private RadioButton rb_middle;
    private RadioButton rb_right;

    private RadioGroup rg;
    private Toast toast;
    private Button book;
    private Button web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_activity);

        this.rb_left = findViewById(R.id.rb_left);
        this.rb_middle = findViewById(R.id.rb_middle);
        this.rb_right = findViewById(R.id.rb_right);
        this.book = findViewById(R.id.book);
        this.web = findViewById(R.id.web_b);


        this.getFragment();
        this.ini_frags();

    }

    private void getFragment() {
        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ques_frag).commit();
    }

    private void ini_frags() {

        this.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_activity.this, web_activity.class));
            }
        });

        this.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_activity.this, dic_activity.class));
            }
        });

        this.rg = findViewById(R.id.rg);

        this.rb_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_activity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ques_frag).commit();
            }
        });


        this.rb_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App myapp = (App) main_activity.this.getApplication();
                if (myapp.is_logged_in) {
                    startActivity(new Intent(main_activity.this, upload_ques.class));
                } else {
                    main_activity.this.start_login_cover();
                    rg.check(R.id.rb_right);

                    if (main_activity.this.toast == null) {
                        toast = Toast.makeText(main_activity.this, "请先登陆！", Toast.LENGTH_SHORT);
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(main_activity.this, "请先登陆！", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            }
        });


        this.rb_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_activity.this.start_login_cover();
            }
        });
    }

    private void start_login_cover() {
        if (u_detail == null) {
            main_activity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, user_frag).commit();
        } else {
            main_activity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_detail()).commit();
        }

    }

    @Override
    public void change(ArrayList<String> fri, String login_id) {
        App myapp = (App) main_activity.this.getApplication();
        myapp.is_logged_in = true;
        this.friends_list = fri;
        this.login_id = login_id;
        msg_box = new HashMap<>();
        in_mb = new HashMap<>();
        u_detail = new user_detail();
        main_activity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, u_detail).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            in.close();
            out.close();
            chat_socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
