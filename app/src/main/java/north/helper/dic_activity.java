package north.helper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class dic_activity extends AppCompatActivity {

    private final String db_name = "dic_db";

    private EditText e;
    private Button b;
    private Button m;
    private Toast toast;
    private TextView word_itself;
    private TextView word_exp;
    private TextView word_hint;
    private TextView tv_word;

    private My_DB_Helper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dic_activity);

        this.ini_db();

        this.e = findViewById(R.id.word);
        this.b = findViewById(R.id.ok);
        this.m = findViewById(R.id.modify_button);
        this.word_itself = findViewById(R.id.words_itself);
        this.word_exp = findViewById(R.id.words_exp);
        this.word_hint = findViewById(R.id.words_hint);
        this.tv_word = findViewById(R.id.tv_word);


        this.e.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (e.length() == 0) {
                    dic_activity.this.clear_text();
                }
                return false;
            }
        });

        /*
        this.tv_word.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(dic_activity.this, all_words_activity.class));
                return false;
            }
        });
        */


        this.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = dic_activity.this.e.getText().toString().trim().toLowerCase();
                if (!word.equals("")) {
                    dic_activity.this.query(word);

                    if(word.matches("saito_")){
                        egg();
                    }

                } else {
                    if (dic_activity.this.toast == null) {
                        toast = Toast.makeText(dic_activity.this, "Sorry, no word!", Toast.LENGTH_SHORT);
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(dic_activity.this, "Sorry, no word!", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            }
        });

        this.m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }// onCreate

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db_helper.close();
    }

    private void clear_text(){
        word_itself.setText("");
        word_exp.setText("");
        word_hint.setText("");
        dic_activity.this.m.setVisibility(View.INVISIBLE);
    }

    private void egg(){
        Log.e("1", "111");
    }

    private void ini_db() {
        db_helper = new My_DB_Helper(this, db_name);
    }

    private void query(String word) {
        SQLiteDatabase db = db_helper.getReadableDatabase();

        String[] columns = new String[]{"Word", "Explanation", "Hint"};
        Cursor c = db.query("Words", columns, "Word = '" + word + "'", null, null, null, null);
        if (c.getCount() == 0) {
            Intent i = new Intent(this, LogActivity.class);
            i.putExtra("nw", word);
            clear_text();
            startActivity(i);
        } else {
            c.moveToFirst();
            this.word_itself.setText(c.getString(c.getColumnIndex("Word")));
            this.word_exp.setText(String.format("%s%s", "[Exp:]   ", c.getString(c.getColumnIndex("Explanation"))));
            this.word_hint.setText(String.format("%s%s", "[Hint:]   ", c.getString(c.getColumnIndex("Hint"))));
            this.m.setVisibility(View.VISIBLE);
        }
        c.close();
        db.close();
    }// query



    private void modify() {

    }//modify

}
