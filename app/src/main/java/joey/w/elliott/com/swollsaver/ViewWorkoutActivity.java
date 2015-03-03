package joey.w.elliott.com.swollsaver;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joey Elliott on 2/24/2015.
 */
public class ViewWorkoutActivity extends ActionBarActivity {

    private long ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView titleView = (TextView) findViewById(R.id.view_title);
            TextView descriptionView = (TextView) findViewById(R.id.view_description);
            TextView dateView = (TextView) findViewById(R.id.view_date);


            String title = (String) extras.get("selectedTitle");
            titleView.setText(title);

            String description = (String) extras.get("selectedDescription");
            descriptionView.setText(description);

            Date date = (Date) extras.get("selectedDate");
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            dateView.setText(sdf.format(date));

            ID = (long) extras.get("selectedID");
        }
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

    public void cancelButton(View v) {
        this.finish();
    }

    public void editButton(View v) {
        // Finish this activity

        // Go to the AddWorkoutActivity with the data

    }

    public void deleteButton(View v) {
        WorkoutDataSource dataSource = new WorkoutDataSource(this);
        dataSource.open();
        dataSource.deleteWorkout(ID);
        dataSource.close();

        this.finish();
    }


}
