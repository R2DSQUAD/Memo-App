package com.example.sqlite_program.db;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite_program.MemoItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "Memo.db";
    public static final int DB_VERSION = 2;
    public static final String NOTE_INTENT = "note_intent";
    public static final String IS_NEW_NOTE_INTENT = "mode_intent";



    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS MemoList ( id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }




    // SELECT 문 (메모 목록들을 조회)
    public ArrayList<MemoItem> getMemoList() {
        ArrayList<MemoItem> memoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM MemoList ORDER BY writeDate DESC", null);
        if(cursor.getCount() !=0) {
            // 조회온 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                MemoItem memoItem = new MemoItem();
                memoItem.setId(id);
                memoItem.setTitle(title);
                memoItem.setContent(content);
                memoItem.setWriteDate(writeDate);
                memoItems.add(memoItem);

            }
        }
        cursor.close();

        return memoItems;
    }

    // SELECT 문 (메모를 조회)
    public MemoItem getMemo (int id) {
        MemoItem memoItem = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM MemoList WHERE id = " + id  + " ORDER BY writeDate DESC", null);
        if(cursor.getCount() !=0) {
            // 조회온 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {

                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                memoItem.setId(id);
                memoItem.setTitle(title);
                memoItem.setContent(content);
                memoItem.setWriteDate(writeDate);

            }
        }
        cursor.close();

        return memoItem;
    }

    // INSERT 문 (메모 목록을 DB에 넣는다.)
    public void InsertMemo(String _title, String _content, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO MemoList (title, content, writeDate) VALUES('" + _title + "','" + _content + "','" + _writeDate + "');");
    }

    // UPDATE 문 (메모 목록을 수정한다.)
    public void UpdateMemo(String _title, String _content, String _writeDate, String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MemoList SET title='" + _title + "', content='" + _content + "' , writeDate='" + _writeDate + "' WHERE writeDate='" + _beforeDate + "'");
    }

    // DELETE 문 (할일 목록을 제거 한다.)
    public void deleteMemo(String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MemoList WHERE writeDate = '" + _beforeDate + "'");
    }
}
