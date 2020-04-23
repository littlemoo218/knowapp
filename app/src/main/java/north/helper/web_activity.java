package north.helper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class web_activity extends AppCompatActivity {

    private WebView wv;
    private String u = "http://49.232.158.29/18/login.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        this.wv = findViewById(R.id.web);

        this.wv.loadUrl(u);
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.setWebChromeClient(new WebChromeClient());
        this.wv.setWebViewClient(new WebViewClient());
    }
}
