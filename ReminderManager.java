package Event_Reminder;

import java.util.ArrayList;

public class ReminderManager {
    ArrayList<Event>arr;
    ReminderManager(){
        arr=new ArrayList<>();
    }


    public void addEvent(Event e){
        arr.add(e);
        System.out.println("Event added to the list of events.!");
    }

    public void viewEvents(){
        if(arr.isEmpty()){
            System.out.println("Hey there is not event to be listed");
        }
        for(int i=0;i<arr.size();i++){
            System.out.println((i+1)+". "+arr.get(i).showTitle());
            System.out.println("The Des :"+arr.get(i).showDescription());
        }        
    }
    public void showEventAlreadyHappened(){
        if(arr.isEmpty()){
            System.out.println("No event in the list");
        }

        for(int i=0;i<arr.size();i++){
            if(arr.get(i).getEventStatus()){
                System.out.println((i+1)+". "+arr.get(i).showTitle());
                System.out.println(arr.get(i).showDescription());
            }
        }
    }
        public void updateEventStatus(int index){
            if(index<=0 || index >=arr.size() || arr.isEmpty())return;
            arr.get(index).setEventStatus(true);
            System.out.println("Event marked as completed!");

        }
    
        public void removeEvent (int index){
            if(index<0 || index>=arr.size() || arr.isEmpty()){
                System.out.println("Not able to remove the event");
            }
            arr.remove(index-1);
        }
}
