package com.balancedbytes.tool.work;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTracker extends JFrame {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE dd.MM.yyyy");

    private final JLabel timeLabel;
    private final JLabel dateLabel;

    public TimeTracker() {

        super("TimeTracker");

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

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

        // add the panel to the frame
        add(panel);
        updateTimeAndDate();

        // use a timer to update the time and date labels every second
        Timer timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();

    }

    private void updateTimeAndDate() {
        Date time = Calendar.getInstance().getTime();
        timeLabel.setText(TIME_FORMAT.format(time));
        dateLabel.setText(DATE_FORMAT.format(time));
    }

    public static void main(String[] args) {
        TimeTracker timeTracker = new TimeTracker();
        timeTracker.setVisible(true);
    }

}