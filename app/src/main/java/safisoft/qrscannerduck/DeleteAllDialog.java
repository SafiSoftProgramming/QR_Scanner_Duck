package safisoft.qrscannerduck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;


public class DeleteAllDialog  extends Dialog implements
        android.view.View.OnClickListener {

    public ImageButton btn_delete_all_yes, btn_delete_all_cancel;
    public Activity c;

    public DeleteAllDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.deleteall_dialog);
        btn_delete_all_yes = findViewById(R.id.btn_delete_all_yes);
        btn_delete_all_cancel = findViewById(R.id.btn_delete_all_cancel);
        btn_delete_all_yes.setOnClickListener(this);
        btn_delete_all_cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete_all_yes:
                c.finish();
                break;
            case R.id.btn_delete_all_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}
