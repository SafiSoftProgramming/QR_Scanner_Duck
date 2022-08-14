package safisoft.qrscannerduck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ManageResultsActivity extends AppCompatActivity {
    TextView txt_qrcode_val;
    Barcode QR_CODE  ;
    int QR_FORMAT ;
    private AdView adView_Banner;
    ImageView img_scan_type ;
    TextView txt_scan_type ;
    TextView txt_scan_date_time ;
    LinearLayout lay_click_copy ;
    ImageButton btn_DuckScan , btn_Settings , btn_Qrcode_Maker , btn_History , btn_Fav ;
    ImageButton btn_fav ;
    ImageButton btn_delete ;
    ImageButton btn_share ;
    TextView txt_lay_info  ;
    DbConnction db ;
    Cursor c = null;
    String date_time_String = "";
    boolean fav_state = false ;
    boolean fav_state_from_database = false ;
    String RECORD_ID_FROM_ADAPTER = " ";



    String Contact_first_name ="";
    String Contact_middle_name ="";
    String Contact_last_name ="";
    String Contact_organization ="";
    String Contact_title ="";
    String Contact_phones ="";
    String Contact_phones1 ="";
    String Contact_phones2 ="";
    String Contact_url = "";
    String Contact_email ="";
    String Contact_addressLine ="";

    String Email_address ="";
    String Email_subject ="";
    String Email_body ="";
    int Email_type ;

    String Book_num ="";

    String Phone_num = "";
    int Phone_type ;

    String Product_txt = "";

    String SMS_phone_number = "";
    String SMS_message = "";

    String URL_url = "";

    String WIFI_name = "";
    String WIFI_password = "";
    int WIFI_type ;
    String WIFI_type_St = "";

    Double GEO_lat ;
    Double GEO_lng ;

    String Event_summary = "";
    String Event_organizer = "";
    String Event_location = "";
    String Event_description = "";
    String Event_status = "";
    String Event_start = "";
    String Event_end = "";
    Date Event_start_data ;
    Date Event_end_data ;

    String des1 ="";
    String val1 ="";
    String des2 ="";
    String val2 ="";
    String des3 ="";
    String val3 ="";
    String des4 ="";
    String val4 ="";
    String des5 ="";
    String val5 ="";
    String des6 ="";
    String val6 ="";
    String des7 ="";
    String val7 ="";
    String des8 ="";
    String val8 ="";
    String des9 ="";
    String val9 ="";
    String des10 ="";
    String val10 ="";
    String des11 ="";
    String val11 ="";



    LinearLayout lay_data1,lay_data2,lay_data3,lay_data4,lay_data5,lay_data6,lay_data7,lay_data8,lay_data9,lay_data10,lay_data11;
    TextView txt_des1,txt_des2,txt_des3,txt_des4,txt_des5,txt_des6,txt_des7,txt_des8,txt_des9,txt_des10,txt_des11;
    TextView txt_val1,txt_val2,txt_val3,txt_val4,txt_val5,txt_val6,txt_val7,txt_val8,txt_val9,txt_val10,txt_val11;
    ImageButton btn_add_contact , btn_add_email , btn_add_copy , btn_add_telephone , btn_add_find, btn_add_message , btn_add_url , btn_add_wifi , btn_add_map , btn_add_calendar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_results);
        QR_CODE = getIntent().getParcelableExtra("QR_CODE");
        QR_FORMAT = getIntent().getIntExtra("QR_FORMAT",0);
        RECORD_ID_FROM_ADAPTER = getIntent().getStringExtra("RECORD_ID_FROM_ADAPTER");
        img_scan_type = findViewById(R.id.img_scan_type);
        txt_scan_type = findViewById(R.id.txt_scan_type);
        txt_scan_date_time = findViewById(R.id.txt_scan_date_time);
        lay_click_copy = findViewById(R.id.lay_click_exit);
        btn_add_contact = findViewById(R.id.btn_add_contact);
        btn_add_email = findViewById(R.id.btn_add_email);
        btn_add_copy = findViewById(R.id.btn_add_copy);
        btn_add_telephone = findViewById(R.id.btn_add_telephone);
        btn_add_find = findViewById(R.id.btn_add_find);
        btn_add_message = findViewById(R.id.btn_add_message);
        btn_add_url = findViewById(R.id.btn_add_url);
        btn_add_wifi = findViewById(R.id.btn_add_wifi);
        btn_add_map = findViewById(R.id.btn_add_map);
        btn_add_calendar = findViewById(R.id.btn_add_calendar);
        txt_lay_info = findViewById(R.id.txt_lay_info);
        btn_fav = findViewById(R.id.btn_fav);
        btn_delete = findViewById(R.id.btn_delete);
        btn_share = findViewById(R.id.btn_share);

        btn_DuckScan = findViewById(R.id.btn_DuckScan);
        btn_Settings = findViewById(R.id.btn_Settings);
        btn_Qrcode_Maker = findViewById(R.id.btn_Qrcode_Maker);
        btn_History = findViewById(R.id.btn_History);
        btn_Fav = findViewById(R.id.btn_Fav);


        lay_data1 = findViewById(R.id.lay_data1);
        lay_data2 = findViewById(R.id.lay_data2);
        lay_data3 = findViewById(R.id.lay_data3);
        lay_data4 = findViewById(R.id.lay_data4);
        lay_data5 = findViewById(R.id.lay_data5);
        lay_data6 = findViewById(R.id.lay_data6);
        lay_data7 = findViewById(R.id.lay_data7);
        lay_data8 = findViewById(R.id.lay_data8);
        lay_data9 = findViewById(R.id.lay_data9);
        lay_data10 = findViewById(R.id.lay_data10);
        lay_data11 = findViewById(R.id.lay_data11);

        txt_des1 = findViewById(R.id.txt_des1);
        txt_des2 = findViewById(R.id.txt_des2);
        txt_des3 = findViewById(R.id.txt_des3);
        txt_des4 = findViewById(R.id.txt_des4);
        txt_des5 = findViewById(R.id.txt_des5);
        txt_des6 = findViewById(R.id.txt_des6);
        txt_des7 = findViewById(R.id.txt_des7);
        txt_des8 = findViewById(R.id.txt_des8);
        txt_des9 = findViewById(R.id.txt_des9);
        txt_des10 = findViewById(R.id.txt_des10);
        txt_des11 = findViewById(R.id.txt_des11);

        txt_val1 = findViewById(R.id.txt_val1);
        txt_val2 = findViewById(R.id.txt_val2);
        txt_val3 = findViewById(R.id.txt_val3);
        txt_val4 = findViewById(R.id.txt_val4);
        txt_val5 = findViewById(R.id.txt_val5);
        txt_val6 = findViewById(R.id.txt_val6);
        txt_val7 = findViewById(R.id.txt_val7);
        txt_val8 = findViewById(R.id.txt_val8);
        txt_val9 = findViewById(R.id.txt_val9);
        txt_val10 = findViewById(R.id.txt_val10);
        txt_val11 = findViewById(R.id.txt_val11);


        db = new DbConnction(ManageResultsActivity.this);
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }



        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        date_time_String = sdf.format(date);
        txt_scan_date_time.setText(date_time_String);


        // get qr code format (text,phone,wifi ......)
        QR_FORMAT(QR_FORMAT);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView_Banner = findViewById(R.id.adView_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Banner.loadAd(adRequest);


        btn_DuckScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageResultsActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageResultsActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Qrcode_Maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageResultsActivity.this, QRmakerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageResultsActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });
        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageResultsActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","show_only_fav" );
                startActivity(intent);
                finish();
            }
        });


        if(!RECORD_ID_FROM_ADAPTER.equals("no")){
            c = db.Row_Query("qr_data","_id",RECORD_ID_FROM_ADAPTER);
            c.moveToFirst();
             des1 =c.getString(1);
             val1 =c.getString(2);
             des2 =c.getString(3);
             val2 =c.getString(4);
             des3 =c.getString(5);
             val3 =c.getString(6);
             des4 =c.getString(7);
             val4 =c.getString(8);
             des5 =c.getString(9);
             val5 =c.getString(10);
             des6 =c.getString(11);
             val6 =c.getString(12);
             des7 =c.getString(13);
             val7 =c.getString(14);
             des8 =c.getString(15);
             val8 =c.getString(16);
             des9 =c.getString(17);
             val9 =c.getString(18);
             des10 =c.getString(19);
             val10 =c.getString(20);
             des11 =c.getString(21);
             val11 =c.getString(22);
            show_data( des1 ,  val1 ,  des2 ,  val2 ,  des3 ,  val3 ,  des4 ,  val4 ,  des5 ,  val5 ,  des6 ,  val6
                    ,  des7 ,  val7 ,  des8 ,  val8 ,  des9 ,  val9 ,  des10 ,  val10 ,  des11 ,  val11);

            QR_FORMAT_DataBase(Integer.parseInt(c.getString(23)));

            txt_scan_date_time.setText(c.getString(25));

            String fav_state = c.getString(24);
            if (fav_state.equals("yes")){
                btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose);
                fav_state_from_database = true ;
            }
            else {
                btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose_not);
                fav_state_from_database = false ;
            }

        }




        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(
                            ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                            ContactsContract.Contacts.CONTENT_URI);
                    if(Contact_phones.equals("")){Contact_phones = " ";}
                    intent.setData(Uri.parse("tel:"+Contact_phones));
                    String con_name = Contact_first_name+" "+Contact_middle_name+" "+Contact_last_name ;
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, con_name.trim() );
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, Contact_organization);
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, Contact_title);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, Contact_phones1);
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, Contact_phones2);
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, "HOME");
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, Contact_url);
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Contact_email);
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, Contact_addressLine);
                    startActivity(intent);
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Contacts");
                }


            }
        });

        btn_add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[] {Email_address});
                    intent.putExtra(Intent.EXTRA_SUBJECT, Email_subject);
                    intent.putExtra(Intent.EXTRA_TEXT, Email_body);
                    startActivity(Intent.createChooser(intent, "choose one"));
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Emails");
                }

            }
        });

        btn_add_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(txt_val1.getText());
                    show_info_lay("Text Copied To Clipboard");
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Clipboard");
                }

            }
        });

        btn_add_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+txt_val1.getText()));
                    startActivity(intent);
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Telephone");
                }

            }
        });

        btn_add_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(txt_val1.getText().toString(), "UTF-8");
                    Uri uri = Uri.parse("http://www.google.com.vn/search?q=" + escapedQuery);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    show_info_lay("No App found to handle Search");
                }

            }
        });

        btn_add_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address",SMS_phone_number);
                    smsIntent.putExtra("sms_body",SMS_message);
                    smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(smsIntent);
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Messages");
                }

            }
        });

        btn_add_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txt_val1.getText().toString()));
                    startActivity(browserIntent);
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Url");
                }

            }
        });

        btn_add_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", WIFI_name);
                    wifiConfig.preSharedKey = String.format("\"%s\"", WIFI_password);

                    WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                    //remember id
                    int netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Wifi");
                }

            }
        });

        btn_add_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QR_FORMAT == 10) {
                    String uri = "http://maps.google.com/maps?q=loc:" + Double.toString(GEO_lat) + "," + Double.toString(GEO_lng);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    try {
                        startActivity(mapIntent);
                    } catch (ActivityNotFoundException ex) {
                        show_info_lay("Please install a maps application");
                    }
                }

                if (QR_FORMAT == 8) {
                    String uri = txt_val1.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.maps.MapsActivity");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        try {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(unrestrictedIntent);
                        } catch (ActivityNotFoundException innerEx) {
                            show_info_lay("Please install a maps application");
                        }
                    }
                }
            }
        });

        btn_add_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.Events.CALENDAR_ID, 1);
                    intent.putExtra(CalendarContract.Events.TITLE, Event_summary);
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, Event_description);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Event_start_data.getTime());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Event_end_data.getTime());
                    intent.putExtra(CalendarContract.Events.ORGANIZER,Event_organizer);
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, Event_location);
                    intent.putExtra(CalendarContract.Events.STATUS, Event_status);
                    startActivity(intent);
                }
                catch (Exception e){
                    show_info_lay("No App found to handle Calendar");
                }

            }
        });

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!RECORD_ID_FROM_ADAPTER.equals("no")){
                    if (!fav_state_from_database) {
                        db.updateRecord("qr_data", "fav", RECORD_ID_FROM_ADAPTER, "yes");
                        btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose);
                        fav_state_from_database = true;
                    } else if (fav_state_from_database) {
                        db.updateRecord("qr_data", "fav", RECORD_ID_FROM_ADAPTER, "no");
                        btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose_not);
                        fav_state_from_database = false;
                    }

                }
                else {

                    if (!fav_state) {
                        c = db.Query_Data("qr_data", null, null, null, null, null, null);
                        c.moveToLast();
                        db.updateRecord("qr_data", "fav", Integer.toString(c.getInt(0)), "yes");
                        btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose);
                        fav_state = true;
                    } else if (fav_state) {
                        c = db.Query_Data("qr_data", null, null, null, null, null, null);
                        c.moveToLast();
                        db.updateRecord("qr_data", "fav", Integer.toString(c.getInt(0)), "no");
                        btn_fav.setBackgroundResource(R.drawable.btn_eff_fav_choose_not);
                        fav_state = false;
                    }
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!RECORD_ID_FROM_ADAPTER.equals("no")){
                    db.deleteRecordAlternate("qr_data", "_id", RECORD_ID_FROM_ADAPTER);
                    Toast.makeText(ManageResultsActivity.this, "QR Code Info Deleted", Toast.LENGTH_SHORT).show();
                    show_info_lay("QR Code Info Deleted");
                }
                else {
                    c = db.Query_Data("qr_data", null, null, null, null, null, null);
                    c.moveToLast();
                    db.deleteRecordAlternate("qr_data", "_id", Integer.toString(c.getInt(0)));
                    Toast.makeText(ManageResultsActivity.this, "QR Code Info Deleted", Toast.LENGTH_SHORT).show();
                    show_info_lay("QR Code Info Deleted");
                }
                Intent intent = new Intent(ManageResultsActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                if(!RECORD_ID_FROM_ADAPTER.equals("no")){
                    c = db.Row_Query("qr_data","_id",RECORD_ID_FROM_ADAPTER);
                    c.moveToFirst();
                    String raw_value =c.getString(26);
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, raw_value);
                }
                else {
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, QR_CODE.rawValue);
                }
                startActivity(shareIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageResultsActivity.this, HistoryActivity.class);
        intent.putExtra("SHOW_ONLY_FAV","NO" );
        startActivity(intent);
        finish();
    }



    private void QR_FORMAT_DataBase(int format) {

        if (format == 1) {
            QR_FORMAT = 1 ;
            btn_add_contact.setVisibility(View.VISIBLE);
            txt_scan_type.setText("Contact info");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_contact);

            Contact_first_name = val1;
            Contact_middle_name = val2;
            Contact_last_name = val3;
            Contact_organization = val4;
            Contact_title = val5;
            Contact_phones = val6;
            Contact_phones1 = val7;
            Contact_phones2 = val8;
            Contact_email = val9;
            Contact_url = val10;
            Contact_addressLine = val11;
        }

        if (format == 2){
            QR_FORMAT = 2 ;
                txt_scan_type.setText("Email");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_email);
                btn_add_email.setVisibility(View.VISIBLE);

                Email_address = val1 ;
                Email_subject = val2 ;
                Email_body = val3 ;

        }

        if (format == 3){
            QR_FORMAT = 3 ;
                txt_scan_type.setText("Book Number");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_book_num);
                btn_add_copy.setVisibility(View.VISIBLE);
                btn_add_find.setVisibility(View.VISIBLE);
                Book_num = val1 ;

        }

        if (format == 4){
            QR_FORMAT = 4 ;
                txt_scan_type.setText("Phone");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_phone);
                btn_add_telephone.setVisibility(View.VISIBLE);
                btn_add_copy.setVisibility(View.VISIBLE);
                Phone_num = val1 ;
        }

        if (format == 5){
            QR_FORMAT = 5 ;
                txt_scan_type.setText("Product");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_product);
                btn_add_copy.setVisibility(View.VISIBLE);
                btn_add_find.setVisibility(View.VISIBLE);

                Product_txt = val1 ;
        }

        if (format == 6){
            QR_FORMAT = 6 ;
                txt_scan_type.setText("SMS");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_sms);
                btn_add_message.setVisibility(View.VISIBLE);

                SMS_phone_number = val1;
                SMS_message = val2;

        }
        if (format == 7){
            QR_FORMAT = 7 ;
                txt_scan_type.setText("Text");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_text);
                btn_add_message.setVisibility(View.VISIBLE);
                btn_add_copy.setVisibility(View.VISIBLE);
                btn_add_find.setVisibility(View.VISIBLE);
                String TEXT_txt = val1;

        }
        if (format == 8){
            QR_FORMAT = 8 ;
            if(val1.contains("maps.google.com")){
                txt_scan_type.setText("Location");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
                btn_add_map.setVisibility(View.VISIBLE);
                URL_url = val1;

            }
            else {
                txt_scan_type.setText("Url");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_url);
                btn_add_copy.setVisibility(View.VISIBLE);
                btn_add_url.setVisibility(View.VISIBLE);
                URL_url = val1;

            }
        }
        if (format == 9){
            QR_FORMAT = 9 ;
                txt_scan_type.setText("Wifi");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_wifi);
                btn_add_wifi.setVisibility(View.VISIBLE);
                WIFI_name =val1 ;
                WIFI_password = val2 ;
                if(WIFI_type == 1){WIFI_type_St = "OPEN";}
                if(WIFI_type == 2){WIFI_type_St = "WPA/WPA2";}
                if(WIFI_type == 3){WIFI_type_St = "WEP";}

        }
        if (format == 10){
            QR_FORMAT = 10 ;
                txt_scan_type.setText("Location");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
                btn_add_map.setVisibility(View.VISIBLE);
                GEO_lat = Double.parseDouble(val1) ;
                GEO_lng = Double.parseDouble(val2) ;
        }
        if (format == 11){
            QR_FORMAT = 11 ;
                txt_scan_type.setText("Calendar");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_calender);
                btn_add_calendar.setVisibility(View.VISIBLE);
                Event_summary = val1;
                Event_organizer = val2;
                Event_location = val3;
                Event_description = val4;
                Event_status = val5;
            try {
                SimpleDateFormat sdfw = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                Event_start_data = sdfw.parse(val6);
                Event_end_data = sdfw.parse(val7);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        if (format == 12){
            QR_FORMAT = 12 ;
                txt_scan_type.setText("Driver License");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_license);
                String firstName = QR_CODE.driverLicense.firstName;
                String middleName = QR_CODE.driverLicense.middleName ;
                String lastName = QR_CODE.driverLicense.lastName ;
                String gender = QR_CODE.driverLicense.gender ;
                String birthDate = QR_CODE.driverLicense.birthDate ;
                String issuingCountry = QR_CODE.driverLicense.issuingCountry ;
                String issueDate = QR_CODE.driverLicense.issueDate ;
                String expiryDate = QR_CODE.driverLicense.expiryDate ;
                String licenseNumber = QR_CODE.driverLicense.licenseNumber ;
                String address = QR_CODE.driverLicense.addressStreet + QR_CODE.driverLicense.addressCity + QR_CODE.driverLicense.addressState ;

        }
    }


    public void show_data(String des1 , String val1 , String des2 , String val2 , String des3 , String val3 , String des4 , String val4 , String des5 , String val5 , String des6 , String val6
            , String des7 , String val7 , String des8 , String val8 , String des9 , String val9 , String des10 , String val10 , String des11 , String val11){


        if(!val1.equals("")){
            txt_des1.setText(des1);
            txt_val1.setText(val1);
            lay_data1.setVisibility(View.VISIBLE);
        }

        if(!val2.equals("")){
            txt_des2.setText(des2);
            txt_val2.setText(val2);
            lay_data2.setVisibility(View.VISIBLE);
        }

        if(!val3.equals("")){
            txt_des3.setText(des3);
            txt_val3.setText(val3);
            lay_data3.setVisibility(View.VISIBLE);
        }

        if(!val4.equals("")){
            txt_des4.setText(des4);
            txt_val4.setText(val4);
            lay_data4.setVisibility(View.VISIBLE);
        }

        if(!val5.equals("")){
            txt_des5.setText(des5);
            txt_val5.setText(val5);
            lay_data5.setVisibility(View.VISIBLE);
        }

        if(!val6.equals("")){
            txt_des6.setText(des6);
            txt_val6.setText(val6);
            lay_data6.setVisibility(View.VISIBLE);
        }

        if(!val7.equals("")){
            txt_des7.setText(des7);
            txt_val7.setText(val7);
            lay_data7.setVisibility(View.VISIBLE);
        }

        if(!val8.equals("")){
            txt_des8.setText(des8);
            txt_val8.setText(val8);
            lay_data8.setVisibility(View.VISIBLE);
        }

        if(!val9.equals("")){
            txt_des9.setText(des9);
            txt_val9.setText(val9);
            lay_data9.setVisibility(View.VISIBLE);
        }

        if(!val10.equals("")){
            txt_des10.setText(des10);
            txt_val10.setText(val10);
            lay_data10.setVisibility(View.VISIBLE);
        }

        if(!val11.equals("")){
            txt_des11.setText(des11);
            txt_val11.setText(val11);
            lay_data11.setVisibility(View.VISIBLE);
        }



    }


    private void QR_FORMAT(int format) {

        if (format == 1) {
            btn_add_contact.setVisibility(View.VISIBLE);
            txt_scan_type.setText("Contact info");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_contact);
            Contact_first_name = QR_CODE.contactInfo.name.first;
            Contact_middle_name = QR_CODE.contactInfo.name.middle;
            Contact_last_name = QR_CODE.contactInfo.name.last;
            if(Contact_first_name.isEmpty()){
                Contact_first_name = Contact_middle_name +" "+Contact_last_name ;
                Contact_middle_name = "" ;
                Contact_last_name = "" ;
            }
            Contact_organization = QR_CODE.contactInfo.organization;
            Contact_title = QR_CODE.contactInfo.title;
            if(QR_CODE.contactInfo.phones.length > 0 ){
                Contact_phones = QR_CODE.contactInfo.phones[0].number;}
            if(QR_CODE.contactInfo.phones.length > 1){
                Contact_phones1 = QR_CODE.contactInfo.phones[1].number;}
            if(QR_CODE.contactInfo.phones.length > 2 ){
                Contact_phones2 = QR_CODE.contactInfo.phones[2].number;}
            if(QR_CODE.contactInfo.emails.length > 0 ){
                Contact_email = QR_CODE.contactInfo.emails[0].address;}
            if(QR_CODE.contactInfo.urls.length > 0 ){
                Contact_url = QR_CODE.contactInfo.urls[0];}
            if(QR_CODE.contactInfo.addresses.length > 0 ){
                Contact_addressLine = QR_CODE.contactInfo.addresses[0].addressLines[0];}

            show_data("First Name", Contact_first_name,"Middle Name", Contact_middle_name,"Last Name", Contact_last_name,"Organization", Contact_organization,"Title", Contact_title,"Phone (Work)", Contact_phones,"Phone (Private)", Contact_phones1,
                    "Phone (Mobile)", Contact_phones2,"Email", Contact_email,"Website", Contact_url,"Address", Contact_addressLine);
            db.insertRecord("First Name", Contact_first_name,"Middle Name", Contact_middle_name,"Last Name", Contact_last_name,"Organization", Contact_organization,"Title", Contact_title,"Phone (Work)", Contact_phones,"Phone (Private)", Contact_phones1,
                    "Phone (Mobile)", Contact_phones2,"Email", Contact_email,"Website", Contact_url,"Address", Contact_addressLine , Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);

        }

        if (format == 2){
            txt_scan_type.setText("Email");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_email);
            btn_add_email.setVisibility(View.VISIBLE);
            Email_address = QR_CODE.email.address ;
            Email_subject = QR_CODE.email.subject ;
            Email_body = QR_CODE.email.body ;
            Email_type = QR_CODE.email.type ;

            show_data("Email Address",Email_address,"Email Subject",Email_subject,"Email Body",Email_body,"","","","","","","","","","",
                    "","","","","","");
            db.insertRecord("Email Address",Email_address,"Email Subject",Email_subject,"Email Body",Email_body,"","","","","","","","","","",
                    "","","","","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }

        if (format == 3){
            txt_scan_type.setText("Book Number");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_book_num);
            btn_add_copy.setVisibility(View.VISIBLE);
            btn_add_find.setVisibility(View.VISIBLE);
            Book_num = QR_CODE.displayValue ;
            show_data("Book Number",Book_num,"","","","","","","","","","","","","","","","","",
                    "","","");
            db.insertRecord("Book Number",Book_num,"","","","","","","","","","","","","","","","","",
                    "","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }

        if (format == 4){
            txt_scan_type.setText("Phone");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_phone);
            btn_add_telephone.setVisibility(View.VISIBLE);
            btn_add_copy.setVisibility(View.VISIBLE);
            Phone_num = QR_CODE.phone.number ;
            Phone_type = QR_CODE.phone.type ;
            show_data("Phone Number",Phone_num,"","","","","","","","","","","","","","","","",""
                    ,"","","");
            db.insertRecord("Phone Number",Phone_num,"","","","","","","","","","","","","","","","",""
                    ,"","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }

        if (format == 5){
            txt_scan_type.setText("Product");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_product);
            btn_add_copy.setVisibility(View.VISIBLE);
            btn_add_find.setVisibility(View.VISIBLE);
            Product_txt = QR_CODE.displayValue ;
            show_data("Product",Product_txt,"","","","","","","","","","","","","","","","","","","","");
            db.insertRecord("Product",Product_txt,"","","","","","","","","","","","","","","","",""
                    ,"","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }

        if (format == 6){
            txt_scan_type.setText("SMS");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_sms);
            btn_add_message.setVisibility(View.VISIBLE);
            SMS_phone_number = QR_CODE.sms.phoneNumber;
            SMS_message = QR_CODE.sms.message;
            show_data("SMS Phone Number",SMS_phone_number,"SMS Body",SMS_message,"","","","","","","","","","","","","",
                    "","","","","");
            db.insertRecord("SMS Phone Number",SMS_phone_number,"SMS Body",SMS_message,"","","","","","","","","","","","","",
                    "","","","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }
        if (format == 7){
            txt_scan_type.setText("Text");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_text);
            btn_add_message.setVisibility(View.VISIBLE);
            btn_add_copy.setVisibility(View.VISIBLE);
            btn_add_find.setVisibility(View.VISIBLE);
            String TEXT_txt = QR_CODE.displayValue;
            show_data("Text",TEXT_txt,"","","","","","","","","","","","","","","","","","",
                    "","");
            db.insertRecord("Text",TEXT_txt,"","","","","","","","","","","","","","","","","","",
                    "","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }
        if (format == 8){
            if(QR_CODE.url.url.contains("maps.google.com")){
                txt_scan_type.setText("Location");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
                btn_add_map.setVisibility(View.VISIBLE);
                URL_url = QR_CODE.url.url;
                show_data("Map Link", URL_url, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "", "", "");
                db.insertRecord("Map Link", URL_url, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "", "", "", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
            }
            else {
                txt_scan_type.setText("Url");
                img_scan_type.setBackgroundResource(R.drawable.ic_scan_url);
                btn_add_copy.setVisibility(View.VISIBLE);
                btn_add_url.setVisibility(View.VISIBLE);
                URL_url = QR_CODE.url.url;
                show_data("Url", URL_url, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "", "", "");
                db.insertRecord("Url", URL_url, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "", "", "", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
            }
        }
        if (format == 9){
            txt_scan_type.setText("Wifi");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_wifi);
            btn_add_wifi.setVisibility(View.VISIBLE);
            WIFI_name = QR_CODE.wifi.ssid ;
            WIFI_password = QR_CODE.wifi.password ;
            WIFI_type = QR_CODE.wifi.encryptionType ;
            if(WIFI_type == 1){WIFI_type_St = "WPA/WPA2";}
            if(WIFI_type == 2){WIFI_type_St = "OPEN";}
            if(WIFI_type == 3){WIFI_type_St = "WEP";}
            show_data("SSID",WIFI_name,"Password",WIFI_password,"Type",WIFI_type_St,"","","","","","","","","","","","",
                    "","","","");
            db.insertRecord("SSID",WIFI_name,"Password",WIFI_password,"Type",WIFI_type_St,"","","","","","","","","","","","",
                    "","","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }
        if (format == 10){
            txt_scan_type.setText("Location");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
            btn_add_map.setVisibility(View.VISIBLE);
            GEO_lat = QR_CODE.geoPoint.lat ;
            GEO_lng = QR_CODE.geoPoint.lng ;
            show_data("Location latitude",Double.toString(GEO_lat),"Location longitude",Double.toString(GEO_lng),"","","","","","","","","",
                    "","","","","","","","","");
            db.insertRecord("Location latitude",Double.toString(GEO_lat),"Location longitude",Double.toString(GEO_lng),"","","","","","","","","",
                    "","","","","","","","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }
        if (format == 11){
            txt_scan_type.setText("Calendar");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_calender);
            btn_add_calendar.setVisibility(View.VISIBLE);
            Event_summary = QR_CODE.calendarEvent.summary;
            Event_organizer = QR_CODE.calendarEvent.organizer;
            Event_location = QR_CODE.calendarEvent.location;
            Event_description = QR_CODE.calendarEvent.description;
            Event_status = QR_CODE.calendarEvent.status;

            try {
                if(QR_CODE.calendarEvent.start.rawValue.contains("T") && !QR_CODE.calendarEvent.start.rawValue.contains("Z")){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                    Event_start_data = sdf.parse(QR_CODE.calendarEvent.start.rawValue);
                    Event_end_data = sdf.parse(QR_CODE.calendarEvent.end.rawValue);
                    sdf.setTimeZone(TimeZone.getDefault());
                    sdf.applyPattern("MMM MM dd, yyyy h:mm a");
                    Event_start = sdf.format(Event_start_data) ;
                    Event_end = sdf.format(Event_end_data) ;
                }
                if(QR_CODE.calendarEvent.start.rawValue.contains("T") && QR_CODE.calendarEvent.start.rawValue.contains("Z")){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // missing line
                    Event_start_data = sdf.parse(QR_CODE.calendarEvent.start.rawValue);
                    Event_end_data = sdf.parse(QR_CODE.calendarEvent.end.rawValue);
                    SimpleDateFormat sdfw = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                    sdf.setTimeZone(TimeZone.getDefault());
                    Event_start = sdfw.format(Event_start_data) ;
                    Event_end = sdfw.format(Event_end_data) ;
                }

            }catch (Exception e){
                System.out.println(e);
            }

            show_data("Summary", Event_summary,"Organizer",Event_organizer,"Location",Event_location,"Description",Event_description,"Status",Event_status,"Start Time",
                    Event_start,"End Time",Event_end,"","","","","","","","");
            db.insertRecord("Summary", Event_summary,"Organizer",Event_organizer,"Location",Event_location,"Description",Event_description,"Status",Event_status,"Start Time",
                    Event_start,"End Time",Event_end,"","","","","","","","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }

        if (format == 12){
            txt_scan_type.setText("Driver License");
            img_scan_type.setBackgroundResource(R.drawable.ic_scan_license);
            String firstName = QR_CODE.driverLicense.firstName;
            String middleName = QR_CODE.driverLicense.middleName ;
            String lastName = QR_CODE.driverLicense.lastName ;
            String gender = QR_CODE.driverLicense.gender ;
            String birthDate = QR_CODE.driverLicense.birthDate ;
            String issuingCountry = QR_CODE.driverLicense.issuingCountry ;
            String issueDate = QR_CODE.driverLicense.issueDate ;
            String expiryDate = QR_CODE.driverLicense.expiryDate ;
            String licenseNumber = QR_CODE.driverLicense.licenseNumber ;
            String address = QR_CODE.driverLicense.addressStreet + QR_CODE.driverLicense.addressCity + QR_CODE.driverLicense.addressState ;

            show_data("First Name",firstName,"Middle Name",middleName,"Last Name",lastName,"Gender",gender,"BirthDate",birthDate,"Issuing Country",issuingCountry,"Issue Date",issueDate,"Expiry Date",expiryDate,"License Number",licenseNumber,"Address",
                    address,"","");
            db.insertRecord("First Name",firstName,"Middle Name",middleName,"Last Name",lastName,"Gender",gender,"BirthDate",birthDate,"Issuing Country",issuingCountry,"Issue Date",issueDate,"Expiry Date",expiryDate,"License Number",licenseNumber,"Address",
                    address,"","", Integer.toString(QR_FORMAT) , "no" , date_time_String , QR_CODE.rawValue);
        }
    }

    public void show_info_lay (String Show_Text){
        lay_click_copy.setVisibility(View.VISIBLE);
        txt_lay_info.setText(Show_Text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lay_click_copy.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }



}

