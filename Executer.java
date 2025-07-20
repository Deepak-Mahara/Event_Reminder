package Event_Reminder;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Executer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ReminderManager rm = new ReminderManager();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.println("*** Event Menu ***");
            System.out.println("*** 1-Add Event ***");
            System.out.println("*** 2-Display Event ***");
            System.out.println("*** 3-Show Occured Events ***");
            System.out.println("*** 4-Remove Event ***");
            System.out.println("*** 5-Exit ***");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    System.out.print("Enter title: ");
                    String t = sc.nextLine();
                    System.out.print("Enter description: ");
                    String d = sc.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String date = sc.nextLine();

                    LocalDate date2 = LocalDate.parse(date, pattern);
                    rm.addEvent(new Event(t, d, date2));
                    break;

                case 2:
                    rm.viewEvents();
                    break;

                case 3:
                    rm.showEventAlreadyHappened();
                    break;

                case 4:
                    rm.viewEvents();
                    System.out.print("Enter event number to remove: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    rm.removeEvent(index);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }
    }
}