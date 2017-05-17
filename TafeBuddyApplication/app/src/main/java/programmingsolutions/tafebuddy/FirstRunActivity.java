package programmingsolutions.tafebuddy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.Manifest;

/**
 * Created by Matthew Shinkfield on 11/5/2016.
 */

public class FirstRunActivity extends AppCompatActivity {

    static final int PERMISSION_ALL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_run_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    @Override
    protected void onStart(){
        super.onStart();
        Button permissionButton = (Button) findViewById(R.id.acceptPermissionsButton);
        permissionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
                if (!hasPermissions(FirstRunActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(FirstRunActivity.this, PERMISSIONS, PERMISSION_ALL);
                }
            }
        });
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent mapIntent = new Intent(FirstRunActivity.this, MainPage.class);
                    startActivity(mapIntent);
                } else {
                    View.OnClickListener snackbar = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
                            if (!hasPermissions(FirstRunActivity.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(FirstRunActivity.this, PERMISSIONS, PERMISSION_ALL);
                            }
                        }
                    };
                    Snackbar.make(findViewById(android.R.id.content), "Please accept the permissions...", Snackbar.LENGTH_LONG)
                            .setAction("Accept", snackbar)
                            .show();
                    return;
                }
                return;
            }
        }
    }
}
