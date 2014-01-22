package be.cegeka.retrobox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RetroBoxDBHelper extends SQLiteOpenHelper {
    public RetroBoxDBHelper(Context context) {
        super(context, RetroBoxContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RetroBoxContract.Retros.CREATE);
        sqLiteDatabase.execSQL(RetroBoxContract.ActivityTypes.CREATE);
        sqLiteDatabase.execSQL(RetroBoxContract.Activities.CREATE);
        sqLiteDatabase.execSQL(RetroBoxContract.RetroActivities.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
