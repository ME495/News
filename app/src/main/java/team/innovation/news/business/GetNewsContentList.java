package team.innovation.news.business;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.graphics.Bitmap;
import android.util.Log;

import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import team.innovation.news.entity.NewsContent;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：获取新闻内容列表
 */
public class GetNewsContentList {
    private GetNewsContentList(){}

    /**
     * 获取新闻内容列表
     * @param channelId 频道名称
     * @return 新闻内容列表
     */
    public static ArrayList<NewsContent> getList(String channelId){
//        String text = new ShowApiRequest("http://route.showapi.com/109-35", "58465", "76fd044d1ae74ea0bff5c000500d594d")
//                .addTextPara("channelId","")
//                .addTextPara("channelName",channelName)
//                .addTextPara("title","")
//                .addTextPara("page","1")
//                .addTextPara("needContent","0")
//                .addTextPara("needHtml","0")
//                .addTextPara("needAllList","0")
//                .addTextPara("maxResult","50")
//                .addTextPara("id","")
//                .post();

        ArrayList<NewsContent> list = new ArrayList<>();
        try {
            String st = "http://v.juhe.cn/toutiao/index?type=" + channelId + "&key=2f9b39d413bc98688f9088fa3a7bf58c";
            Log.e("st",st);
            String text = NetworkUtil.getText(st);
            Log.e("json",text);

            JSONObject jsonObject = new JSONObject(text);
            String reason = jsonObject.getString("reason");
            Log.e("reason", reason);
            if (!reason.equals("成功的返回")) {
                return list;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("data");
            String noImageUrl = "https://github.com/ME495/pictures/raw/master/%E6%9A%82%E6%97%A0%E5%9B%BE%E7%89%87.jpg";
            Bitmap b = NetworkUtil.getBitmap(noImageUrl);
            Log.e("len",jsonArray.length()+"");
            for(int i=0;i<jsonArray.length();++i){
                Log.e("i",i+"");
                JSONObject object = (JSONObject) jsonArray.get(i);
                NewsContent newsContent = new NewsContent();
                newsContent.setTitle(object.getString("title"));
                newsContent.setDesc("");
                newsContent.setLink(object.getString("url"));
                String url = object.getString("thumbnail_pic_s");
//                    newsContent.setBitmap(NetworkUtil.getBitmap(url));
                if ( url != null){
                    newsContent.setImageUrl(url);
                    newsContent.setBitmap(null);
                } else {
                    newsContent.setImageUrl(noImageUrl);
                    newsContent.setBitmap(b);
                }
                list.add(newsContent);
                Log.e("title",newsContent.getTitle());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
