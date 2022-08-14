package safisoft.qrscannerduck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by SafiSoft on 3/19/2021.
 */

public class DbConnction extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "qrscanner_duck";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DbConnction(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        super.onOpen(myDataBase);
        myDataBase.disableWriteAheadLogging();

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {//fix database problem on api28+
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public Cursor Query_Data(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query(table,null,null,null,null,null,null);
    }

    public Cursor Row_Query(String Tapel_name,String column_name,String data){
        return myDataBase.rawQuery("SELECT * FROM " + Tapel_name + " WHERE " + column_name + "=?", new String[] { data });
    }

    public Cursor Search_Query(String Tapel_name,String column_name,String val){
        return myDataBase.rawQuery("SELECT * FROM "+Tapel_name+" WHERE "+column_name+" like '%"+val+"%'",null);
    }

    public void insertRecord(String des1 , String val1 , String des2 , String val2 , String des3 , String val3 , String des4 , String val4 , String des5 , String val5 , String des6 , String val6
            , String des7 , String val7 , String des8 , String val8 , String des9 , String val9 , String des10 , String val10 , String des11 , String val11 , String format , String fav , String time , String rawValue) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("des1",des1);
        contentValues.put("val1",val1);
        contentValues.put("des2",des2);
        contentValues.put("val2",val2);
        contentValues.put("des3",des3);
        contentValues.put("val3",val3);
        contentValues.put("des4",des4);
        contentValues.put("val4",val4);
        contentValues.put("des5",des5);
        contentValues.put("val5",val5);
        contentValues.put("des6",des6);
        contentValues.put("val6",val6);
        contentValues.put("des7",des7);
        contentValues.put("val7",val7);
        contentValues.put("des8",des8);
        contentValues.put("val8",val8);
        contentValues.put("des9",des9);
        contentValues.put("val9",val9);
        contentValues.put("des10",des10);
        contentValues.put("val10",val10);
        contentValues.put("des11",des11);
        contentValues.put("val11",val11);
        contentValues.put("format",format);
        contentValues.put("fav",fav);
        contentValues.put("time",time);
        contentValues.put("rawValue",rawValue);
        database.insert("qr_data", null, contentValues);
        database.close();
    }

    public void updateRecord(String TABEL_NAME,String COLUMN_NAME,String RECORD_ID,String COLUMN_VAL) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,COLUMN_VAL);
        database.update(TABEL_NAME, contentValues, "_id" + " = ?", new String[]{RECORD_ID});
        database.close();
    }

    public void updateRecordlogin(String TABEL_NAME,String COLUMN_NAME1,String COLUMN_NAME2,String COLUMN_NAME3,String COLUMN_NAME4,String RECORD_ID,String COLUMN_VAL1,String COLUMN_VAL2,String COLUMN_VAL3,String COLUMN_VAL4) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME1,COLUMN_VAL1);
        contentValues.put(COLUMN_NAME2,COLUMN_VAL2);
        contentValues.put(COLUMN_NAME3,COLUMN_VAL3);
        contentValues.put(COLUMN_NAME4,COLUMN_VAL4);
        database.update(TABEL_NAME, contentValues, "username" + " = ?", new String[]{RECORD_ID});
        database.close();
    }

    public void deleteRecordAlternate(String TABLE_NAME,String COLUMN_ID,String ID_NUM) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + ID_NUM + "'");
        database.close();
    }

    public void deleteAllRecord(String TABLE_NAME) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME );
        database.close();
    }

    public Cursor sortRecord_type( ) {
      return    myDataBase.rawQuery("SELECT * FROM qr_data Order By des1 DESC ",null);
    }

    public Cursor sortRecord_name( ) {
        return    myDataBase.rawQuery("SELECT * FROM qr_data Order By val1 ASC ",null);
    }

    public Cursor sortRecord_id( ) {
        return    myDataBase.rawQuery("SELECT * FROM qr_data Order By _id DESC",null);
    }




}