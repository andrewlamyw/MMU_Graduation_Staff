package com.lamyatweng.mmugraduationstaff;

import java.util.List;
import java.util.Map;

public class Session {
    int columnSize;
    int convocationYear;
    String date;
    String endTime;
    String id;
    List<Map<String, Boolean>> programmes;
    int rowSize;
    String startTime;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Session() {
    }

    public Session(int columnSize, int convocationYear, String date, String endTime, String id, List<Map<String, Boolean>> programmes, int rowSize, String startTime) {
        this.columnSize = columnSize;
        this.convocationYear = convocationYear;
        this.date = date;
        this.endTime = endTime;
        this.id = id;
        this.programmes = programmes;
        this.rowSize = rowSize;
        this.startTime = startTime;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getConvocationYear() {
        return convocationYear;
    }

    public String getDate() {
        return date;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getId() {
        return id;
    }

    public List<Map<String, Boolean>> getProgrammes() {
        return programmes;
    }

    public int getRowSize() {
        return rowSize;
    }

    public String getStartTime() {
        return startTime;
    }
}
