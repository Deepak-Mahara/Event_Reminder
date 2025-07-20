package Event_Reminder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventReminderApp extends JFrame {
    private final ReminderManager manager = new ReminderManager();
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Title", "Description", "Date", "Completed"}, 0);
    private final JTable eventTable = new JTable(tableModel);
    private final JTextField titleField = new JTextField(), descField = new JTextField(), dateField = new JTextField();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EventReminderApp() {
        super("Event Reminder App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createInputPanel(), BorderLayout.NORTH);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        eventTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) markCompleted();
            }
        });
        updateTable();

        Thread reminderThread = new Thread(() -> {
            while (true) {
                try {
                    checkUpcomingEvents();
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        reminderThread.setDaemon(true);
        reminderThread.start();
    }
    private void checkUpcomingEvents() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        StringBuilder sb = new StringBuilder();
        for (Event ev : manager.arr) {
            if (!ev.getEventStatus() && ev.getDate().equals(tomorrow)) {
                sb.append(ev.showTitle()).append(" - ").append(ev.showDescription()).append("\n");
            }
        }
        if (sb.length() > 0) {
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this, "Upcoming event(s) tomorrow:\n" + sb, "Event Reminder", JOptionPane.INFORMATION_MESSAGE)
            );
        }
    }

    private JPanel createInputPanel() {
        JPanel p = new JPanel(new GridLayout(2, 4, 5, 5));
        p.add(new JLabel("Title:")); p.add(titleField);
        p.add(new JLabel("Description:")); p.add(descField);
        p.add(new JLabel("Date (yyyy-MM-dd):")); p.add(dateField);
        JButton addBtn = new JButton("Add Event");
        JButton showCompletedBtn = new JButton("Show Completed");
        p.add(addBtn); p.add(showCompletedBtn);
        addBtn.addActionListener(e -> addEvent());
        showCompletedBtn.addActionListener(e -> showCompleted());
        return p;
    }

    private JPanel createBottomPanel() {
        JPanel p = new JPanel();
        JButton removeBtn = new JButton("Remove Selected");
        p.add(removeBtn);
        removeBtn.addActionListener(e -> removeEvent());
        return p;
    }

    private void addEvent() {
        String title = titleField.getText().trim(), desc = descField.getText().trim(), dateStr = dateField.getText().trim();
        if (title.isEmpty() || desc.isEmpty() || dateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            manager.addEvent(new Event(title, desc, date));
            updateTable();
            titleField.setText(""); descField.setText(""); dateField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private void removeEvent() {
        int row = eventTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an event to remove.");
            return;
        }
        manager.removeEvent(row + 1);
        updateTable();
    }

    private void showCompleted() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < manager.arr.size(); i++) {
            Event ev = manager.arr.get(i);
            if (ev.getEventStatus())
                sb.append((i + 1)).append(". ").append(ev.showTitle()).append(" - ").append(ev.showDescription()).append("\n");
        }
        if (sb.length() == 0) sb.append("No completed events.");
        JOptionPane.showMessageDialog(this, sb.toString(), "Completed Events", JOptionPane.INFORMATION_MESSAGE);
    }

    private void markCompleted() {
        int row = eventTable.getSelectedRow();
        if (row != -1) {
            manager.updateEventStatus(row + 1);
            updateTable();
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Event ev : manager.arr)
            tableModel.addRow(new Object[]{ev.showTitle(), ev.showDescription(), ev.getDate(), ev.getEventStatus() ? "Yes" : "No"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventReminderApp().setVisible(true));
    }
}