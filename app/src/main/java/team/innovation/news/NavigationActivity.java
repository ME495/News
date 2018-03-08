package team.innovation.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
public class NavigationActivity extends Activity {
    private LinearLayout bar;
    private ArrayList<TextView> textViews;
    private ListView listView;
    private ProgressBar progressBar;
    private ImageView next;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_navigation);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        bar = findViewById(R.id.bar);
        listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.pb);
        tvTitle = findViewById(R.id.tvTitle);
        // 更改标题栏字体
        tvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "PERTILI.TTF"));
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), StarActivity.class);
                startActivity(intent);
            }
        });
        new LoadChanneBar().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsContent newsContent = GetNewsContentList.getItem(i);
                WebView webView = new WebView(getApplicationContext());
                webView.loadUrl(newsContent.getLink());
            }
        });
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
            if(progressBar.getVisibility()!=View.GONE){
                progressBar.setVisibility(View.GONE);
            }
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
                final TextView textView = new TextView(NavigationActivity.this);
                textView.setText(list.get(i).getName());
                textView.setTag(list.get(i).getChannelId());
                textView.setHeight(125);
                textView.setPadding(30,0,30,0);
                textView.setBackgroundColor(0xFFFFFFFF);
                textView.setTextSize(23);
                textView.setGravity(Gravity.CENTER);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TextView textView = (TextView) v;
                        resetTextViewColor();
                        textView.setBackgroundColor(0xFFF44336);
                        textView.setTextColor(0xFFFFFFFF);
                        if(progressBar.getVisibility() == View.GONE){
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        new LoadNewsContent().execute(v.getTag().toString());
                    }
                });
                textViews.add(textView);
                bar.addView(textView);
            }
            textViews.get(0).callOnClick();

        }
        /**
         * 让bar中的TextView恢复默认颜色
         * jq
         * 18/03/08
          */
        protected void resetTextViewColor() {
            for(int i = 0; i < bar.getChildCount(); i++) {
                TextView textView = (TextView) bar.getChildAt(i);
                    textView.setBackgroundColor(0xFFFFFFFF);
                    textView.setTextColor(0x8A000000);
            }
        }
    }
}
