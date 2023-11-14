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
        sqLiteDatabase.execSQL(createFavoritesQuery); // 추가된 부분
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


    private final String FAVORITES_TABLE = "favorites_table";
    private final String FAVORITES_ID = "favorites_id";
    private final String FAVORITES_CATEGORY_NAME = "favorites_category_name";
    private final String FAVORITES_NAME = "favorites_name";
    private final String FAVORITES_PLACEID = "favorites_placeId";
    private final String FAVORITES_PNUM = "favorites_pNum";
    private final String FAVORITES_DISTANCE = "favorites_distance";
    private final String FAVORITES_VICINITY = "favorites_vicinity";
    private final String FAVORITES_OPENNOW = "favorites_open_now";
    private final String FAVORITES_RATING = "favorites_rating";
    private final String FAVORITES_LAT = "favorites_lat";
    private final String FAVORITES_LNG = "favorites_lng";
    private final String FAVORITES_STATUS = "favorites_status";

    private final String createFavoritesQuery = "CREATE TABLE IF NOT EXISTS " + FAVORITES_TABLE + "(" +
            FAVORITES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FAVORITES_CATEGORY_NAME + " TEXT, " +
            FAVORITES_NAME + " TEXT," + FAVORITES_PLACEID + " TEXT, "+
            FAVORITES_PNUM + " TEXT," +
            FAVORITES_DISTANCE + " INTEGER DEFAULT 0, " +
            FAVORITES_VICINITY + " TEXT, "+
            FAVORITES_OPENNOW + " TEXT, "+
            FAVORITES_RATING + " TEXT, "+FAVORITES_LAT + " DOUBLE DEFAULT 0, "+ FAVORITES_LNG + " DOUBLE DEFAULT 0, " + FAVORITES_STATUS + " TEXT) " ;


    // 추가된 메서드
    public void insertFavorite(String favorites_category_name, String favorites_name, String favorites_placeId, String favorites_pNum, int favorites_distance, String favorites_vicinity, String favorites_open_now, String favorites_rating, Double favorites_lat, Double favorites_lng, Boolean favorites_status) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        contentValues.put(FAVORITES_CATEGORY_NAME, favorites_category_name);
        contentValues.put(FAVORITES_NAME, favorites_name);
        contentValues.put(FAVORITES_PLACEID, favorites_placeId);
        contentValues.put(FAVORITES_PNUM, favorites_pNum);
        contentValues.put(FAVORITES_DISTANCE, favorites_distance);
        contentValues.put(FAVORITES_VICINITY, favorites_vicinity);
        contentValues.put(FAVORITES_OPENNOW, favorites_open_now);
        contentValues.put(FAVORITES_RATING, favorites_rating);
        contentValues.put(FAVORITES_LAT, favorites_lat);
        contentValues.put(FAVORITES_LNG, favorites_lng);
        contentValues.put(FAVORITES_STATUS, favorites_status);
        db.insert(FAVORITES_TABLE, null, contentValues);
    }

    // 추가된 메서드
    public ArrayList<LocationItem> selectAllFavorites() {
        ArrayList<LocationItem> allFavorites = new ArrayList<>();
        Cursor c = getWritableDatabase().rawQuery("select * from " + FAVORITES_TABLE, null);
        if (c != null && c.moveToFirst()) {
            try{
            do {
                @SuppressLint("Range") String favorites_category_name= c.getString(c.getColumnIndex(FAVORITES_CATEGORY_NAME));;
                @SuppressLint("Range") String favorites_name= c.getString(c.getColumnIndex(FAVORITES_CATEGORY_NAME));
                @SuppressLint("Range") String favorites_placeId= c.getString(c.getColumnIndex(FAVORITES_PLACEID));
                @SuppressLint("Range") String favorites_pNum= c.getString(c.getColumnIndex(FAVORITES_PNUM));
                @SuppressLint("Range") int favorites_distance= c.getInt(c.getColumnIndex(FAVORITES_DISTANCE));
                @SuppressLint("Range") String favorites_vicinity= c.getString(c.getColumnIndex(FAVORITES_VICINITY));
                @SuppressLint("Range") String favorites_open_now= c.getString(c.getColumnIndex(FAVORITES_OPENNOW));
                @SuppressLint("Range") String favorites_rating= c.getString(c.getColumnIndex(FAVORITES_RATING));
                @SuppressLint("Range") double favorites_lat= c.getDouble(c.getColumnIndex(FAVORITES_LAT));
                @SuppressLint("Range") double favorites_lng= c.getDouble(c.getColumnIndex(FAVORITES_LNG));
                @SuppressLint("Range") boolean favorites_status= Boolean.parseBoolean(c.getString(c.getColumnIndex(FAVORITES_STATUS)));
                Log.d(TAG, favorites_name + "///select all favorites////" + favorites_open_now);

                allFavorites.add(new LocationItem(favorites_placeId, favorites_name, favorites_category_name,favorites_vicinity, favorites_distance, favorites_pNum, favorites_open_now, favorites_rating, favorites_lat, favorites_lng,favorites_status));

            } while (c.moveToNext());}
            finally {
                c.close();
            }
        }
        return allFavorites;
    }

}