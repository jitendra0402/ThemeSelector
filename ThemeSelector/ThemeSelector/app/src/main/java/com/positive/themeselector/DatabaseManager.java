package com.positive.themeselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = DatabaseManager.class.getSimpleName();
    private static DatabaseManager sInstance;

    private final static String DATABASE_NAME = "mbit";
    private final static int DATABASE_VERSION = 1;

    private static final String TABLE_THEMES = "tbl_themes";

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_THEMES + "(id INTEGER,theme_name TEXT,gameobjectName TEXT,thumb_url TEXT,thumb_path TEXT,sound_url TEXT,sound_path TEXT,lyrics TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THEMES);
        onCreate(db);
    }

    public void addMusic(int id, String theme_name, String gameobjectName, String thumb_url, String thumb_path, String sound_url, String sound_path,String lyrics) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("theme_name", theme_name);
            values.put("gameobjectName", gameobjectName);
            values.put("thumb_url", thumb_url);
            values.put("thumb_path", thumb_path);
            values.put("sound_url", sound_url);
            values.put("sound_path", sound_path);
            values.put("lyrics", lyrics);

            db.insertOrThrow(TABLE_THEMES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public boolean checkTheme(int id) {
        boolean isCheck = false;
        String CROPS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE id = %s",
                        TABLE_THEMES, id);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CROPS_SELECT_QUERY, null);
        try {
            isCheck = cursor.moveToFirst();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return isCheck;
    }

    public String GetMusicFileById(int id) {
        String filepath = "";
        String CROPS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE id = %s",
                        TABLE_THEMES, id);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CROPS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                filepath = cursor.getString(cursor.getColumnIndex("sound_path"));
            } else
                filepath = "";
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return filepath;
    }

    public String GetThumbFileById(int id) {
        String filepath = "";
        String CROPS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE id = %s",
                        TABLE_THEMES, id);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CROPS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                filepath = cursor.getString(cursor.getColumnIndex("thumb_path"));
            } else
                filepath = "";
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return filepath;
    }


}
