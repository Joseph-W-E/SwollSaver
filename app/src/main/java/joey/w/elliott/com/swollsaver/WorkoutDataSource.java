package joey.w.elliott.com.swollsaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joey Elliott on 2/26/2015.
 */
public class WorkoutDataSource {

    // Database fields
    private SQLiteDatabase database;
    private WorkoutSQLiteHelper dbHelper;
    private String[] allColumns = { WorkoutSQLiteHelper.COLUMN_ID,
            WorkoutSQLiteHelper.COLUMN_TITLE, WorkoutSQLiteHelper.COLUMN_DESCRIPTION, WorkoutSQLiteHelper.COLUMN_DATE };

    public WorkoutDataSource(Context context) {
        dbHelper = new WorkoutSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createWorkout(Workout workout) {
        String title = workout.getTitle();
        String description = workout.getDescription();
        long timeInMillis = workout.getDate().getTime();

        ContentValues values = new ContentValues();
        values.put(WorkoutSQLiteHelper.COLUMN_TITLE, title);
        values.put(WorkoutSQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(WorkoutSQLiteHelper.COLUMN_DATE, timeInMillis);

        database.insert(WorkoutSQLiteHelper.TABLE_WORKOUTS, null, values);
    }

    public void deleteWorkout(Workout workout) {
        long id = workout.getID();
        //System.out.println("Comment deleted with id: " + id);
        database.delete(WorkoutSQLiteHelper.TABLE_WORKOUTS, WorkoutSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteWorkout(long id) {
        database.delete(WorkoutSQLiteHelper.TABLE_WORKOUTS, WorkoutSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteAllWorkouts() {
        ArrayList<Workout> workouts = this.getWorkouts();
        for (int i = 0; i < workouts.size(); i++) {
            deleteWorkout(workouts.get(i).getID());
        }
    }

    public ArrayList<Workout> getWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();

        // columnName + " DESC" orders dates by descending order
        Cursor cursor = database.query(WorkoutSQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null, WorkoutSQLiteHelper.COLUMN_DATE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return workouts;
    }

    /* Only gets the workouts for the given month. */
    public ArrayList<Workout> getWorkouts(Date date) {
        // Convert the date into milliseconds
        long timeInMillis = date.getTime();

        // Get a calendar instance for the time in milliseconds
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        ArrayList<Workout> workouts = new ArrayList<Workout>();

        Cursor cursor = database.query(WorkoutSQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null, WorkoutSQLiteHelper.COLUMN_DATE + " DESC");

        // Get a calendar instance for the row's time in milliseconds
        Calendar rowCalendar = Calendar.getInstance();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            rowCalendar.setTimeInMillis(workout.getDate().getTime());

            // if the dates are in the same month and year, add the workout
            if ((calendar.get(Calendar.YEAR) == rowCalendar.get(Calendar.YEAR)) &&
                    (calendar.get(Calendar.MONTH) == rowCalendar.get(Calendar.MONTH))) {
                workouts.add(workout);
            }

            cursor.moveToNext();
        }

        // Make sure to close the cursor!!!
        cursor.close();
        return workouts;
    }

    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setID(cursor.getLong(0));
        workout.setTitle(cursor.getString(1));
        workout.setDescription(cursor.getString(2));

        // The date is currently in milliseconds
        Date date = new Date();

        // For future reference, you may have to surround this with try/catch
        date.setTime(Long.parseLong(cursor.getString(3)));

        workout.setDate(date);

        return workout;
    }
}
