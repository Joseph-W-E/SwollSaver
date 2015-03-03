package joey.w.elliott.com.swollsaver;

import java.util.Date;

/**
 * Created by Joey Elliott on 2/12/2015.
 */
public class WorkoutFolder {

    private Date date;
    private String day;

    public WorkoutFolder() {
        super();
    }

    public WorkoutFolder(Date date, String day) {
        this.date = date;
        this.day = day;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

}
