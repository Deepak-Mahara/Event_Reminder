# Event Reminder Java Application

A simple Java Swing desktop application to manage and remind you of your events. Events are saved to a file for persistence, and the app will notify you if an event is scheduled for tomorrow.

## Features
- Add, view, and remove events with title, description, and date
- Mark events as completed (double-click in the table)
- View completed events
- Persistent storage: events are saved in `events.txt`
- Automatic popup reminder for events happening tomorrow (checks every minute)
