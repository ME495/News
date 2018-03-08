package team.innovation.news.business;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.util.Log;
import android.widget.CheckBox;

import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import team.innovation.news.entity.Channel;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：获取新闻类型
 */
public class GetChannelList {
    private GetChannelList(){}

    public static ArrayList<Channel> getList(){
        String text = new ShowApiRequest("http://route.showapi.com/109-34", "58465", "76fd044d1ae74ea0bff5c000500d594d")
                .post();
        try {
//            Log.e("text",text);
            JSONObject jsonObject = new JSONObject(text);
            JSONObject body = jsonObject.getJSONObject("showapi_res_body");
            JSONArray jsonArray = body.getJSONArray("channelList");
            ArrayList<Channel> arrayList = new ArrayList<>();
//            Log.e("list_size", jsonArray.length()+"");
            for(int i=0;i<jsonArray.length();++i){
                String channelId = ((JSONObject)jsonArray.get(i)).getString("channelId");
                String name = ((JSONObject)jsonArray.get(i)).getString("name");
                arrayList.add(new Channel(channelId, name));
//                Log.e("channel", channelId + " " + name);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
