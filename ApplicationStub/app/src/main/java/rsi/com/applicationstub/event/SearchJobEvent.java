package rsi.com.applicationstub.event;

import java.util.Date;

public class SearchJobEvent {
    public static final int POSITION = 0;
    public static final int LOCATION = 1;
    public static final int DATE = 2;

    public int criteria = -1;

    public String position, location;
    public Date date;

    public SearchJobEvent(int criteria, String string) {
        this.criteria = criteria;
        switch (criteria){
            case POSITION:
                position = string;
                break;
            case LOCATION:
                location = string;
                break;

        }

    }

    public SearchJobEvent(Date date) {
        this.criteria = DATE;
        this.date = date;
    }
}
