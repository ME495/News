package team.innovation.news.entity;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.graphics.Bitmap;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：实体类，新闻内容
 */
public class NewsContent {
    private String title, desc, link;
    private Bitmap bitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
