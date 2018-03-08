package team.innovation.news.business;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：业务类，网络工具
 */
public class NetworkUtil {
    private NetworkUtil(){}

    /**
     * 获取图片
     * @param url 图片链接
     * @return 图片
     */
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

    /**
     * 获取文本
     * @param url 链接
     * @return 文本
     */
    public static String getText(String url) {
        try {
            Log.e("url", url);
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            InputStream inputStream = connection.getInputStream();
            StringBuilder builder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
