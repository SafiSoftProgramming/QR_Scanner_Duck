package safisoft.qrscannerduck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {



    private static final int REQUEST= 112;
    String[] PERMISSIONS = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.CHANGE_WIFI_STATE
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// hide notification bar

        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermissions(this, PERMISSIONS)) {


                AppPermissionsDialog appPermissionsDialog = new AppPermissionsDialog(SplashActivity.this);
                appPermissionsDialog.show();
                appPermissionsDialog.setCanceledOnTouchOutside(false);
                appPermissionsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                appPermissionsDialog.btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS, REQUEST);
                        appPermissionsDialog.dismiss();
                    }
                });





            }
            else {
                  callActivity();
                }
        }
        else {
           callActivity();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG","@@@ PERMISSIONS grant");
                    callActivity();
                } else {
                    Log.d("TAG","@@@ PERMISSIONS Denied");
                    Toast.makeText(getApplicationContext(), "PERMISSIONS Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void callActivity() {
        new CountDownTimer(500, 500) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }



}