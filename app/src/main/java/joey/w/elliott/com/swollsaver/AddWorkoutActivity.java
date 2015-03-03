package joey.w.elliott.com.swollsaver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
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
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        workout = new Workout(title, description, date);

        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();
        dataSource.createWorkout(workout);
        dataSource.close();

        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.add_workout:
                break;
        }

        return super.onOptionsItemSelected(item);
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
