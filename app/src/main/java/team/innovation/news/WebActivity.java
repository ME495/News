package team.innovation.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;

public class WebActivity extends Activity {
    private WebView webView;
    private ImageButton btnBack;
//    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_web);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_for_web);
        webView = findViewById(R.id.web);
        btnBack = findViewById(R.id.btnBackInWeb);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
