package team.innovation.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import team.innovation.news.business.MyDatabaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, MyDatabaseHelper.FILENAME, null, 1);
        dbHelper.initLinkSet();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO 加载网络数据
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跳转Activity
                        Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        //结束当前的 Activity
                        SplashActivity.this.finish();
                    }
                });
            }
        }).start();
    }
}
