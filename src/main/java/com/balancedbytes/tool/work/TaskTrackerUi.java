package com.balancedbytes.tool.work;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskTrackerUi extends JFrame {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE dd.MM.yyyy");

    private JLabel timeLabel;
    private JLabel dateLabel;
    private DefaultListModel<String> listModel;
    private String currentTask;
    private final TaskTracker taskTracker;

    public TaskTrackerUi(TaskTracker taskTracker) {

        super("TaskTracker");

        this.taskTracker = taskTracker;

        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createTimerPanel());
        panel.add(createTaskPanel());
        panel.add(createJournalPanel());

        add(panel);

        updateTimeAndDate();

        // use a timer to update the time and date labels every second
        Timer timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();

    }

    private JPanel createTimerPanel() {

        // create a panel to hold the time and date labels
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // create a label to display the time
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.RED);
        panel.add(timeLabel, BorderLayout.CENTER);

        // create a label to display the date
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setVerticalAlignment(SwingConstants.CENTER);
        dateLabel.setForeground(Color.WHITE);
        panel.add(dateLabel, BorderLayout.SOUTH);

        return panel;

    }

    private JPanel createTaskPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> listModel.addElement(currentTask));
        panel.add(startButton);

        panel.add(Box.createHorizontalStrut(5));

        String[] tasks = taskTracker.getTaskList().toArray(new String[0]);
        JComboBox<String> taskComboBox = new JComboBox<>(tasks);
        taskComboBox.addActionListener(e -> {
            if (taskComboBox.getSelectedIndex() >= 0) {
                currentTask = tasks[taskComboBox.getSelectedIndex()];
            }
        });
        panel.add(taskComboBox);

        return panel;

    }

    private JPanel createJournalPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        panel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        JList<String> journalList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(journalList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;

    }

    private void updateTimeAndDate() {
        Date time = Calendar.getInstance().getTime();
        timeLabel.setText(TIME_FORMAT.format(time));
        dateLabel.setText(DATE_FORMAT.format(time));
    }

}
