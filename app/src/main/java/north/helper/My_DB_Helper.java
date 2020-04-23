package north.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class My_DB_Helper extends SQLiteOpenHelper {

    private static Integer DB_VERSION = 1;


    public My_DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public My_DB_Helper(Context context, String name) {
        this(context, name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Words(Word varchar(30) primary key, Explanation text, Hint text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
