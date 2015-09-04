package com.rsi;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date timestamp;

    public Job() {
        // default constructor
    }


    public Job(String position, String location, Date timestamp) {
        this.position = position;
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
