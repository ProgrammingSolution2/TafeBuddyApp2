package programmingsolutions.tafebuddy;

/**
 * Created by timot on 10/6/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import JSoupBrowser_classes.JSoupConnect;
import programmingsolutions.tafebuddy.R;


public class WebviewActivity extends AppCompatActivity {

    //variable to hold URL
    public static final String EXTRA_URL = "extra.url";
    private static boolean Loaded = false;
    private static int count = 0;
    //created the webview with the variable passed from the webviewfallback


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //retrievin the URL from the extra intent we sent through the webview fallback
        String url = getIntent().getStringExtra(EXTRA_URL);

        final WebView webView = (WebView)findViewById(R.id.webview);

        /*Almost works but too slow needs some form of caching and better page recognition
        WebViewClient webClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                //webView.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                if(!Loaded){
                    count++;
                    System.out.println("Loaded "+count+" times");
                    System.out.println("LOADING    "+url);
                    new JSoupConnect().execute(getIntent().getStringExtra(EXTRA_URL));
                    System.out.println("Waiting for webpage to load");
                    while(!JSoupConnect.loaded){

                    }
                    String HTML = JSoupConnect.html;
                    Loaded = true;
                    webView.loadData(HTML, "text/html", "UTF-8");

                }else Loaded = false;
            }
        };
        //webView.setWebViewClient(webClient);
        */

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //allows login to stay cached
        webSettings.setAppCacheEnabled(true);

        //setting the title of the page url for now
        setTitle("Tafe Buddy 3");
        getSupportActionBar();
        //setting the actionbar to show home button arrow



       // webView.loadUrl(url);
        webView.loadData(URLS.loadHTML(url), "text/html", "UTF-8");

    }


    //this will respong when the user selects the home button on the action bar
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

}
