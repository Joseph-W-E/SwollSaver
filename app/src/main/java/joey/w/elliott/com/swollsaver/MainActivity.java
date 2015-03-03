package joey.w.elliott.com.swollsaver;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    private static ArrayList<Workout> workouts;
    private ArrayList<WorkoutFolder> workoutFolders;
    private ListView listview;
    private WorkoutFolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the list of workouts, already sorted by date
        loadWorkouts();

        // Load all the folders (Note: they aren't saved permanently)
        populateListView();
    }

    private void loadWorkouts() {
        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();
        workouts = dataSource.getWorkouts();
        if (workouts == null) {
            workouts = new ArrayList<Workout>();
        }
        dataSource.close();
    }

    private void populateListView() {
        // Create the list of items
        workoutFolders = new ArrayList<WorkoutFolder>();
        Date lastDate = null;
        Date currDate;
        String lastDateStr = null;
        String currDateStr;

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        for (int i = 0; i < workouts.size(); i++) {
            currDate = workouts.get(i).getDate();
            currDateStr = sdf.format(currDate);

            // If a folder for that date doesn't already exist, create one
            if (lastDate == null || !currDateStr.equals(lastDateStr)) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
                workoutFolders.add(new WorkoutFolder(currDate, sdf2.format(currDate)));
            }

            lastDate = currDate;
            lastDateStr = currDateStr;
        }

        // Build the Adapter
        adapter = new WorkoutFolderAdapter(this, R.layout.workout_folders, workoutFolders);

        // Configure the list view
        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        // Set an OnItemClickListener
        // Brings to InFolderActivity
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InFolderActivity.class);
                intent.putExtra("folderDate", workoutFolders.get(position).getDate());
                startActivity(intent);
            }
        });
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
                AddWorkoutActivityIntent();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void AddWorkoutActivityIntent() {
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkouts();
        adapter.clear();
        populateListView();
    }

}
