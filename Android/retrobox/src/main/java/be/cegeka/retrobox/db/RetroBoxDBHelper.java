package be.cegeka.retrobox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static be.cegeka.retrobox.db.RetroBoxContract.Activities;
import static be.cegeka.retrobox.db.RetroBoxContract.ActivityExecutions;
import static be.cegeka.retrobox.db.RetroBoxContract.ActivityTypes;
import static be.cegeka.retrobox.db.RetroBoxContract.Retros;

public class RetroBoxDBHelper extends SQLiteOpenHelper {
    public RetroBoxDBHelper(Context context) {
        super(context, RetroBoxContract.DB_NAME, null, RetroBoxContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ActivityExecutions.DROP);
        sqLiteDatabase.execSQL(Retros.DROP);
        sqLiteDatabase.execSQL(Activities.DROP);
        sqLiteDatabase.execSQL(ActivityTypes.DROP);

        sqLiteDatabase.execSQL(Retros.CREATE);
        sqLiteDatabase.execSQL(ActivityTypes.CREATE);
        sqLiteDatabase.execSQL(Activities.CREATE);
        sqLiteDatabase.execSQL(ActivityExecutions.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
