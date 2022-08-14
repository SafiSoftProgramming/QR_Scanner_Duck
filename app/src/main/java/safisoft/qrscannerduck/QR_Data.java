package safisoft.qrscannerduck;

public class QR_Data {

    private int id;
    private String Qr_format;
    private String Qr_date;
    private String Qr_first_val;
    private String Qr_fav ;
    private String SHOW_ONLY_FAV ;

    public QR_Data(int id, String qr_format, String qr_date, String ad_first_val,String fav,String SHOW_ONLY_FAV) {
        this.id = id;
        this.Qr_format = qr_format;
        this.Qr_date = qr_date;
        this.Qr_first_val = ad_first_val;
        this.Qr_fav = fav ;
        this.SHOW_ONLY_FAV = SHOW_ONLY_FAV ;
    }

        public int get_Id() {
            return id;
        }

        public String get_Qr_format() {
            return Qr_format;
        }

        public String get_Qr_date() {
            return Qr_date;
        }

        public String get_Qr_first_vale() {
            return Qr_first_val;
        }

        public String get_Qr_vaf() {
            return Qr_fav ;
        }

    public String get_show_only_fav_vaf() {
        return SHOW_ONLY_FAV ;
    }


    }












