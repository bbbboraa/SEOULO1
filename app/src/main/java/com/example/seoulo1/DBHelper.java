package com.example.seoulo1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
public class DBHelper extends SQLiteOpenHelper {
    private final String ID = "id";
    private static final String DATABASE_NAME = "Check.db";
    private final String TABLE_NAME = "Table_content";
    private final String INFO = "info";
    private final String STATE = "state";
    public static final int DATABASE_VERSION = 1;

    private final String createQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INFO + " TEXT, "
            + STATE + " TEXT)";

    //생성자
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //    데이터 베이스 생성
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createQuery);
    }

    //    데이터베이스 업데이트
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor loadSQLiteDBCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        String selectQuery = "SELECT " + ID + ", " + INFO + ", " + STATE + " FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return cursor;
    }


    //데이터베이스에 값 삽입하는 함수
    public void insertContent(String info, String state) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase(); // 데이터베이스를 수정할 때는 getWritableDatabase(), 데이터를 읽을 떄는 getReadableDatabase()를 씁니다
        contentValues.put(INFO, info);
        contentValues.put(STATE, state);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateContent(String info, String state) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase(); // 데이터베이스를 수정할 때는 getWritableDatabase(), 데이터를 읽을 떄는 getReadableDatabase()를 씁니다
        contentValues.put(INFO, info);
        contentValues.put(STATE, state);
        db.update(TABLE_NAME, contentValues, "INFO=?", new String[]{info});
    }


    //데이터 삭제하기
    public int deleteData(String info) {
        SQLiteDatabase db = getWritableDatabase(); // 데이터베이스를 수정할 때는 getWritableDatabase(), 데이터를 읽을 떄는 getReadableDatabase()를 씁니다
        return db.delete(TABLE_NAME, "INFO = ?", new String[]{info});
    }

    // 데이터베이스에 값 일괄적으로 삽입
    public void loadContent(SQLiteDatabase db) {
    }

    public ArrayList SelectAll() {
        ArrayList all_item_db = new ArrayList<>();
        Cursor c = getWritableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                @SuppressLint("Range") String info = c.getString(c.getColumnIndex(INFO));
                @SuppressLint("Range") String state = c.getString(c.getColumnIndex(STATE));

                //HashMap에 넣습니다.
                //HashMap<String, String> item = new HashMap<String, String>();

                //item.put(INFO, info);
                //item.put(STATE, state);
                Log.d(TAG, info + "///select all////" + state);
                //ArrayList에 추가합니다..
                all_item_db.add(new PreparationItem(Boolean.valueOf(state), info));
                //all_item_db.add(new HashMap(c.getString(c.getColumnIndex(INFO)),c.getString(c.getColumnIndex(STATE)));
            } while (c.moveToNext());

        }
        return all_item_db;
    }
}