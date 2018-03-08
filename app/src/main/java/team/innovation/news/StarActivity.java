package team.innovation.news;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import team.innovation.news.adapter.NewsContentAdapter;
import team.innovation.news.business.MyDatabaseHelper;
import team.innovation.news.entity.NewsContent;

public class StarActivity extends Activity {
    private ListView listView;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_star);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_for_favourites);

        listView = findViewById(R.id.star_list);
        btnBack = findViewById(R.id.btnBackInFavourites);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new LoadStarNewsContent().execute();
    }

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
