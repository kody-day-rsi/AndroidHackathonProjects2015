package rsi.com.applicationstub;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rsi.com.applicationstub.domain.Job;

public interface JobService {

    @GET("/getJobs")
    void getJobs(Callback<List<Job>> cb);

    @POST("/addJob")
    void createJob(@Body Job job, Callback<List<Job>> cb);

}
