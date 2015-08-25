package com.rsi;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRespository extends CrudRepository<Job, Long> {

    List<Job> findAll();
    Job save(Job job);

}
