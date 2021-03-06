package programmingsolutions.tafebuddy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.util.Log;


import java.util.List;

import custom_tabs.CustomTabsHelper;
import custom_tabs.ServiceConnectionCallback;
import custom_tabs.SessionHelper;

/**
 * This is a helper class to manage the connection to the Custom Tabs Service.
 */


class CustomTabActivityHelper implements ServiceConnectionCallback {

    private static final String TAG = "CustomTabsClientExample";

    private CustomTabsSession mCustomTabsSession;
    private CustomTabsClient mClient;
    private CustomTabsServiceConnection mConnection;
    private ConnectionCallback mConnectionCallback;


    //opens the URL on a Custom Tab if possible ortherwise it will fall back to opening it on a WebView.


    static void openCustomTab(Activity activity,
                              CustomTabsIntent customTabsIntent,
                              Uri uri,
                              CustomTabFallBack fallBack){

        CustomTabsHelper.getPackages();

        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        //if we cant find a package name it means that theres no browser that supports
        //Chrome Custom Tabs installed. So we fallback to using a webview.
        if(URLS.isFormattable(uri.toString())&&packageName != null&&fallBack != null){
            fallBack.openURI(activity,uri);
        }
        //otherwise it will open in a custom tab
        else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
    }


    //this will unbind the activity from Custom Tabs Service freeing up resources
    void unbindCustomTabsService(Activity activity) {
        if (mConnection == null) return;
        activity.unbindService(mConnection);
        mClient = null;
        mCustomTabsSession = null;
        mConnection = null;
    }

    //this will create the session
    CustomTabsSession getSession() {
        if (mClient == null) {
            mCustomTabsSession = null;
        } else if (mCustomTabsSession == null) {
            mCustomTabsSession = mClient.newSession(new NavigationCallback());
            SessionHelper.setCurrentSession(mCustomTabsSession);
        }
        return mCustomTabsSession;
    }

    private static class NavigationCallback extends CustomTabsCallback {
        @Override
        public void onNavigationEvent(int navigationEvent, Bundle extras) {
            Log.w(TAG, "onNavigationEvent: Code = " + navigationEvent);
        }
    }

    /*  Register a Callback to be called when connected or disconnected from the Custom Tabs Service*/
    void setConnectionCallback(ConnectionCallback connectionCallback) {
        this.mConnectionCallback = connectionCallback;
    }

    /* Binds the Activity to the Custom Tabs Service.  activity the activity to be binded to the service. */
    void bindCustomTabsService(Activity activity) {
        if (mClient != null) return;

        String packageName = CustomTabsHelper.getPackageNameToUse(activity);
        if (packageName == null) return;

        mConnection = new ServiceConnection(this);
        CustomTabsClient.bindCustomTabsService(activity, packageName, mConnection);
    }

    //this will warm up the browser and get faster loading times.
    public boolean mayLaunchUrl(Uri uri, Bundle extras, List<Bundle> otherLikelyBundles) {
        if (mClient == null) return false;

        CustomTabsSession session = getSession();
        return session != null && session.mayLaunchUrl(uri, extras, otherLikelyBundles);

    }

    @Override
    public void onServiceConnected(CustomTabsClient client) {

           mClient = client;
           mClient.warmup(0L);
           CustomTabsSession customTabsSession = getSession();



       //getting the urls ready in a bundle so they can passed to maylaunch method
        Bundle campusBundle = new Bundle();

        campusBundle.putString("CourseScehdule", URLS.COURSE_SCHEDULE);
        campusBundle.putString("FAQ", URLS.FAQ);
        campusBundle.putString("Counselling", URLS.COUNSELLING_BOOKING);
        campusBundle.putString("Calender", URLS.CALENDER);
        campusBundle.putString("Video", URLS.VIDEO);
        campusBundle.putString("OneDrive", URLS.ONEDRIVE);
        campusBundle.putString("OneNote", URLS.ONENOTE);
        campusBundle.putString("Account", URLS.ACCOUNT);
        campusBundle.putString("UserDetails", URLS.USERDETAILS);
        campusBundle.putString("Email", URLS.EMAIL);
        campusBundle.putString("Account Mainpage", URLS.ACCOUNT_MAIN_PAGE);
        campusBundle.putString("Files", URLS.FILES);
        campusBundle.putString("Moodle", URLS.MOODLE);
        campusBundle.putString("courseInformation", URLS.COURSE_INFORMATION);






        customTabsSession.mayLaunchUrl(null,campusBundle,null);


    }



    @Override
    public void onServiceDisconnected() {

        mClient = null;
        mCustomTabsSession = null;

    }







    /**
     * A Callback for when the service is connected or disconnected. Use those callbacks to
     * handle UI changes when the service is connected or disconnected.
     */
    interface ConnectionCallback {
        /**
         * Called when the service is connected.
         */


        /**
         * Called when the service is disconnected.
         */

    }


    //this will be used as a fallback to open urls when custom tabs is not availible. we will use
    //a webview to load the data

    interface CustomTabFallBack{


        //passing in the activity that wants to open the URL and the URL to be opened by the fallback
        //webview.

        void openURI(Activity activity, Uri uri);

    }

}
