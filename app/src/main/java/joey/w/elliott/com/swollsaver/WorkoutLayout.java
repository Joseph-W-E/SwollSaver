package joey.w.elliott.com.swollsaver;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Joey Elliott on 4/4/2015.
 */
public class WorkoutLayout extends LinearLayout {

    public TextView title;
    public TextView description;

    public WorkoutLayout(Context context) {
        super(context);
        init();
    }

    public WorkoutLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkoutLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.workout_items, this);
        this.title = (TextView) findViewById(R.id.title_item);
        this.description = (TextView) findViewById(R.id.description_item);
    }



}
