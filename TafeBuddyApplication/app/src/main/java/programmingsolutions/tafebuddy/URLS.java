package programmingsolutions.tafebuddy;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;

import JSoupBrowser_classes.JSoupConnect;

/**
 * Created by Ben on 25/05/2017.
 */

public abstract class URLS {

    static final String COURSE_SCHEDULE = "https://my.tafesa.edu.au/PROD/bwskfshd.P_CrseSchd";
    static final String COUNSELLING_BOOKING = "http://itstudies.simplybook.me/sheduler/manage";
    static final String FAQ = "http://www.tafesa.edu.au/mytafe-sa/help";
    static final String CALENDER = "https://outlook.office.com/owa/?realm=student.tafesa.edu.au&exsvurl=1&ll-cc=1033&modurl=1&path=/calendar/view/Month";
    static final String VIDEO = "https://tafesaedu.sharepoint.com/portals/hub/_layouts/15/PointPublishing.aspx?app=video&p=h";
    static final String ONEDRIVE = "https://tafesaedu-my.sharepoint.com/";
    static final String ONENOTE = "https://www.onenote.com/notebooks";
    static final String ACCOUNT = "https://my.tafesa.edu.au/PROD/bwsksphs.P_ViewStatement";
    static final String USERDETAILS = "https://my.tafesa.edu.au/PROD/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu#pageName=bmenu--P_GenMnu___UID1&pageReferrerId=&pageDepth=2&options=false";
    static final String EMAIL = "https://outlook.office.com";
    static final String ACCOUNT_MAIN_PAGE = "https://my.tafesa.edu.au/PROD/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu#pageName=bmenu--P_StuMainMnu___UID0&pageReferrerId=&pageDepth=2&options=false";
    static final String FILES = "http://netstorage.tafesa.edu.au/SitePages/Home.aspx";
    static final String COURSE_INFORMATION = "http://www.tafesa.edu.au/courses";
    static final String MOODLE = "http://learn.tafesa.edu.au/course/view.php?id=3129";

    //Once you have created a formattable page for one of the urls add it to this list to use it.
    private static final String[] Formattable = {/*ACCOUNT,FAQ*/};
    private static long counter = 0;

    private static HashMap<String,String> Cache = new HashMap();

    public static String loadHTML(String URL){

        //if url is in cache load from memory
        if(Cache.containsKey(URL)){
            //clear cache if its got too many pages
            if(Cache.size()>3){clearCache();}
            return Cache.get(URL);

        }else{
            System.out.println("LOADING    "+URL);

            //theres a bug with retreiving the html from a page which causes the html to be corrupt
            //this needs to be fixed for the html formatter to fully work, i recommend downloading the html using this
            //and going through it to see whats different from loading it normally.

            new JSoupConnect().execute(URL);
            while(!JSoupConnect.loaded){
                counter++;
            }

            Document DOC = JSoupConnect.doc;


            //format the given page

            DOC = Format(URL,DOC);
            String HTML = Format(URL,DOC.html());


            System.err.println(HTML);


            //store the new page into the cache
            Cache.put(URL,HTML);

        }
        return JSoupConnect.html;
    }

    //Checks to see if url is customisable by checking the list
    public static boolean isFormattable(String URL){
        for(String url : Formattable){
            if(url.equals(URL)){
                //page is customisable
                return true;
            }
        }
        return false;
    }
    /*
       this is where all the formatting of the urls will go
       for example

        case ACCOUNT:
           HTML.replace("Welcome","Welcome "+StudentName);
           break;

        the replace can be used to delete html you dont need.



        */
    public static String Format(String URL,String HTML){
        switch(URL){
            case ACCOUNT:
                //implement customisations

                break;
            case FAQ:

                break;
        }
        return HTML;
    }

    /*
        this is the document version its harder to figure out
            but much more flexiable

        <html>
            <head>
            </head>
            <body>
                <p id="first"></p>
                <p id="second"></p>
            </body>
        </html>

        Element p = doc.select("p").first();
        p.text("My Example Text");

        <html>
            <head>
            </head>
            <body>
                <p id="first">My Example Text</p>
                <p id="second"></p>
            </body>
        </html>
    */

    public static Document Format(String URL,Document doc){
        switch(URL){
            case ACCOUNT:
                //implement customisations

                break;
            case FAQ:

                break;
        }
        return doc;
    }


    public static void clearCache(){
        Cache.clear();
    }

}
