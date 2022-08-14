package safisoft.qrscannerduck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.scheme.BizCard;
import net.glxn.qrgen.core.scheme.Bookmark;
import net.glxn.qrgen.core.scheme.EMail;
import net.glxn.qrgen.core.scheme.GeoInfo;
import net.glxn.qrgen.core.scheme.Girocode;
import net.glxn.qrgen.core.scheme.GooglePlay;
import net.glxn.qrgen.core.scheme.ICal;
import net.glxn.qrgen.core.scheme.KddiAu;
import net.glxn.qrgen.core.scheme.MMS;
import net.glxn.qrgen.core.scheme.MeCard;
import net.glxn.qrgen.core.scheme.SMS;
import net.glxn.qrgen.core.scheme.Telephone;
import net.glxn.qrgen.core.scheme.Url;
import net.glxn.qrgen.core.scheme.VCard;
import net.glxn.qrgen.core.scheme.Wifi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static safisoft.qrscannerduck.HistoryActivity.hideSoftKeyboard;


public class QRmakerActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageButton btn_amakeqr_text, btn_makeqr_contact, btn_makeqr_email, btn_makeqr_location, btn_makeqr_telephone, btn_makeqr_url, btn_makeqr_wifi;
    ImageButton btn_generate_qr;
    ImageButton btn_wifi_hidden_visible;
    ImageButton btn_wifi_wpa_wpa2, btn_wifi_wep, btn_wifi_open;
    ImageButton btn_DuckScan , btn_Settings , btn_Qrcode_Maker , btn_History , btn_Fav ;
    LinearLayout lay_qr_text, lay_qr_contact, lay_qr_email, lay_qr_telephone, lay_qr_location, lay_qr_url, lay_qr_wifi;
    LinearLayout lay_maker_but1 ,lay_maker_but2 ,lay_maker_but3 , lay_maker_but4 ;
    EditText edtxt_qr_text;
    EditText edtxt_qr_contact_name, edtxt_qr_contact_phone, edtxt_qr_contact_address, edtxt_qr_contact_email, edtxt_qr_contact_company, edtxt_qr_contact_title, edtxt_qr_contact_website;
    EditText edtxt_qr_email;
    EditText edtxt_qr_telephone;
    EditText edtxt_qr_url;
    EditText edtxt_qr_wifi_name, edtxt_qr_wifi_password;
    EditText edtxt_qr_location_lat, edtxt_qr_location_long;
    TextView txtv_qrtxt_length, txtv_required_fields;
    TextView txt_lay_info ;
    RelativeLayout lay_activity_qrmaker_root;
    String QRCODE_TYPE;
    ScrollView scrollview;
    LinearLayout lay_click_info ;
    View map;
    boolean wifi_hidden_visible = true;
    String WIFI_VISIBLE = "yes";
    String WIFI_TYPE = "WPA/WPA2";
    private GoogleMap mMap;
    double lat = 0;
    double lng = 0;
    private AdView adView_Banner;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    private FusedLocationProviderClient fusedLocationClient;

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrmaker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        adView_Banner = findViewById(R.id.adView_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Banner.loadAd(adRequest);

        btn_DuckScan = findViewById(R.id.btn_DuckScan);
        btn_Settings = findViewById(R.id.btn_Settings);
        btn_Qrcode_Maker = findViewById(R.id.btn_Qrcode_Maker);
        btn_History = findViewById(R.id.btn_History);
        btn_Fav = findViewById(R.id.btn_Fav);

        btn_amakeqr_text = findViewById(R.id.btn_amakeqr_text);
        btn_makeqr_contact = findViewById(R.id.btn_makeqr_contact);
        btn_makeqr_email = findViewById(R.id.btn_makeqr_email);
        btn_makeqr_location = findViewById(R.id.btn_makeqr_location);
        btn_makeqr_telephone = findViewById(R.id.btn_makeqr_telephone);
        btn_makeqr_url = findViewById(R.id.btn_makeqr_url);
        btn_makeqr_wifi = findViewById(R.id.btn_makeqr_wifi);

        edtxt_qr_contact_name = findViewById(R.id.edtxt_qr_contact_name);
        edtxt_qr_contact_phone = findViewById(R.id.edtxt_qr_contact_phone);
        edtxt_qr_contact_address = findViewById(R.id.edtxt_qr_contact_address);
        edtxt_qr_contact_email = findViewById(R.id.edtxt_qr_contact_email);
        edtxt_qr_contact_company = findViewById(R.id.edtxt_qr_contact_company);
        edtxt_qr_contact_title = findViewById(R.id.edtxt_qr_contact_title);
        edtxt_qr_contact_website = findViewById(R.id.edtxt_qr_contact_website);

        lay_maker_but1 = findViewById(R.id.lay_maker_but1);
        lay_maker_but2 = findViewById(R.id.lay_maker_but2);
        lay_maker_but3 = findViewById(R.id.lay_maker_but3);
        lay_maker_but4 = findViewById(R.id.lay_maker_but4);

        edtxt_qr_email = findViewById(R.id.edtxt_qr_email);

        edtxt_qr_telephone = findViewById(R.id.edtxt_qr_telephone);

        edtxt_qr_url = findViewById(R.id.edtxt_qr_url);

        edtxt_qr_wifi_name = findViewById(R.id.edtxt_qr_wifi_name);
        edtxt_qr_wifi_password = findViewById(R.id.edtxt_qr_wifi_password);

        edtxt_qr_location_lat = findViewById(R.id.edtxt_qr_location_lat);
        edtxt_qr_location_long = findViewById(R.id.edtxt_qr_location_long);

        btn_generate_qr = findViewById(R.id.btn_generate_qr);

        btn_wifi_hidden_visible = findViewById(R.id.btn_wifi_hidden_visible);

        btn_wifi_wpa_wpa2 = findViewById(R.id.btn_wifi_wpa_wpa2);
        btn_wifi_wep = findViewById(R.id.btn_wifi_wep);
        btn_wifi_open = findViewById(R.id.btn_wifi_open);

        lay_click_info =findViewById(R.id.lay_click_info);

        lay_activity_qrmaker_root = findViewById(R.id.lay_activity_qrmaker_root);

        scrollview = findViewById(R.id.scrollview);
        map = findViewById(R.id.map);


        lay_qr_text = findViewById(R.id.lay_qr_text);
        lay_qr_contact = findViewById(R.id.lay_qr_contact);
        lay_qr_email = findViewById(R.id.lay_qr_email);
        lay_qr_telephone = findViewById(R.id.lay_qr_telephone);
        lay_qr_url = findViewById(R.id.lay_qr_url);
        lay_qr_wifi = findViewById(R.id.lay_qr_wifi);
        lay_qr_location = findViewById(R.id.lay_qr_location);

        edtxt_qr_text = findViewById(R.id.edtxt_qr_text);
        txtv_qrtxt_length = findViewById(R.id.txtv_qrtxt_length);
        txtv_required_fields = findViewById(R.id.txtv_required_fields);

        txt_lay_info = findViewById(R.id.txt_lay_info);



       // setupUI(lay_activity_qrmaker_root);

        btn_DuckScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRmakerActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRmakerActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Qrcode_Maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch_Visibility();
            }
        });
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRmakerActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });
        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRmakerActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","show_only_fav" );
                startActivity(intent);
                finish();
            }
        });





        btn_amakeqr_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_text.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "TEXT";
            }
        });
        btn_makeqr_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_contact.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "CONTACT";
            }
        });
        btn_makeqr_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_email.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "EMAIL";

            }
        });
        btn_makeqr_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_location.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "LOCATION";

            }
        });
        btn_makeqr_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_telephone.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "TELEPHONE";

            }
        });
        btn_makeqr_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_url.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "URL";

            }
        });
        btn_makeqr_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideButtons();
                lay_qr_wifi.setVisibility(View.VISIBLE);
                btn_generate_qr.setVisibility(View.VISIBLE);
                QRCODE_TYPE = "WIFI";

            }
        });


        btn_generate_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(QRmakerActivity.this, QRgenerateActivity.class);

                if (QRCODE_TYPE.equals("TEXT")) {
                    if (!edtxt_qr_text.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("TEXT_VAL", edtxt_qr_text.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("CONTACT")) {
                    if (!edtxt_qr_contact_name.getText().toString().equals("") && !edtxt_qr_contact_phone.getText().toString().equals("") && !edtxt_qr_contact_address.getText().toString().equals("") && !edtxt_qr_contact_email.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("CONTACT_NAME", edtxt_qr_contact_name.getText().toString());
                        intent.putExtra("CONTACT_PHONE", edtxt_qr_contact_phone.getText().toString());
                        intent.putExtra("CONTACT_ADDRESS", edtxt_qr_contact_address.getText().toString());
                        intent.putExtra("CONTACT_EMAIL", edtxt_qr_contact_email.getText().toString());
                        intent.putExtra("CONTACT_COMPANY", edtxt_qr_contact_company.getText().toString());
                        intent.putExtra("CONTACT_TITLE", edtxt_qr_contact_title.getText().toString());
                        intent.putExtra("CONTACT_WEBSITE", edtxt_qr_contact_website.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("EMAIL")) {
                    if (!edtxt_qr_email.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("EMAIL_VAL", edtxt_qr_email.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("LOCATION")) {
                    if (!edtxt_qr_location_lat.getText().toString().equals("") && !edtxt_qr_location_long.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("LOCATION_lat", Double.toString(lat));
                        intent.putExtra("LOCATION_lng", Double.toString(lng));
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("TELEPHONE")) {
                    if (!edtxt_qr_telephone.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("TELEPHONE_VAL", edtxt_qr_telephone.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("URL")) {
                    if (!edtxt_qr_url.getText().toString().equals("") && edtxt_qr_url.getText().toString().contains("https://") || edtxt_qr_url.getText().toString().contains("http://")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("URL_VAL", edtxt_qr_url.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }

                if (QRCODE_TYPE.equals("WIFI")) {
                    if (!edtxt_qr_wifi_name.getText().toString().equals("")) {
                        intent.putExtra("QRCODE_TYPE", QRCODE_TYPE);
                        intent.putExtra("WIFI_NAME", edtxt_qr_wifi_name.getText().toString());
                        intent.putExtra("WIFI_PASSWORD", edtxt_qr_wifi_password.getText().toString());
                        intent.putExtra("WIFI_TYPE", WIFI_TYPE);
                        intent.putExtra("WIFI_VISIBLE", WIFI_VISIBLE);
                        startActivity(intent);
                        finish();
                    } else {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                txtv_required_fields.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                txtv_required_fields.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }


            }
        });


        edtxt_qr_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int count_text = 500;
                int textlength = edtxt_qr_text.getText().length();
                count_text = count_text - textlength;
                txtv_qrtxt_length.setText(Integer.toString(count_text));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_wifi_hidden_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifi_hidden_visible) {
                    btn_wifi_hidden_visible.setBackgroundResource(R.drawable.ic_wifi_hidden_tog);
                    WIFI_VISIBLE = "no";
                    wifi_hidden_visible = false;
                } else {
                    btn_wifi_hidden_visible.setBackgroundResource(R.drawable.ic_wifi_visible_tog);
                    WIFI_VISIBLE = "yes";
                    wifi_hidden_visible = true;
                }

            }
        });


        btn_wifi_wpa_wpa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WIFI_TYPE = "WPA/WPA2";
                btn_wifi_wpa_wpa2.setBackgroundResource(R.drawable.ic_wifi_wpa_wpa2_yes);
                btn_wifi_wep.setBackgroundResource(R.drawable.ic_wifi_wep_no);
                btn_wifi_open.setBackgroundResource(R.drawable.ic_wifi_open_no);


            }
        });
        btn_wifi_wep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WIFI_TYPE = "WEP";
                btn_wifi_wpa_wpa2.setBackgroundResource(R.drawable.ic_wifi_wpa_wpa2_no);
                btn_wifi_wep.setBackgroundResource(R.drawable.ic_wifi_wep_yes);
                btn_wifi_open.setBackgroundResource(R.drawable.ic_wifi_open_no);

            }
        });
        btn_wifi_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WIFI_TYPE = "OPEN";
                btn_wifi_wpa_wpa2.setBackgroundResource(R.drawable.ic_wifi_wpa_wpa2_no);
                btn_wifi_wep.setBackgroundResource(R.drawable.ic_wifi_wep_no);
                btn_wifi_open.setBackgroundResource(R.drawable.ic_wifi_open_yes);

            }
        });


    }


    private void HideButtons() {
        btn_amakeqr_text.setVisibility(View.GONE);
        btn_makeqr_contact.setVisibility(View.GONE);
        btn_makeqr_email.setVisibility(View.GONE);
        btn_makeqr_location.setVisibility(View.GONE);
        btn_makeqr_telephone.setVisibility(View.GONE);
        btn_makeqr_url.setVisibility(View.GONE);
        btn_makeqr_wifi.setVisibility(View.GONE);

        lay_maker_but1.setVisibility(View.GONE);
        lay_maker_but2.setVisibility(View.GONE);
        lay_maker_but3.setVisibility(View.GONE);
        lay_maker_but4.setVisibility(View.GONE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                map.getParent().requestDisallowInterceptTouchEvent(false);
                lat = googleMap.getCameraPosition().target.latitude;
                lng = googleMap.getCameraPosition().target.longitude;

                edtxt_qr_location_lat.setText(String.valueOf(lat));
                edtxt_qr_location_long.setText(String.valueOf(lng));
            }
        });

        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                map.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location == null){
                           show_info_lay("QR Code Generator Can't Find Device Location");
                        }
                        if (location != null) {
                            // Logic to handle location object

                            // Add a marker in Sydney and move the camera
                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        }
                    }
                });



    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(QRmakerActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }





    public void getLocationPermission(){
        String [] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;


            } else {
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void Switch_Visibility(){
        btn_amakeqr_text.setVisibility(View.VISIBLE);
        btn_makeqr_contact.setVisibility(View.VISIBLE);
        btn_makeqr_email.setVisibility(View.VISIBLE);
        btn_makeqr_location.setVisibility(View.VISIBLE);
        btn_makeqr_telephone.setVisibility(View.VISIBLE);
        btn_makeqr_url.setVisibility(View.VISIBLE);
        btn_makeqr_wifi.setVisibility(View.VISIBLE);

        lay_maker_but1.setVisibility(View.VISIBLE);
        lay_maker_but2.setVisibility(View.VISIBLE);
        lay_maker_but3.setVisibility(View.VISIBLE);
        lay_maker_but4.setVisibility(View.VISIBLE);


        lay_qr_text.setVisibility(View.GONE);
        lay_qr_contact.setVisibility(View.GONE);
        lay_qr_email.setVisibility(View.GONE);
        lay_qr_location.setVisibility(View.GONE);
        lay_qr_telephone.setVisibility(View.GONE);
        lay_qr_url.setVisibility(View.GONE);
        lay_qr_wifi.setVisibility(View.GONE);

        btn_generate_qr.setVisibility(View.GONE);
    }



    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(QRmakerActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Switch_Visibility();
        this.doubleBackToExitPressedOnce = true;

       show_info_lay("Press Twice to Scan");
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