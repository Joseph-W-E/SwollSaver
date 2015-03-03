package joey.w.elliott.com.swollsaver;

import java.util.Date;

/**
 * Created by Joey Elliott on 2/11/2015.
 */
public class Workout {

    private long ID;
    private String title;
    private String description;
    private Date date;

    public Workout() {
        title = "";
        description = "";
        date = new Date();
    }

    public Workout(String title, String workout, Date date) {
        this.title = title;
        this.description = workout;
        this.date = date;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

}
