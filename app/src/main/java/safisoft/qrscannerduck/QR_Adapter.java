package safisoft.qrscannerduck;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.Model;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class QR_Adapter extends RecyclerView.Adapter<QR_Adapter.QR_ViewHolder> {

    private Context mCtx;
    private List<QR_Data> QR_List;


    public QR_Adapter(Context mCtx, List<QR_Data> QR_List ) {
        this.mCtx = mCtx;
        this.QR_List = QR_List;

    }

    @NonNull
    @Override
    public QR_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list, null);
        return new QR_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QR_ViewHolder holder, int position) {
        QR_Data qrData = QR_List.get(position);
        holder.txt_scan_date_time.setText(qrData.get_Qr_date());
        holder.txt_scan_first_val.setText(qrData.get_Qr_first_vale());

        if(qrData.get_Qr_vaf().equals("yes")){
            holder.img_heart_fav.setVisibility(View.VISIBLE);
            holder.lay_list.setVisibility(View.VISIBLE);
        }
        else {
            holder.img_heart_fav.setVisibility(View.INVISIBLE);
            if(qrData.get_show_only_fav_vaf().equals("show_only_fav")) {
                holder.lay_list.setVisibility(View.GONE);
            }
        }

        String format = qrData.get_Qr_format() ;

        if (format.equals("1")) {
            holder.txt_scan_type.setText("Contact info");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_contact);
        }
        if (format.equals("2")){
            holder.txt_scan_type.setText("Email");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_email);
        }
        if (format.equals("3")){
            holder.txt_scan_type.setText("Book Number");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_book_num);
        }
        if (format.equals("4")){
            holder.txt_scan_type.setText("Phone");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_phone);
        }
        if (format.equals("5")){
            holder.txt_scan_type.setText("Product");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_product);
        }
        if (format.equals("6")){
            holder.txt_scan_type.setText("SMS");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_sms);
        }
        if (format.equals("7")){
            holder.txt_scan_type.setText("Text");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_text);
        }
        if (format.equals("8")){
            if(qrData.get_Qr_first_vale().contains("maps.google.com")){
                holder.txt_scan_type.setText("Location");
                holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
            }
            else {
                holder.txt_scan_type.setText("Url");
                holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_url);
            }
        }
        if (format.equals("9")){
            holder.txt_scan_type.setText("Wifi");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_wifi);
        }
        if (format.equals("10")){
            holder.txt_scan_type.setText("Location");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_geo);
        }
        if (format.equals("11")){
            holder.txt_scan_type.setText("Calendar");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_calender);
        }
        if (format.equals("12")){
            holder.txt_scan_type.setText("Driver License");
            holder.img_scan_type.setBackgroundResource(R.drawable.ic_scan_license);
        }




    }

    @Override
    public int getItemCount() {
        return QR_List.size(); // important to show all data
    }


    class QR_ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_scan_type ;
        TextView txt_scan_type ;
        TextView txt_scan_date_time ;
        TextView txt_scan_first_val ;
        LinearLayout lay_list ;
        ImageView img_heart_fav ;



        public QR_ViewHolder(View itemView) {
            super(itemView);

            img_scan_type = itemView.findViewById(R.id.img_scan_type);
            txt_scan_type = itemView.findViewById(R.id.txt_scan_type);
            txt_scan_date_time = itemView.findViewById(R.id.txt_scan_date_time);
            txt_scan_first_val = itemView.findViewById(R.id.txt_scan_first_val);
            lay_list = itemView.findViewById(R.id.lay_list);
            img_heart_fav = itemView.findViewById(R.id.img_heart_fav);


            lay_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        QR_Data clickedDataItem = QR_List.get(pos);

                        Intent intent = new Intent(mCtx, ManageResultsActivity.class);
                        intent.putExtra("RECORD_ID_FROM_ADAPTER",Integer.toString(clickedDataItem.get_Id()));
                        mCtx.startActivity(intent);
                    }
                }
            });




        }
    }





}
