package team.innovation.news;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import team.innovation.news.adapter.NewsContentAdapter;
import team.innovation.news.business.GetChannelList;
import team.innovation.news.business.GetNewsContentList;
import team.innovation.news.entity.Channel;
import team.innovation.news.entity.NewsContent;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：导航界面
 */
public class NavigationActivity extends AppCompatActivity {
    private LinearLayout bar;
    private ArrayList<TextView> textViews;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bar = findViewById(R.id.bar);
        listView = findViewById(R.id.list_view);
        new LoadChanneBar().execute();
    }

    /**
     * 载入新闻内容
     */
    private class LoadNewsContent extends AsyncTask<String, Void, ArrayList<NewsContent>>{

        @Override
        protected ArrayList<NewsContent> doInBackground(String... strings) {
            ArrayList<NewsContent> list = GetNewsContentList.getList(strings[0]);
            if(list == null) Log.e("list","null");
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsContent> newsContents) {
            super.onPostExecute(newsContents);
            NewsContentAdapter adapter = new NewsContentAdapter(NavigationActivity.this, R.layout.news_content_item, newsContents);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 载入频道列表
     */
    private class LoadChanneBar extends AsyncTask<Void, ArrayList<Channel>, ArrayList<Channel>>{

        @Override
        protected ArrayList<Channel> doInBackground(Void... voids) {
            return GetChannelList.getList();
        }

        @Override
        protected void onPostExecute(final ArrayList<Channel> list) {
            super.onPostExecute(list);
            textViews = new ArrayList<>();
            for(int i=0;i<list.size();++i){
                TextView textView = new TextView(NavigationActivity.this);
                textView.setText(list.get(i).getName());
                textView.setTag(list.get(i).getChannelId());
                textView.setTextSize(30);
                textView.setPadding(30,0,30,0);
                textView.setBackgroundColor(0xffff0000);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TextView textView = (TextView) v;
                        new LoadNewsContent().execute(v.getTag().toString());
                    }
                });
                textViews.add(textView);
                bar.addView(textView);
            }
            textViews.get(0).callOnClick();
        }
    }
}
