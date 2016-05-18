package promo.letspray.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import promo.letspray.Model.Prayer;

/**
 * Created by wali on 5/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    //  Database name
    static String DATABASE_NAME = "prayerDB";
    SQLiteDatabase db;

    //  Table Name
    private static final String TABLE_NAME = "prayer";

    //  fields for table

    public static final String ID = "id";
    public static final String COLUMN_PRAYER_NAMES = "prayer_time";
    public static final String COLUMN_PRAYER_TIMES = "prayer_name";

    //  Table create

    public static final String
            TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY autoincrement, " + COLUMN_PRAYER_NAMES
            + " TEXT, " + COLUMN_PRAYER_TIMES + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXIST " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }

    public void insertPrayer(ArrayList<Prayer> prayers) {
        deletePreviousData();
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Prayer prayer :
                prayers) {
            values.put(COLUMN_PRAYER_NAMES, prayer.getPrayerName());
            values.put(COLUMN_PRAYER_TIMES, prayer.getPrayerTime());
            db.insert(TABLE_NAME, null, values);
        }
        long count = db.insert(TABLE_NAME, null, values);
        Log.e("INFO", "One rowinserter" + count);
        db.close();
    }


    public ArrayList<Prayer> getPrayer() {
        ArrayList<Prayer> contactList = new ArrayList<Prayer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prayer prayer = new Prayer();
                prayer.setPrayerName(cursor.getString(1));
                prayer.setPrayerTime(cursor.getString(2));
                // Adding contact to list
                contactList.add(prayer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void deletePreviousData(){
        db=getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
