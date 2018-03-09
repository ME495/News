package team.innovation.news.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

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

    //数据库文件名
    public static final String FILENAME = "ContentStar1.db";

    //创建表的sql语句
    public static final String CREATE_CONTENT = "create table Content ("
            + "link text primary key,"
            + "title text,"
            + "desc text,"
            + "imageUrl text)";

    //link的缓存
    private static HashSet<String> linkSet = null;

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

    /**
     * 从数据库中读取数据初始化link缓存
     */
    public void initLinkSet() {
        ArrayList<NewsContent> list = getNewsContents();
        linkSet = new HashSet<>();
        for (NewsContent newsContent : list) {
            linkSet.add(newsContent.getLink());
        }
    }

    /**
     * 判断newsContent是否被收藏
     * @param newsContent
     * @return true表示被收藏，false表示没有被收藏
     */
    public boolean isExist(NewsContent newsContent) {
        if (linkSet != null) {
            //从缓存中获取数据
            if (linkSet.contains(newsContent.getLink())) {
                return true;
            } else {
                return false;
            }
        }
        //从数据库中获取数据
        SQLiteDatabase db = getWritableDatabase();
        if (newsContent == null) Log.e("newsContent","null");
        Cursor cursor = db.query("Content", new String[]{"link"}, "link=?", new String[]{newsContent.getLink()}, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从数据库中获取所有被收藏的新闻内容
     * @return 被收藏的新闻内容列表
     */
    public ArrayList<NewsContent> getNewsContents() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Content", null, null, null, null, null, null);
        ArrayList<NewsContent> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NewsContent newsContent = new NewsContent();
            newsContent.setLink(cursor.getString(0));
            newsContent.setTitle(cursor.getString(1));
            newsContent.setDesc(cursor.getString(2));
            newsContent.setImageUrl(cursor.getString(3));
            list.add(newsContent);
        }
        return list;
    }

    /**
     * 向数据库中插入新闻内容
     * @param newsContent
     */
    public void insertNewsContent(NewsContent newsContent) {
        if (linkSet != null) {
            linkSet.add(newsContent.getLink());
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", newsContent.getTitle());
        values.put("desc", newsContent.getDesc());
        values.put("imageUrl", newsContent.getImageUrl());
        values.put("link", newsContent.getLink());
        db.insert("Content", null, values);
        Log.e("insert", values.getAsString("title") + " " + values.getAsString("link"));
    }

    /**
     * 从数据库中删除新闻内容
     * @param newsContent
     */
    public void deleteNewsContent(NewsContent newsContent) {
        if (linkSet != null) {
            linkSet.remove(newsContent.getLink());
        }
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Content", "link=?", new String[]{newsContent.getLink()});
        Log.e("delete", newsContent.getTitle() + " " + newsContent.getLink());
    }
}
