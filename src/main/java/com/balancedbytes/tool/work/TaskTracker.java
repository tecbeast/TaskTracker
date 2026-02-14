package com.balancedbytes.tool.work;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class TaskTracker {

    private static final String CONFIG_PATH = "/TaskTracker.properties";

    private final Properties config;
    private final Journal journal;

    public TaskTracker() {
        config = new Properties();
        journal = new Journal();
    }

    private boolean init() {
        try (InputStream in = TaskTracker.class.getResourceAsStream(CONFIG_PATH)) {
            config.load(in);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
            return false;
        }
        String journalPath = config.getProperty("journalPath");
        try (InputStream in = TaskTracker.class.getResourceAsStream(journalPath)) {
            if (in != null) {
                journal.readFrom(new InputStreamReader(in));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    protected List<String> getTaskList() {
        List<String> taskList = new ArrayList<>();
        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("task.") && (config.getProperty(key) != null)) {
                String task = config.getProperty(key).trim();
                if (!task.isEmpty()) {
                    taskList.add(task);
                }
            }
        }
        Collections.sort(taskList);
        return taskList;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private void createAndShowUi() {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Can't change Look&Feel: " + e);
        }

        // make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        TaskTrackerUi ui = new TaskTrackerUi(this);
        ui.setVisible(true);

    }

    public static void main(String[] args) {

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.init();

        // schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(taskTracker::createAndShowUi);

    }

}