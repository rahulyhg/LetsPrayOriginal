package promo.letspray.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wali on 5/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION =1;

    //  Database name
    static String DATABASE_NAME = "database_name";

    //  Table Name
    public static final String TABLE_NAME = "table_name";

    //  fields for table

    //  required resources to manage database

    private ContentValues cValues;
    private Cursor cursor;
    private SQLiteDatabase database = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXIST "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
