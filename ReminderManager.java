package Event_Reminder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReminderManager {
    public List<Event> arr;
    private static final String FILE_NAME = "events.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ReminderManager() {
        arr = new ArrayList<>();
        loadEventsFromFile();
    }

    private void loadEventsFromFile() {
        arr.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    String title = parts[0];
                    String desc = parts[1];
                    LocalDate date = LocalDate.parse(parts[2], FORMATTER);
                    boolean completed = Boolean.parseBoolean(parts[3]);
                    Event e = new Event(title, desc, date);
                    e.setEventStatus(completed);
                    arr.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveEventsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Event e : arr) {
                pw.println(e.showTitle() + "\t" + e.showDescription() + "\t" + e.getDate() + "\t" + e.getEventStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEvent(Event e) {
        arr.add(e);
        saveEventsToFile();
        System.out.println("Event added to the list of events.!");
    }

    public void viewEvents() {
        if (arr.isEmpty()) {
            System.out.println("Hey there is not event to be listed");
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println((i + 1) + ". " + arr.get(i).showTitle());
            System.out.println("The Des :" + arr.get(i).showDescription());
        }
    }

    public void showEventAlreadyHappened() {
        if (arr.isEmpty()) {
            System.out.println("No event in the list");
        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getEventStatus()) {
                System.out.println((i + 1) + ". " + arr.get(i).showTitle());
                System.out.println(arr.get(i).showDescription());
            }
        }
    }

    public void updateEventStatus(int index) {
        if (index <= 0 || index > arr.size() || arr.isEmpty()) return;
        arr.get(index - 1).setEventStatus(true);
        saveEventsToFile();
        System.out.println("Event marked as completed!");
    }

    public void removeEvent(int index) {
        if (index <= 0 || index > arr.size() || arr.isEmpty()) {
            System.out.println("Not able to remove the event");
            return;
        }
        arr.remove(index - 1);
        saveEventsToFile();
    }
}
