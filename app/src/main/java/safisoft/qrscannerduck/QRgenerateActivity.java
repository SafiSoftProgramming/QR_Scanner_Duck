package safisoft.qrscannerduck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.EMail;
import net.glxn.qrgen.core.scheme.GeoInfo;
import net.glxn.qrgen.core.scheme.MMS;
import net.glxn.qrgen.core.scheme.SMS;
import net.glxn.qrgen.core.scheme.Telephone;
import net.glxn.qrgen.core.scheme.Url;
import net.glxn.qrgen.core.scheme.VCard;
import net.glxn.qrgen.core.scheme.Wifi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;


public class QRgenerateActivity extends AppCompatActivity {

    ImageView img_qr_stage , imageView_qr1 , imageView_qr2 , imageView_qr3 , imageView_qr4  ;
    TextView txtv_qr1 , txtv_qr2 , txtv_qr3 , txtv_qr4 ;
    Bitmap FINAL_BITMAP ;
    String QRCODE_TYPE ;
    HorizontalScrollView scrollview ;
    ImageButton btn_Save_QR , btn_Share_QR;
    private AdView adView_Banner;
    private InterstitialAd mInterstitialAd;

    String TEXT_QR_TEXTVAL ;
    VCard vCard ;
    EMail email ;
    GeoInfo geoInfo ;
    Telephone telephone ;
    Url url ;
    Wifi wifi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerate);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView_Banner = findViewById(R.id.adView_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Banner.loadAd(adRequest);


        AdRequest adRequest2 = new AdRequest.Builder().build();
        // real ad code ca-app-pub-5637187199850424/7749846288   test code ca-app-pub-3940256099942544/1033173712
        InterstitialAd.load(this,"ca-app-pub-5637187199850424/7749846288", adRequest2, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });


        img_qr_stage = findViewById(R.id.img_qr_stage);

        imageView_qr1 = findViewById(R.id.imageView_qr1);
        imageView_qr2 = findViewById(R.id.imageView_qr2);
        imageView_qr3 = findViewById(R.id.imageView_qr3);
        imageView_qr4 = findViewById(R.id.imageView_qr4);

        txtv_qr1 = findViewById(R.id.txtv_qr1);
        txtv_qr2 = findViewById(R.id.txtv_qr2);
        txtv_qr3 = findViewById(R.id.txtv_qr3);
        txtv_qr4 = findViewById(R.id.txtv_qr4);

        btn_Save_QR = findViewById(R.id.btn_Save_QR);
        btn_Share_QR = findViewById(R.id.btn_Share_QR);

        QRCODE_TYPE = getIntent().getStringExtra("QRCODE_TYPE");

        scrollview = findViewById(R.id.scrollview);



        if(QRCODE_TYPE.equals("TEXT")){
            TEXT_QR_TEXTVAL = getIntent().getStringExtra("TEXT_VAL");
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_text));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_text));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }



        if(QRCODE_TYPE.equals("CONTACT")){
            vCard = new VCard();
            vCard.setName(getIntent().getStringExtra("CONTACT_NAME"));
            vCard.setPhoneNumber(getIntent().getStringExtra("CONTACT_PHONE"));
            vCard.setAddress(getIntent().getStringExtra("CONTACT_ADDRESS"));
            vCard.setEmail(getIntent().getStringExtra("CONTACT_EMAIL"));
            vCard.setCompany(getIntent().getStringExtra("CONTACT_COMPANY"));
            vCard.setTitle(getIntent().getStringExtra("CONTACT_TITLE"));
            vCard.setWebsite(getIntent().getStringExtra("CONTACT_WEBSITE"));
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_contact));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_contact));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }

        if(QRCODE_TYPE.equals("EMAIL")){
            email = new EMail(getIntent().getStringExtra("EMAIL_VAL"));
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_email));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_email));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }

        if(QRCODE_TYPE.equals("LOCATION")){
            geoInfo = new GeoInfo();
            List<String> supplierNames = Arrays.asList(getIntent().getStringExtra("LOCATION_lat"),getIntent().getStringExtra("LOCATION_lng"));
            geoInfo.setPoints(supplierNames);
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_location));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_location));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }

        if(QRCODE_TYPE.equals("TELEPHONE")){
            telephone = new Telephone();
            telephone.setTelephone(getIntent().getStringExtra("TELEPHONE_VAL"));
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_phone));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_phone));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }

        if(QRCODE_TYPE.equals("URL")){
            url = new Url();
            url.setUrl(getIntent().getStringExtra("URL_VAL"));
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_url));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_url));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }

        if(QRCODE_TYPE.equals("WIFI")){
            wifi = new Wifi();
            wifi.setSsid(getIntent().getStringExtra("WIFI_NAME"));
            wifi.setPsk(getIntent().getStringExtra("WIFI_PASSWORD"));
            wifi.setAuthentication(getIntent().getStringExtra("WIFI_TYPE"));
            String WIFI_VISIBLE = getIntent().getStringExtra("WIFI_VISIBLE") ;
            if(WIFI_VISIBLE.equals("yes")) {
                wifi.setHidden(false);
            }
            else {
                wifi.setHidden(true);
            }
            img_qr_stage.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr1.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow));
            imageView_qr2.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white));
            imageView_qr3.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow_wifi));
            imageView_qr4.setImageBitmap(LAYOUT_MAKER(getResources().getColor(R.color.black),getResources().getColor(R.color.white),R.drawable.ic_qr_bg_white_wifi));
            FINAL_BITMAP = LAYOUT_MAKER(getResources().getColor(R.color.white),getResources().getColor(R.color.duck),R.drawable.ic_qr_bg_yellow) ;
        }




        imageView_qr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart_Colors();
                txtv_qr1.setTextColor(getResources().getColor(R.color.duck));
                txtv_qr1.setTypeface(Typeface.DEFAULT_BOLD);
                img_qr_stage.setImageBitmap(((BitmapDrawable)imageView_qr1.getDrawable()).getBitmap());
                FINAL_BITMAP = ((BitmapDrawable)imageView_qr1.getDrawable()).getBitmap() ;
            }
        });
        imageView_qr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart_Colors();
                txtv_qr2.setTextColor(getResources().getColor(R.color.duck));
                txtv_qr2.setTypeface(Typeface.DEFAULT_BOLD);
                img_qr_stage.setImageBitmap(((BitmapDrawable)imageView_qr2.getDrawable()).getBitmap());
                FINAL_BITMAP = ((BitmapDrawable)imageView_qr2.getDrawable()).getBitmap() ;
            }
        });
        imageView_qr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart_Colors();
                txtv_qr3.setTextColor(getResources().getColor(R.color.duck));
                txtv_qr3.setTypeface(Typeface.DEFAULT_BOLD);
                img_qr_stage.setImageBitmap(((BitmapDrawable)imageView_qr3.getDrawable()).getBitmap());
                FINAL_BITMAP = ((BitmapDrawable)imageView_qr3.getDrawable()).getBitmap() ;
            }
        });
        imageView_qr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart_Colors();
                txtv_qr4.setTextColor(getResources().getColor(R.color.duck));
                txtv_qr4.setTypeface(Typeface.DEFAULT_BOLD);
                img_qr_stage.setImageBitmap(((BitmapDrawable)imageView_qr4.getDrawable()).getBitmap());
                FINAL_BITMAP = ((BitmapDrawable)imageView_qr4.getDrawable()).getBitmap() ;
            }
        });


        btn_Save_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 SaveImagescan(FINAL_BITMAP);
            }
        });

        btn_Share_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBitmap(FINAL_BITMAP);

            }
        });



    }


    private void Restart_Colors(){
        txtv_qr1.setTextColor(getResources().getColor(R.color.white));
        txtv_qr1.setTypeface(Typeface.DEFAULT);

        txtv_qr2.setTextColor(getResources().getColor(R.color.white));
        txtv_qr2.setTypeface(Typeface.DEFAULT);

        txtv_qr3.setTextColor(getResources().getColor(R.color.white));
        txtv_qr3.setTypeface(Typeface.DEFAULT);

        txtv_qr4.setTextColor(getResources().getColor(R.color.white));
        txtv_qr4.setTypeface(Typeface.DEFAULT);
    }

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2 ) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp2.getWidth(), bmp2.getHeight(), bmp2.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, (bmp2.getWidth() - bmp1.getWidth()) / 2, (bmp2.getHeight() - bmp1.getHeight()) / 2, null);
        canvas.drawBitmap(bmp2,new Matrix() , null);

        return bmOverlay;
    }

    private Bitmap LAYOUT_MAKER(int QR_COLOR , int QR_BG_COLOR , int IMAGE_QR_FRAME ) {

        Bitmap DataBitmap = null ;

        if(QRCODE_TYPE.equals("TEXT")) {
            DataBitmap = QRCode.from(TEXT_QR_TEXTVAL).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("CONTACT")) {
            DataBitmap = QRCode.from(vCard).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("EMAIL")){
            DataBitmap = QRCode.from(email).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("LOCATION")){
            DataBitmap =   QRCode.from(geoInfo).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("TELEPHONE")){
            DataBitmap =   QRCode.from(telephone).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("URL")){
            DataBitmap =   QRCode.from(url).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }
        if(QRCODE_TYPE.equals("WIFI")){
            DataBitmap =   QRCode.from(wifi).withSize(830, 830).withColor(QR_COLOR,QR_BG_COLOR).withCharset("UTF-8").bitmap();
        }

        Bitmap FRAME = BitmapFactory.decodeResource(getResources(),IMAGE_QR_FRAME);
        FRAME = Bitmap.createScaledBitmap(FRAME, 1000, 1000, false);

        return overlay(DataBitmap,FRAME);
    }

    private void SaveImagescan(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/QRScannerDuck");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = System.currentTimeMillis()+"_QRScannerDuck"+".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.setHasAlpha(true);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

            SavedDoneDialog savedDoneDialog = new SavedDoneDialog(QRgenerateActivity.this);
            savedDoneDialog.show();
            savedDoneDialog.setCanceledOnTouchOutside(false);
            savedDoneDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            savedDoneDialog.btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(QRgenerateActivity.this);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }

                    savedDoneDialog.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void shareBitmap(@NonNull Bitmap bitmap)
    {
        //---Save bitmap to external cache directory---//
        //get cache directory
        File cachePath = new File(getExternalCacheDir(), "my_images/");
        cachePath.mkdirs();

        //create png file
        File file = new File(cachePath, "Image_123.png");
        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //---Share File---//
        //get file uri
        Uri myImageFileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

        //create a intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri);
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "Share with"));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QRgenerateActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }


}