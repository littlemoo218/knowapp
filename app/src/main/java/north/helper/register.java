package north.helper;

import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity implements code_callback, add_new_user_callback {

    private int code = -1;

    private EditText email;
    private EditText et_code;
    private Button send_code;
    private Button upload;
    private EditText newid;
    private EditText pwd;
    private EditText repwd;



    private String[] id_pwd = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.et_mail);
        et_code = findViewById(R.id.et_code);
        send_code = findViewById(R.id.b_code);
        upload = findViewById(R.id.b_upload);
        newid = findViewById(R.id.et_newid);
        pwd = findViewById(R.id.et_newpwd);
        repwd = findViewById(R.id.et_re_newpwd);


        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.length() != 0) {
                    new sendmail(email.getText().toString(), register.this).start();
                    lock_button();
                }
                else {
                    Toast.makeText(register.this, "请填写电子邮件！", Toast.LENGTH_SHORT).show();

                }

            }
        });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.length()!=0&&newid.length()!=0&&pwd.length()!=0&&repwd.length()!=0&&et_code.length()!=0){
                    if(pwd.getText().toString().matches(repwd.getText().toString())){
                        if (code == Integer.parseInt(et_code.getText().toString())) {
                            id_pwd[0]=newid.getText().toString().trim();
                            id_pwd[1]=pwd.getText().toString().trim();
                            id_pwd[2]=email.getText().toString().trim();

                            new new_user_to_server(id_pwd, register.this).start();
                        }
                        else{
                            Toast.makeText(register.this, "验证码错误！请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(register.this, "两次密码不相同！", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(register.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void lock_button(){
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                send_code.setEnabled(false);
                send_code.setText("已发送(" + millisUntilFinished / 1000 + ")");

            }

            @Override
            public void onFinish() {
                send_code.setEnabled(true);
                send_code.setText("重新发送");

            }
        }.start();
    }


    @Override
    public void set_code(int code) {
        this.code = code;
    }

    @Override
    public void new_user_success() {
        Looper.prepare();
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
        Looper.loop();
    }

    @Override
    public void user_exist() {
        Looper.prepare();
        Toast.makeText(this, "账号已存在，换一个试试？", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

}
