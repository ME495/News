package team.innovation.news.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import team.innovation.news.entity.NewsContent;

/**
 * Created by ME495 on 2018/3/8.
 */
/**
 * 作者：程坚
 * 时间：2018/3/8
 * 描述：数据库帮助类
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String FILENAME = "ContentStar1.db";
    public static final String CREATE_CONTENT = "create table Content ("
            + "link text primary key,"
            + "title text,"
            + "desc text,"
            + "imageUrl text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isExist(NewsContent newsContent) {
        SQLiteDatabase db = getWritableDatabase();
        if (newsContent == null) Log.e("newsContent","null");
        Cursor cursor = db.query("Content", new String[]{"link"}, "link=?", new String[]{newsContent.getLink()}, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void insertNewsContent(NewsContent newsContent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", newsContent.getTitle());
        values.put("desc", newsContent.getDesc());
        values.put("imageUrl", newsContent.getImageUrl());
        values.put("link", newsContent.getLink());
        db.insert("Content", null, values);
    }

    public void deleteNewsContent(NewsContent newsContent) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Content", "link=?", new String[]{newsContent.getLink()});
    }
}
