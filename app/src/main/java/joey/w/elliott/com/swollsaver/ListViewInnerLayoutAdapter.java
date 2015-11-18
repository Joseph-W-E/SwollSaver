package joey.w.elliott.com.swollsaver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joey Elliott on 4/3/2015.
 */
public class ListViewInnerLayoutAdapter extends ArrayAdapter<Date> {

    Context context;
    ArrayList<Date> dates = new ArrayList<Date>();
    ArrayList<Workout> workouts = new ArrayList<Workout>();


    public ListViewInnerLayoutAdapter(Context context, int layoutResourceId, ArrayList<Date> dates, ArrayList<Workout> workouts) {
        super(context, layoutResourceId, dates);
        this.context = context;
        this.dates = dates;
        this.workouts = workouts;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // Note: This method works, it just makes scrolling laggy
        View v = convertView;
        v = null;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_inner_layout, null);

            Date date = dates.get(position);

            if (date == null) {
                date = new Date();
            }

            // The left text for the current day of the month
            TextView dayofmonthdate = (TextView) v.findViewById(R.id.day_of_month_date);
            TextView dayofmonthday = (TextView) v.findViewById(R.id.day_of_month_day);

            // The linear layout that will have workouts dynamically added to it
            LinearLayout ll = (LinearLayout) v.findViewById(R.id.lvil_innerLL);

            // Simple date format for the date to be put into dayofmonth
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
            String fd1 = sdf1.format(date);
            String fd2 = sdf2.format(date);

            // Set the day of the month
            if (dayofmonthdate != null) {
                dayofmonthdate.setText(fd1);
            }
            if (dayofmonthday != null) {
                dayofmonthday.setText(fd2);
            }

            // Add the workouts to the linear layout dynamically for the given date
            SimpleDateFormat sdfCompare = new SimpleDateFormat("yyyy-MM-dd");
            String dateString;
            String workoutString;

            dateString = sdfCompare.format(date);

            for (int i = 0; i < workouts.size(); i++) {
                WorkoutLayout workoutItem = new WorkoutLayout(getContext());
                workoutString = sdfCompare.format(workouts.get(i).getDate());

                if (dateString.equals(workoutString)) {
                    // Set the title of the workout
                    if (workoutItem.title != null) {
                        workoutItem.title.setText(workouts.get(i).getTitle());
                    }
                    // Set the description of the workout
                    if (workoutItem.description != null) {
                        workoutItem.description.setText(workouts.get(i).getDescription());
                    }

                    class WorkoutOnClickListener implements View.OnClickListener {
                        Workout workout;
                        public WorkoutOnClickListener(Workout workout) {
                            this.workout = workout;
                        }
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(context, AddWorkoutActivity.class);
                            intent.putExtra("id", workout.getID());
                            intent.putExtra("title", workout.getTitle());
                            intent.putExtra("description", workout.getDescription());
                            intent.putExtra("date", workout.getDate().getTime());
                            context.startActivity(intent);
                        }
                    }

                    // This crashes the app
                    workoutItem.setOnClickListener(new WorkoutOnClickListener(workouts.get(i)));

                    ll.addView(workoutItem);
                }

            }
        }

        // the view must be returned to our activity
        return v;

    }

    public void clear() {
        dates = new ArrayList<Date>();
    }

}
