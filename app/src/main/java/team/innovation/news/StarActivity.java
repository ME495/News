package team.innovation.news;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import team.innovation.news.adapter.NewsContentAdapter;
import team.innovation.news.business.MyDatabaseHelper;
import team.innovation.news.entity.NewsContent;

/**
 * 作者：程坚
 * 时间：2018/3/8
 * 描述：收藏夹页面
 */
public class StarActivity extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        listView = findViewById(R.id.star_list);
        new LoadStarNewsContent().execute();
    }

    /**
     * 从数据库中载入收藏的新闻
     */
    private class LoadStarNewsContent extends AsyncTask<Void, Void, ArrayList<NewsContent>> {

        @Override
        protected ArrayList<NewsContent> doInBackground(Void... voids) {
            MyDatabaseHelper dbHelper =  new MyDatabaseHelper(getApplicationContext(), MyDatabaseHelper.FILENAME, null, 1);
            return dbHelper.getNewsContents();
        }

        @Override
        protected void onPostExecute(ArrayList<NewsContent> newsContents) {
            super.onPostExecute(newsContents);
            NewsContentAdapter adapter = new NewsContentAdapter(StarActivity.this, R.layout.news_content_item, newsContents);
            listView.setAdapter(adapter);
        }
    }
}
