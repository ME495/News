package team.innovation.news.business;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：
 */
public class NetworkUtil {
    private NetworkUtil(){}

    public static Bitmap getBitmap(String url) {
        try {
            Log.e("url", url);
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
