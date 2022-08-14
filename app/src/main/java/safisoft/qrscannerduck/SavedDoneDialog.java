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


public class SavedDoneDialog extends Dialog implements
        View.OnClickListener {

    public ImageButton btn_ok;
    public Activity c;


    public SavedDoneDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.saved_done_dialog);

        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
