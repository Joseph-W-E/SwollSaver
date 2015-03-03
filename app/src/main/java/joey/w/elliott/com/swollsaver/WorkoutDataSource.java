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
        Date date = workout.getDate();
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        String dateString = dateFormat.format(date);

        ContentValues values = new ContentValues();
        values.put(WorkoutSQLiteHelper.COLUMN_TITLE, title);
        values.put(WorkoutSQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(WorkoutSQLiteHelper.COLUMN_DATE, dateString);
        database.insert(WorkoutSQLiteHelper.TABLE_WORKOUTS, null,
                values);
    }

    // Probably don't need
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

    public ArrayList<Workout> getWorkouts(Date date) {
        // Only gets workouts for the given date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        String dateString = sdf.format(date);
        String workoutDateString;

        ArrayList<Workout> workouts = new ArrayList<Workout>();

        Cursor cursor = database.query(WorkoutSQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null, WorkoutSQLiteHelper.COLUMN_DATE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workoutDateString = sdf.format(workout.getDate());

            // if the date doesn't match the given one, move on
            if (dateString.equals(workoutDateString)) {
                workouts.add(workout);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return workouts;
    }

    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setID(cursor.getLong(0));
        workout.setTitle(cursor.getString(1));
        workout.setDescription(cursor.getString(2));
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

        try{
            workout.setDate(dateFormat.parse(cursor.getString(3)));
        } catch (ParseException e) {
            workout.setDate(new Date());
        }

        return workout;
    }
}
