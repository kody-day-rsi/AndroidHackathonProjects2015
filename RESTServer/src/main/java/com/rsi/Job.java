package com.rsi;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date timestamp;

    public Job() {
        // default constructor
    }

    public Job(String companyName, String position, String description, String location, Date timestamp) {
        this.companyName = companyName;
        this.position = position;
        this.location = location;
        this.timestamp = timestamp;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
