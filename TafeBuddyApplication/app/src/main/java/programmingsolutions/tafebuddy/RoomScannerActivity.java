package programmingsolutions.tafebuddy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import custom_tabs.CustomTabsHelper;

public class RoomScannerActivity extends AppCompatActivity implements CustomTabActivityHelper.ConnectionCallback {
    public static final String EXTRA_URI = "programmingsolutions.tafebuddy.roomscanneractivity.MESSAGE";
    //setting up the custom tab helper class
    private CustomTabActivityHelper customTabActivityHelper;
    SurfaceView cameraView;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    Button preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_scanner);
        preview = (Button) findViewById(R.id.scan_button);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_scan);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        /*ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        customTabActivityHelper = new CustomTabActivityHelper();
        //lets the helper know that we want this class to be used.
        customTabActivityHelper.setConnectionCallback(this);
        CustomTabsHelper.getPackageNameToUse(this);



        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo = (TextView)findViewById(R.id.txt_scan_content);
        //Disable preview Button
        preview.setEnabled(false);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(RoomScannerActivity.this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED)
                    {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    preview.post(new Runnable() {
                        @Override
                        public void run() {
                            preview.setEnabled(true);
                        }
                    });
                    preview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(barcodes.valueAt(0).displayValue);
                            openCustomTab(uri);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Disable preview Button
        preview.setEnabled(false);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(RoomScannerActivity.this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED)
                    {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    preview.post(new Runnable() {
                        @Override
                        public void run() {
                            preview.setEnabled(true);
                        }
                    });
                    preview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(barcodes.valueAt(0).displayValue);
                            openCustomTab(uri);
                        }
                    });
                }
            }
        });
    }

    //housekeeping freeing up space or binding to the service.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        customTabActivityHelper.setConnectionCallback(null);
        //barcodes.clear();
        //finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customTabActivityHelper.unbindCustomTabsService(this);
        //barcodes.clear();
        //finish();
    }


    //Method handling all of the Custom tab customization.
    private void openCustomTab(Uri uri){

        //creating a custom tab and making customizing the animations and toolbar.
        final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(customTabActivityHelper.getSession());

        //setting the toolbar color also allowing the tab to show the title of the wabpage.
        builder.setToolbarColor(Color.RED).setShowTitle(true);

        //this will change the custom animations for custom tab using animatinos resource files.
        builder.setStartAnimations(RoomScannerActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(RoomScannerActivity.this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        //setting the home button in the custom tab
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back));

        //this will hide the URL bar when a user scrolls down the page.
        builder.enableUrlBarHiding();

        //this adds to the menu android default share.
        builder.addDefaultShareMenuItem();

        //this should add actions to the custom tab pages
        //prepareActionButton(builder);

        //this will check to see what browser is availible to handle custom tab.
        CustomTabsIntent customTabsIntent = builder.build();
        CustomTabsHelper.addKeepAliveExtra(getBaseContext(), customTabsIntent.intent);

        //sending the information to the helper to process
        CustomTabActivityHelper.openCustomTab(RoomScannerActivity.this, builder.build(), uri, new WebviewFallback());
    }

}

