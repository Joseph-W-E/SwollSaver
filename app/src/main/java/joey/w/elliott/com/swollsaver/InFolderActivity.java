package joey.w.elliott.com.swollsaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



/**
 * Created by Joey Elliott on 2/24/2015.
 */
public class InFolderActivity extends ActionBarActivity {

    private ArrayList<Workout> workouts;
    private Date date;
    private WorkoutAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_folder);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = (Date) extras.get("folderDate");
            TextView textView = (TextView) findViewById(R.id.in_folder_date);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            textView.setText(sdf.format(date));
        }

        populateListView();
    }

    private void populateListView() {
        // Get the list of workouts
        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();
        workouts = dataSource.getWorkouts(date);
        if (workouts == null) {
            workouts = new ArrayList<Workout>();
        }
        dataSource.close();

        // Build the Adapter
        adapter = new WorkoutAdapter(this, R.layout.workout_items, workouts);

        // Configure the list view
        listView = (ListView) findViewById(R.id.in_folder_list_view);
        listView.setAdapter(adapter);

        // Set an OnItemClickListener
        // Brings to InFolderActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InFolderActivity.this, ViewWorkoutActivity.class);
                intent.putExtra("selectedTitle", workouts.get(position).getTitle());
                intent.putExtra("selectedDescription", workouts.get(position).getDescription());
                intent.putExtra("selectedDate", workouts.get(position).getDate());
                intent.putExtra("selectedID", workouts.get(position).getID());
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        populateListView();
    }

}
