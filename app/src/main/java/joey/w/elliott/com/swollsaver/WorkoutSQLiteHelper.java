package joey.w.elliott.com.swollsaver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ethan on 2/23/2015.
 * Edited by Joey on 2/26/2015.
 */
public class WorkoutSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "workouts.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_WORKOUTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text not null, " + COLUMN_DESCRIPTION + ", "
            + COLUMN_DATE + " text not null);";

    public WorkoutSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WorkoutSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

}