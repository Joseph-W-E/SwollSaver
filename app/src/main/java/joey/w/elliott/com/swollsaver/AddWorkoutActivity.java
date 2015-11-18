package joey.w.elliott.com.swollsaver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joey Elliott on 2/15/2015.
 */
public class AddWorkoutActivity  extends ActionBarActivity {

    Workout workout;
    EditText titleEditText, descriptionEditText;
    private int year = 0, month = 0, day = 0;
    private Date date = new Date();

    private boolean editMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_editor);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        titleEditText = (EditText) findViewById(R.id.add_title);
        descriptionEditText = (EditText) findViewById(R.id.add_description);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = (long) extras.get("id");
            String title = (String) extras.get("title");
            String description = (String) extras.get("description");
            date = new Date();
            date.setTime((long) extras.get("date"));

            workout = new Workout();
            workout.setID(id);
            workout.setTitle(title);
            workout.setDescription(description);
            workout.setDate(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            titleEditText.setText(title);
            descriptionEditText.setText(description);

            editMode = true;
        }
    }

    public void dateButton(View v) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int ayear, int amonth, int aday) {

                Calendar cal = Calendar.getInstance(Locale.US);
                cal.set(ayear, amonth, aday);
                date = cal.getTime();

            }
        }, year, month, day);
        dialog.show();
    }

    public void cancelButton(View v) {
        this.finish();
    }

    public void addButton(View v) {
        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();

        if (!editMode) {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            workout = new Workout(title, description, date);
            dataSource.createWorkout(workout);
        } else {
            dataSource.deleteWorkout(workout.getID());

            workout.setTitle(titleEditText.getText().toString());
            workout.setDescription(descriptionEditText.getText().toString());
            workout.setDate(date);

            dataSource.createWorkout(workout);
        }


        dataSource.close();

        Toast.makeText(getApplicationContext(), workout.getDate().toString(), Toast.LENGTH_LONG).show();

        this.finish();
    }

    public void discardButton(View v) {
        try {
            WorkoutDataSource dataSource = new WorkoutDataSource(this);
            dataSource.open();
            dataSource.deleteWorkout(workout.getID());
            dataSource.close();
            this.finish();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.accept:
                addButton(null);
                return true;
            case R.id.show_date:
                dateButton(null);
                return true;
            case R.id.cancel:
                cancelButton(null);
                return true;
            case R.id.discard:
                discardButton(null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void convertStringsToWorkout(String id, String title, String description, String date) {

    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

}
