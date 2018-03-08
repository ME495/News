package team.innovation.news;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends Activity {
    private WebView webView;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.web);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
//        flag = false;
        webView.loadUrl(link);
//        flag = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (flag) finish();
    }
}
