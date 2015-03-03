package joey.w.elliott.com.swollsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joey Elliott on 2/12/2015.
 */
public class WorkoutAdapter extends ArrayAdapter<Workout> {

    ArrayList<Workout> workoutArrayList = new ArrayList<Workout>();


    public WorkoutAdapter(Context context, int layoutResourceId, ArrayList<Workout> workoutArrayList) {
        super(context, layoutResourceId, workoutArrayList);
        this.workoutArrayList = workoutArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.workout_items, null);
        }

        Workout workout = workoutArrayList.get(position);

        if (workout != null) {
            TextView titleItem = (TextView) v.findViewById(R.id.title_item);
            if (titleItem != null){
                titleItem.setText(workout.getTitle());
            }
        }

        return v;

    }
}
