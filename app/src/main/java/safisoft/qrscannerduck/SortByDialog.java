package safisoft.qrscannerduck;

import android.app.Activity;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import java.io.IOException;


public class SortByDialog extends Dialog implements
        View.OnClickListener {

    public ImageButton btn_sort_by_date, btn_sort_by_type , btn_sort_by_name ,btn_sort_ok;
    public Activity c;
    DbConnction db ;

    public SortByDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sortby_dialog);

        db = new DbConnction(c);
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


        btn_sort_by_date = findViewById(R.id.btn_sort_by_date);
        btn_sort_by_type = findViewById(R.id.btn_sort_by_type);
        btn_sort_by_name = findViewById(R.id.btn_sort_by_name);
        btn_sort_ok = findViewById(R.id.btn_sort_ok);


        btn_sort_by_date.setOnClickListener(this);
        btn_sort_by_type.setOnClickListener(this);
        btn_sort_by_name.setOnClickListener(this);
        btn_sort_ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
