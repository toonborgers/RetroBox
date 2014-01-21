package be.cegeka.retrobox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RetroPilotDBHelper extends SQLiteOpenHelper {
    public RetroPilotDBHelper(Context context) {
        super(context, RetroPilotContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RetroPilotContract.Retros.CREATE);
        sqLiteDatabase.execSQL(RetroPilotContract.Activities.CREATE);
        sqLiteDatabase.execSQL(RetroPilotContract.RetroActivities.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
