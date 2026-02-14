package com.balancedbytes.tool.work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalEntry {

    private static final String COMMA = ",";
    private static final String BLANK = " ";
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Date startDate;
    private Date stopDate;
    private String task;

    private JournalEntry() {
        super();
    }

    public JournalEntry(Date startDate, String task) {
        setStartDate(startDate);
        setTask(task);
    }

    public Date getStartDate() {
        return startDate;
    }

    private void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getTask() {
        return task;
    }

    private void setTask(String task) {
        this.task = task;
    }

    public int getMinutes() {
        if ((startDate == null) || (stopDate == null)) {
            return 0;
        }
        long timeInMs = stopDate.getTime() - startDate.getTime();
        if (timeInMs <= 0) {
            return 0;
        }
        return (int) Math.floorDiv(timeInMs, 60000);
    }

    public String toCsv() {
        String result = "";
        result += DATE_FORMAT.format(startDate);
        result += COMMA;
        result += TIME_FORMAT.format(startDate);
        result += COMMA;
        if (stopDate != null) {
            result += TIME_FORMAT.format(stopDate);
        }
        result += COMMA;
        result += task;
        return result;
    }

    public static JournalEntry fromCsv(String csv) {
        String[] columns = csv.split(COMMA);
        if (columns.length < 4) {
            return null;
        }
        JournalEntry entry = new JournalEntry();
        try {
            entry.setStartDate(DATETIME_FORMAT.parse(columns[0] + BLANK + columns[1]));
            entry.setStopDate(DATETIME_FORMAT.parse(columns[0] + BLANK + columns[2]));
        } catch (ParseException pe) {
            return null;
        }
        entry.setTask(columns[3]);
        return entry;
    }

}
