package com.rsi;

import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface JobRespository extends CrudRepository<Job, Long> {

    List<Job> findAll();
    Job save(Job job);

    List<Job> findByPosition(String position);
    List<Job> findByLocation(String location);


    List<Job> findByTimestampBetween(Date before, Date after);

}
