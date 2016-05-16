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
    static String DATABASE_NAME = "prayerDB";

    //  Table Name
    private static final String TABLE_NAME = "prayer";

    //  fields for table

    public static final String ID = "id";
    public static final String PRAYER_TIMES = "name";
    public static final String PRAYER_NAMES = "salary";

    //  Table create

    public static final String
            TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY autoincrement, " + PRAYER_TIMES
            + " TEXT, " + PRAYER_NAMES + " TEXT)";


    //  required resources to manage database

    private ContentValues cValues;
    private Cursor cursor;
    private SQLiteDatabase database = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXIST "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
