package rsi.com.applicationstub.event;

import java.util.Comparator;

import rsi.com.applicationstub.domain.Job;

public class ChangeJobSortEvent {

    public Comparator<Job> comparator;

    public ChangeJobSortEvent(Comparator<Job> comparator) {
        this.comparator = comparator;
    }
}
