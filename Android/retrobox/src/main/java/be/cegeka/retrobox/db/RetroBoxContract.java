package be.cegeka.retrobox.db;

import android.provider.BaseColumns;

public class RetroBoxContract {
    public static final String DB_NAME = "be.cegeka.retropilot.DATABASE.db";
    public static final int VERSION = 1;

    public static final class Retros implements BaseColumns {
        public static final String TABLE_NAME = "retros";

        public static final String COL_NAME = "name";
        public static final String COL_TIME = "time";
        public static final String COL_LOCATION = "location";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String CREATE = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_NAME + " TEXT, ")
                .append(COL_LOCATION + " TEXT, ")
                .append(COL_TIME + " INTEGER)")
                .toString();
    }

    public static final class ActivityTypes implements BaseColumns {
        public static final String TABLE_NAME = "activityTypes";

        public static final String COL_TYPE_ID = "typeId";
        public static final String COL_DESCRIPTION = "description";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String CREATE = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_TYPE_ID + " INTEGER, ")
                .append(COL_DESCRIPTION + " TEXT)")
                .toString();
    }

    public static final class Activities implements BaseColumns {
        public static final String TABLE_NAME = "activities";

        public static final String COL_NAME = "name";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_MATERIALS = "materials";
        public static final String COL_DURATION = "duration";
        public static final String COL_HOWTO = "howto";
        public static final String COL_ACTIVITY_TYPE_ID = "activityTypeId";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String CREATE = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_NAME + " TEXT, ")
                .append(COL_DESCRIPTION + " TEXT, ")
                .append(COL_MATERIALS + " TEXT, ")
                .append(COL_HOWTO + " TEXT, ")
                .append(COL_DURATION + " INTEGER, ")
                .append(COL_ACTIVITY_TYPE_ID + " INTEGER,")
                .append("FOREIGN KEY (" + COL_ACTIVITY_TYPE_ID + ") REFERENCES " + ActivityTypes.TABLE_NAME + "(" + ActivityTypes.COL_TYPE_ID + "))")
                .toString();
    }

    public static final class ActivityExecutions implements BaseColumns {
        public static final String TABLE_NAME = "activityExecutions";

        public static final String COL_RETRO_ID = "retroId";
        public static final String COL_ACTIVITY_ID = "activityId";
        public static final String COL_START = "start";
        public static final String COL_END = "end";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String CREATE = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_RETRO_ID + " INTEGER, ")
                .append(COL_ACTIVITY_ID + " INTEGER, ")
                .append(COL_START + " INTEGER, ")
                .append(COL_END + " INTEGER, ")
                .append("FOREIGN KEY (" + COL_RETRO_ID + ") REFERENCES " + Retros.TABLE_NAME + "(" + Retros._ID + "),")
                .append("FOREIGN KEY (" + COL_ACTIVITY_ID + ") REFERENCES " + Activities.TABLE_NAME + "(" + Activities._ID + "))")
                .toString();
    }
}
