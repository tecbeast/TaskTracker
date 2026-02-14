package com.balancedbytes.tool.work;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Journal {

    private static final String HEADER = "Date,StartTime,StopTime,Task";

    private final List<JournalEntry> entries;

    public Journal() {
        entries = new ArrayList<>();
    }

    public void add(JournalEntry entry) {
        entries.add(entry);
    }

    public void writeTo(Writer out) throws IOException {
        BufferedWriter bufferedOut = new BufferedWriter(out);
        bufferedOut.write(HEADER);
        bufferedOut.newLine();
        for (JournalEntry entry : entries) {
            bufferedOut.write(entry.toCsv());
            bufferedOut.newLine();
        }
        bufferedOut.flush();
    }

    public void readFrom(Reader in) throws IOException {
        String line;
        boolean skipHeader = true;
        clear();
        BufferedReader bufferedIn = new BufferedReader(in);
        while ((line = bufferedIn.readLine()) != null) {
            if (skipHeader) {
                skipHeader = false;
            } else {
                JournalEntry entry = JournalEntry.fromCsv(line);
                if (entry != null) {
                    add(entry);
                }
            }
        };
    }

    public void clear() {
        this.entries.clear();
    }

}
