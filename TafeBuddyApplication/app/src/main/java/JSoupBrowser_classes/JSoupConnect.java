package JSoupBrowser_classes;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Ben on 29/05/2017.
 */

public class JSoupConnect extends AsyncTask<String,Void,String> {
    public static String html;
    public static Document doc;
    public static boolean loaded = false;
    @Override
    protected String doInBackground(String... URL) {
        System.out.println("Loading webpage");
        html = "Loading "+URL[0];
        loaded = false;
        try{
            doc = Jsoup.connect(URL[0]).get();
            System.out.println("Finished");
            loaded = true;
        }catch(Exception e){
            // this.exception = e;
            System.err.println(e);
            html = e.toString();
            loaded = true;
        }
        return html;
    }
}
