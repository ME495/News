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

import java.util.ArrayList;

import team.innovation.news.entity.NewsContent;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：获取新闻内容列表
 */
public class GetNewsContentList {
    private static ArrayList<NewsContent> list;
    private GetNewsContentList(){}

    /**
     * 获取新闻内容列表
     * @param channelId 频道名称
     * @return 新闻内容列表
     */
    public static ArrayList<NewsContent> getList(String channelId){
        String text = new ShowApiRequest("http://route.showapi.com/109-35", "58465", "76fd044d1ae74ea0bff5c000500d594d")
                .addTextPara("channelId",channelId)
                .addTextPara("channelName","")
                .addTextPara("title","")
                .addTextPara("page","1")
                .addTextPara("needContent","0")
                .addTextPara("needHtml","0")
                .addTextPara("needAllList","0")
                .addTextPara("maxResult","50")
                .addTextPara("id","")
                .post();
        list = new ArrayList<>();
        try {
            Log.e("id",channelId);
            Log.e("json",text);
            String noImageUrl = "https://github.com/ME495/pictures/raw/master/%E6%9A%82%E6%97%A0%E5%9B%BE%E7%89%87.jpg";
            Bitmap b = NetworkUtil.getBitmap(noImageUrl);
            JSONObject jsonObject = new JSONObject(text);
            String errror = jsonObject.getString("showapi_res_error");
            if (errror == null || !errror.trim().equals("")) {
                return list;
            }
            JSONObject body = jsonObject.getJSONObject("showapi_res_body");
            JSONObject pagebean = body.getJSONObject("pagebean");
            JSONArray jsonArray = pagebean.getJSONArray("contentlist");

            for(int i=0;i<jsonArray.length();++i){
                JSONObject object = (JSONObject) jsonArray.get(i);
                NewsContent newsContent = new NewsContent();
                newsContent.setTitle(object.getString("title"));
                newsContent.setDesc(object.getString("desc"));
                newsContent.setLink(object.getString("link"));
                JSONArray imageurls = object.getJSONArray("imageurls");
                if(imageurls.length() > 0) {
                    String url = ((JSONObject)imageurls.get(0)).getString("url");
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

    public static NewsContent getItem(int pos){
        return list.get(pos);
    }
}
