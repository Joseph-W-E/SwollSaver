package joey.w.elliott.com.swollsaver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class MainActivity extends ActionBarActivity {

    private static ArrayList<Workout> workouts;
    private ListViewInnerLayoutAdapter adapter;
    private long currentTimeInMillis = 0;
    private Date currentMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentMonth = getTodaysDate();
        initializeListView(currentMonth);

        ImageButton previousMonthButton = (ImageButton) findViewById(R.id.previous_month_button);
        ImageButton nextMonthButton = (ImageButton) findViewById(R.id.next_month_button);

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Set the current month to the previous month
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(currentTimeInMillis);
                calendar.add(Calendar.MONTH, -1);
                currentTimeInMillis = calendar.getTimeInMillis();
                Date date = new Date();
                date.setTime(currentTimeInMillis);
                currentMonth = date;

                // Update the layout
                initializeListView(currentMonth);
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Set the current month to the previous month
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(currentTimeInMillis);
                calendar.add(Calendar.MONTH, 1);
                currentTimeInMillis = calendar.getTimeInMillis();
                Date date = new Date();
                date.setTime(currentTimeInMillis);
                currentMonth = date;

                // Update the layout
                initializeListView(currentMonth);
            }
        });

    }

    private Date getTodaysDate() {
        // Get today's date
        Calendar rightNow = Calendar.getInstance();
        currentTimeInMillis = rightNow.getTimeInMillis();
        Date date = new Date();
        date.setTime(currentTimeInMillis);
        return date;
    }

    private void initializeListView(Date date) {
        // Set the current month
        TextView monthText = (TextView) findViewById(R.id.month_text);
        SimpleDateFormat monthTextSDF = new SimpleDateFormat("MMMM yyyy");
        monthText.setText(monthTextSDF.format(date));

        // Get the workouts for this month
        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();
        workouts = dataSource.getWorkouts(date);
        if (workouts == null) {
            workouts = new ArrayList<Workout>();
        }
        dataSource.close();

        // Get an ArrayList of dates with corresponding workouts
        ArrayList<Date> days = new ArrayList<Date>();
        HashSet<String> workoutDatesStrings = new HashSet<String>();
        SimpleDateFormat sdfCompare = new SimpleDateFormat("yyyy-MM-dd");
        String workoutsString;

        for (int i = 0; i < workouts.size(); i++) {
            workoutsString = sdfCompare.format(workouts.get(i).getDate());
            if (workoutDatesStrings.add(workoutsString)) {
                days.add(workouts.get(i).getDate());
            }
        }

        // Build the adapter
        adapter = new ListViewInnerLayoutAdapter(this, R.layout.listview_inner_layout, days, workouts);

        // Configure the list view
        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
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
                AddWorkoutActivityIntent(false);
                return true;
            case R.id.return_to_today:
                currentMonth = getTodaysDate();
                initializeListView(currentMonth);
                return true;
            case R.id.delete_all_workouts:
                this.deleteAllWorkouts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllWorkouts() {

        new AlertDialog.Builder(this)
                .setTitle(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete All Workouts")
                .setMessage("Are you sure you want to delete all your workouts?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WorkoutDataSource dataSource = new WorkoutDataSource(getApplicationContext());
                        dataSource.open();
                        dataSource.deleteAllWorkouts();
                        dataSource.close();
                        initializeListView(getTodaysDate());
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    public void AddWorkoutActivityIntent(Boolean edit) {
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        if (edit) {
            // Get the workout that is going to be edited

        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        initializeListView(getTodaysDate());
    }

}
