package es.hol.ecotiffins.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.History;

public class DatabaseHandler extends SQLiteOpenHelper {

    private String TABLE_HISTORY =  "history";

    private String KEY_ID       = "id";
    private String KEY_TYPE     = "type";
    private String KEY_ORDER_ID = "orderId";
    private String KEY_PRICE    = "price";
    private String KEY_DATE     = "date";

    private final static int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context ) {
        super(context, context.getResources().getString(R.string.app_name), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID		    + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + KEY_TYPE  		+ " TEXT , "
                + KEY_ORDER_ID		+ " TEXT UNIQUE, "
                + KEY_PRICE		    + " TEXT , "
                + KEY_DATE          + " TEXT ) ";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
    }

    public void insertData(ArrayList<History> data){
        SQLiteDatabase db = this.getWritableDatabase();
        for(History history : data) {
            ContentValues values = new ContentValues();
            values.put(KEY_TYPE, history.getTitle());
            values.put(KEY_ORDER_ID, history.getSubtitle());
            values.put(KEY_PRICE, history.getPrice());
            values.put(KEY_DATE, history.getDate());
            db.insert(TABLE_HISTORY,null,values);
        }
        db.close();
    }

    public ArrayList<History> getHistory(){
        ArrayList<History> histories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HISTORY;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                histories.add(new History(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return histories;
    }
}
