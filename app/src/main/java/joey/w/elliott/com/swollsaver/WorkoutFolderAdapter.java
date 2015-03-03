package joey.w.elliott.com.swollsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Joey Elliott on 2/12/2015.
 */
public class WorkoutFolderAdapter extends ArrayAdapter<WorkoutFolder> {

    ArrayList<WorkoutFolder> workoutFolderArrayList = new ArrayList<WorkoutFolder>();

    public WorkoutFolderAdapter(Context context, int layoutResourceId, ArrayList<WorkoutFolder> workoutFolderArrayList) {
        super(context, layoutResourceId, workoutFolderArrayList);
        this.workoutFolderArrayList = workoutFolderArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.workout_folders, null);
        }

        WorkoutFolder workoutFolder = workoutFolderArrayList.get(position);

        if (workoutFolder != null) {

            TextView maindate = (TextView) v.findViewById(R.id.main_date);
            TextView dayofweek = (TextView) v.findViewById(R.id.day_of_week);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
            String formattedDate = simpleDateFormat.format(workoutFolder.getDate());

            if (maindate != null){
                maindate.setText(formattedDate);
            }
            if (dayofweek != null) {
                dayofweek.setText(workoutFolder.getDay());
            }
        }

        // the view must be returned to our activity
        return v;

    }

    public void clear() {
        workoutFolderArrayList = new ArrayList<WorkoutFolder>();
    }
}
