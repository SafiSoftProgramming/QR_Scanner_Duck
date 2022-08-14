package safisoft.qrscannerduck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DbConnction db ;
    List<QR_Data> QR_List;
    Cursor c = null;
    private AdView adView_Banner;
    ImageButton btn_DuckScan , btn_Settings , btn_Qrcode_Maker , btn_History , btn_Fav ;
    String SHOW_ONLY_FAV = "";
    EditText edtxt_search ;
    ImageButton btn_Delete_all;
    LinearLayout lay_btns ;
    RelativeLayout lay_main ;
    CardView card ;
    DeleteAllDialog deleteAllDialog ;
    SortByDialog sortByDialog ;
    QR_Adapter QR ;
    ImageView img_no_data ;
    ImageButton btn_sort ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView_Banner = findViewById(R.id.adView_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Banner.loadAd(adRequest);

        SHOW_ONLY_FAV = getIntent().getStringExtra("SHOW_ONLY_FAV");

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        edtxt_search = findViewById(R.id.edtxt_search);
        lay_btns = findViewById(R.id.lay_btns);
        lay_main = findViewById(R.id.lay_main);
        card = findViewById(R.id.card);
        btn_Delete_all = findViewById(R.id.btn_Delete_all);
        img_no_data = findViewById(R.id.img_no_data);
        btn_sort  = findViewById(R.id.btn_sort);

        btn_DuckScan = findViewById(R.id.btn_DuckScan);
        btn_Settings = findViewById(R.id.btn_Settings);
        btn_Qrcode_Maker = findViewById(R.id.btn_Qrcode_Maker);
        btn_History = findViewById(R.id.btn_History);
        btn_Fav = findViewById(R.id.btn_Fav);

      //  setupUI(lay_main);


        db = new DbConnction(HistoryActivity.this);
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



        btn_DuckScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Qrcode_Maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, QRmakerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });
        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","show_only_fav" );
                startActivity(intent);
                finish();
            }
        });




        QR_List = new ArrayList<>();


        c = db.Row_Query("qr_sort","_id","1");
        c.moveToFirst();
        String sort = c.getString(1);
        if(sort.equals("date")){
            c =  db.sortRecord_id();
        }
        if(sort.equals("name")){
            c =  db.sortRecord_name();
        }
        if(sort.equals("type")){
            c =  db.sortRecord_type();
        }

        c.moveToFirst();

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                QR_List.add(new QR_Data(c.getInt(0),c.getString(23),c.getString(25),c.getString(2),c.getString(24),SHOW_ONLY_FAV));
            }while (c.moveToNext());
        }
        QR = new QR_Adapter(HistoryActivity.this,QR_List);
        recyclerView.setAdapter(QR);
        if(QR_List.isEmpty()){
            img_no_data.setVisibility(View.VISIBLE);
        }
        else {
            img_no_data.setVisibility(View.GONE);
        }



        edtxt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                c = db.Search_Query("qr_data","val1",edtxt_search.getText().toString());
                c.moveToFirst();
                // looping through all rows and adding to list
                if (c.moveToFirst()) {
                    QR_List.clear();
                    do {
                        QR_List.add(new QR_Data(c.getInt(0),c.getString(23),c.getString(25),c.getString(2),c.getString(24),SHOW_ONLY_FAV));
                    }while (c.moveToNext());
                }
                recyclerView.setAdapter(QR);
                QR.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_Delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllDialog = new DeleteAllDialog(HistoryActivity.this);
                deleteAllDialog.show();
                deleteAllDialog.setCanceledOnTouchOutside(false);
                deleteAllDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                deleteAllDialog.btn_delete_all_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteAllRecord("qr_data");
                        QR_List.clear();
                        recyclerView.setAdapter(QR);
                        QR.notifyDataSetChanged();
                        img_no_data.setVisibility(View.VISIBLE);
                        deleteAllDialog.dismiss();
                    }
                });

                deleteAllDialog.btn_delete_all_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAllDialog.dismiss();
                    }
                });
            }
        });

        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortByDialog = new SortByDialog(HistoryActivity.this);
                sortByDialog.show();
                sortByDialog.setCanceledOnTouchOutside(false);
                sortByDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

               c = db.Row_Query("qr_sort","_id","1");
               c.moveToFirst();
               String sort = c.getString(1);
               if(sort.equals("date")){
                   sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_no);
                   sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_no);
                   sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_yes);
                   c =  db.sortRecord_id();
               }
                if(sort.equals("name")){
                    sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_no);
                    sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_yes);
                    sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_no);
                    c =  db.sortRecord_name();
                }
                if(sort.equals("type")){
                    sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_yes);
                    sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_no);
                    sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_no);
                    c =  db.sortRecord_type();
                }


                sortByDialog.btn_sort_by_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_no);
                        sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_no);
                        sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_yes);
                        c =  db.sortRecord_id();
                        db.updateRecord("qr_sort","sort","1","date");
                    }
                });

                sortByDialog.btn_sort_by_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_no);
                        sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_yes);
                        sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_no);
                        c =  db.sortRecord_name();
                        db.updateRecord("qr_sort","sort","1","name");
                    }
                });

                sortByDialog.btn_sort_by_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortByDialog.btn_sort_by_type.setBackgroundResource(R.drawable.ic_sort_type_yes);
                        sortByDialog.btn_sort_by_name.setBackgroundResource(R.drawable.ic_sort_name_no);
                        sortByDialog.btn_sort_by_date.setBackgroundResource(R.drawable.ic_sort_date_no);
                        c =  db.sortRecord_type();
                        db.updateRecord("qr_sort","sort","1","type");
                    }
                });

                sortByDialog.btn_sort_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c.moveToFirst();
                        // looping through all rows and adding to list
                        if (c.moveToFirst()) {
                            QR_List.clear();
                            do {
                                QR_List.add(new QR_Data(c.getInt(0),c.getString(23),c.getString(25),c.getString(2),c.getString(24),SHOW_ONLY_FAV));
                            }while (c.moveToNext());
                        }
                        recyclerView.setAdapter(QR);
                        QR.notifyDataSetChanged();
                        sortByDialog.dismiss();
                    }
                });

            }
        });



    }





    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(HistoryActivity.this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HistoryActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }








}
