package com.rsi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    JobRespository repo;

    @RequestMapping("/getJobs")
    public List<Job> getJobs() {
        return repo.findAll();
    }

    @RequestMapping("/addJob")
    public List<Job> addJob(@RequestBody Job job) {
        repo.save(job);
        return repo.findAll();
    }

}
