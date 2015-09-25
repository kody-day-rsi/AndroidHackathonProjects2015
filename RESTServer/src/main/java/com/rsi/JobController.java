package com.rsi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

@RestController
public class JobController {

    @Autowired
    JobRespository repo;

    @RequestMapping("/getJobs")
    public List<Job> getJobs() {
        return repo.findAll();
    }

    @RequestMapping("/addJob")
    public void addJob(@RequestBody Job job) {
        repo.save(job);
    }

    @RequestMapping("/findJobsByPosition")
    public List<Job> findJobsByPosition(@RequestBody String position) {
        return repo.findByPosition(position);
    }

    @RequestMapping("/findJobsByLocation")
    public List<Job> findJobsByLocation(@RequestBody String location) {
        return repo.findByLocation(location);
    }

    @RequestMapping("/findJobsAfterTimestamp")
    public List<Job> findJobsAfterTimestamp(@RequestBody Date timestamp) {
        return repo.findByTimestampBetween(timestamp, new Date(System.currentTimeMillis()));
    }

    @PostConstruct
    public void populateData() {

        String testCompanyName = "RSI";
        String[] positions = {"Test Position 1", "Test Position 2",
                "Test Position 3", "Test Position 4", "Test Position 5"};
        String testDescription = "This is a test description";

        String[] locations = {"Test location, GA", "Test location, AL",
                "Test location NY", "Test location TX", "Test location FL"};

        for(int i = 0; i < 5; i++) {
            Job job = new Job(testCompanyName, positions[i], testDescription,
                    locations[i], new Date());
            addJob(job);
        }

    }


}
