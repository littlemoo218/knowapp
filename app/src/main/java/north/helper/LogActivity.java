package north.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    private TextView new_word;
    private EditText nw_exp;
    private EditText nw_hint;
    private Button log;

    private Toast toast;
    private My_DB_Helper db_helper;

    private String nw;
    private final String db_name = "dic_db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ini_db();

        this.new_word = findViewById(R.id.new_word);
        this.nw_exp = findViewById(R.id.nw_exp);
        this.nw_hint = findViewById(R.id.nw_hint);
        this.log = findViewById(R.id.log_button);

        this.nw = getIntent().getStringExtra("nw");
        this.new_word.setText(nw);

        this.log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp = nw_exp.getText().toString().trim();
                String hint = nw_hint.getText().toString().trim();

                if (!(!exp.equals("") && !hint.equals(""))) {
                    if (LogActivity.this.toast == null) {
                        toast = Toast.makeText(LogActivity.this, "Please fill all the blank!", Toast.LENGTH_SHORT);
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(LogActivity.this, "Please fill all the blank!", Toast.LENGTH_SHORT);

                    }
                    toast.show();
                } else {
                    String[] word_bag = new String[3];
                    word_bag[0] = nw;
                    word_bag[1] = exp;
                    word_bag[2] = hint;
                    LogActivity.this.insert(word_bag);
                }
            }
        });

    }//onCreate

    private void ini_db() {
        db_helper = new My_DB_Helper(this, db_name);
    }

    private void insert(String[] word_bag) {

        SQLiteDatabase db = db_helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Word", word_bag[0]);
        values.put("Explanation", word_bag[1]);
        values.put("Hint", word_bag[2]);
        db.insert("Words", null, values);
        db.close();
        this.finish();

    }//insert


}
