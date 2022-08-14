package safisoft.qrscannerduck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Field;

import static android.content.ContentValues.TAG;
import static android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;

public class StartActivity extends AppCompatActivity {

    SurfaceView surfaceView;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    ImageButton btn_Flash, btn_CameraFlip;
    Barcode QR_CODE;
    int QR_FORMAT;
    boolean flashlight_state = true;
    private AdView adView_Banner;
    LinearLayout lay_click_info;
    ImageButton btn_DuckScan, btn_Settings, btn_Qrcode_Maker, btn_History, btn_Fav;
    TextView txt_lay_info;
    SeekBar seekBar;
    int Camera_Face = CameraSource.CAMERA_FACING_BACK;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        adView_Banner = findViewById(R.id.adView_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Banner.loadAd(adRequest);


        surfaceView = findViewById(R.id.surfaceView);
        btn_Flash = findViewById(R.id.btn_Flash);
        btn_CameraFlip = findViewById(R.id.btn_CameraFlip);

        btn_DuckScan = findViewById(R.id.btn_DuckScan);
        btn_Settings = findViewById(R.id.btn_Settings);
        btn_Qrcode_Maker = findViewById(R.id.btn_Qrcode_Maker);
        btn_History = findViewById(R.id.btn_History);
        btn_Fav = findViewById(R.id.btn_Fav);

        txt_lay_info = findViewById(R.id.txt_lay_info);


        lay_click_info = findViewById(R.id.lay_click_info);

        seekBar = findViewById(R.id.seekBar);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        btn_Flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (flashlight_state) {
                        flashlight_state = false;
                        btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_on);
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        flashlight_state = false;
                    } else {
                        flashlight_state = false;
                        btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_off);
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        flashlight_state = true;
                    }
                } catch (Exception e) {
                    btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_off);
                    show_info_lay("Flash is not supported on this device");
                    flashlight_state = true;
                }


            }
        });

        btn_CameraFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraSource.stop();

                if(cameraSource.getCameraFacing()==CameraSource.CAMERA_FACING_BACK) {
                    cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                            .setRequestedPreviewSize(1920, 1080)
                            .setFacing(CameraSource.CAMERA_FACING_FRONT)
                            .setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE)
                            .build();
                    btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_off);
                    flashlight_state = true ;
                }
               else {
                    cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                            .setRequestedPreviewSize(1920, 1080)
                            .setFacing(CameraSource.CAMERA_FACING_BACK)
                            .setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE)
                            .build();
                    btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_off);
                    flashlight_state = true ;
                }


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                   show_info_lay("Not Supported");
                }


            }
        });



        btn_DuckScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Qrcode_Maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, QRmakerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });
        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","show_only_fav" );
                startActivity(intent);
                finish();
            }
        });




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    seekBar.setMax(cameraSource.Get_max_zoome()-1);
                    cameraSource.doZoom(progress);
                }
                catch (Exception e){
                    show_info_lay("Zoom is not supported on this device");
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void initialiseDetectorsAndSources() {


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        if(!barcodeDetector.isOperational()){
            show_info_lay("Make sure Google Play Services is up to date");
        }


            cameraSource = new CameraSource.Builder(this, barcodeDetector)
                    .setRequestedPreviewSize(1920, 1080)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE)
                    .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(StartActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(StartActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

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
                    QR_CODE = barcodes.valueAt(0);
                    QR_FORMAT = barcodes.valueAt(0).valueFormat ;

                    Intent intent = new Intent(StartActivity.this, ManageResultsActivity.class);
                    intent.putExtra("QR_CODE",QR_CODE );
                    intent.putExtra("QR_FORMAT",QR_FORMAT);
                    intent.putExtra("RECORD_ID_FROM_ADAPTER","no");
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.stop();
        btn_Flash.setBackgroundResource(R.drawable.btn_eff_flash_off);
        flashlight_state = false ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }





    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        show_info_lay("Press Twice to Exit");
    }

    public void show_info_lay (String Show_Text){
        lay_click_info.setVisibility(View.VISIBLE);
        txt_lay_info.setText(Show_Text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                lay_click_info.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }





}