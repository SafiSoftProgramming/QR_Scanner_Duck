package safisoft.qrscannerduck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    ImageButton btn_DuckScan , btn_Settings , btn_Qrcode_Maker , btn_History , btn_Fav ;
    ImageButton btn_info_rateme , btn_info_apps , btn_info_facebook , btn_info_feesback , btn_info_permissions , btn_info_youtube ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_DuckScan = findViewById(R.id.btn_DuckScan);
        btn_Settings = findViewById(R.id.btn_Settings);
        btn_Qrcode_Maker = findViewById(R.id.btn_Qrcode_Maker);
        btn_History = findViewById(R.id.btn_History);
        btn_Fav = findViewById(R.id.btn_Fav);

        btn_info_rateme = findViewById(R.id.btn_info_rateme);
        btn_info_apps = findViewById(R.id.btn_info_apps);
        btn_info_facebook = findViewById(R.id.btn_info_facebook);
        btn_info_feesback = findViewById(R.id.btn_info_feesback);
        btn_info_permissions = findViewById(R.id.btn_info_permissions);
        btn_info_youtube = findViewById(R.id.btn_info_youtube);


        btn_DuckScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_Qrcode_Maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, QRmakerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","NO" );
                startActivity(intent);
                finish();
            }
        });
        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HistoryActivity.class);
                intent.putExtra("SHOW_ONLY_FAV","show_only_fav" );
                startActivity(intent);
                finish();
            }
        });





        btn_info_rateme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });
        btn_info_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SafiSoft")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SafiSoft")));
                }

            }
        });
        btn_info_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/301343817018905"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SafiSoft.programming")));
                }

            }
        });
        btn_info_feesback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:SafiSoft.programmer@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "QR Scanner Duck User Feedback");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
        btn_info_permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppPermissionsDialog appPermissionsDialog = new AppPermissionsDialog(SettingsActivity.this);
                appPermissionsDialog.show();
                appPermissionsDialog.setCanceledOnTouchOutside(false);
                appPermissionsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                appPermissionsDialog.btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appPermissionsDialog.dismiss();
                    }
                });


            }
        });

        btn_info_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCtC8eqUUZmktsUoFznAL91w")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCtC8eqUUZmktsUoFznAL91w")));
                }

            }
        });



    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}