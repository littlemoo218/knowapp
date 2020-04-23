package north.helper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static north.helper.main_activity.login_id;


public class upload_ques extends AppCompatActivity {

    private RadioButton signal;
    private RadioButton vehicle;
    private RadioButton engineering;
    private Button b;
    private EditText et_sub;
    private EditText et_cont;
    private int choice = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ques);

        this.signal = findViewById(R.id.signal);
        this.vehicle = findViewById(R.id.vehicle);
        this.engineering = findViewById(R.id.engineering);
        this.b = findViewById(R.id.up_b);
        this.et_sub = findViewById(R.id.up_sub);
        this.et_cont = findViewById(R.id.up_content);

        this.signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_color();
                upload_ques.this.signal.setTextColor(getColor(R.color.checked));
                choice = 1;
            }
        });

        this.vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_color();
                upload_ques.this.vehicle.setTextColor(getColor(R.color.checked));
                choice = 2;
            }
        });

        this.engineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_color();
                upload_ques.this.engineering.setTextColor(getColor(R.color.checked));
                choice = 3;
            }
        });

        this.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = upload_ques.this.et_sub.getText().toString().trim();
                String cont = upload_ques.this.et_cont.getText().toString().trim();
                String[] ques_box = new String[4];
                ques_box[0] = login_id;
                ques_box[1] = sub;
                ques_box[2] = cont;
                ques_box[3] = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

                if ((!sub.equals("")) && (!cont.equals(""))) {
                    switch (choice) {
                        case 1:
                            new upload_thread(choice, ques_box).start();
                            finish();
                            break;
                        case 2:
                            new upload_thread(choice, ques_box).start();
                            finish();
                            break;
                        case 3:
                            new upload_thread(choice, ques_box).start();
                            finish();
                            break;
                        default:
                            if (upload_ques.this.toast == null) {
                                toast = Toast.makeText(upload_ques.this, "请选择问题类型！", Toast.LENGTH_SHORT);
                            } else {
                                toast.cancel();
                                toast = Toast.makeText(upload_ques.this, "请选择问题类型！", Toast.LENGTH_SHORT);
                            }
                            toast.show();
                            finish();
                            break;
                    }
                } else {
                    if (upload_ques.this.toast == null) {
                        toast = Toast.makeText(upload_ques.this, "请填写完整信息！", Toast.LENGTH_SHORT);
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(upload_ques.this, "请填写完整信息！", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }


            }
        });


    }


    private void clear_color() {
        this.signal.setTextColor(getColor(R.color.unchecked));
        this.vehicle.setTextColor(getColor(R.color.unchecked));
        this.engineering.setTextColor(getColor(R.color.unchecked));

    }

}
