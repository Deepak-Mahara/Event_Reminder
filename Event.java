package Event_Reminder;

import java.time.LocalDate;

public class Event {
    private String title;
    private String description;
    private LocalDate date;
    private boolean isCompleted;

    public Event(String title, String description, LocalDate date) {
        this.title = title;
        this.description = description;
        this.date = date;
        isCompleted = false;
    }

    public String showTitle() {
        return this.title;
    }

    public String showDescription() {
        return this.description;
    }

    public boolean getEventStatus() {
        return isCompleted;
    }

    public void setEventStatus(boolean flag) {
        this.isCompleted = flag;
    }
}
