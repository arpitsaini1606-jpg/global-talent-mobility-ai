package com.mobility.controller;

import com.mobility.proto.ScrapeResponse;
import com.mobility.service.JobServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JobController {

    private final JobServiceClient client;

    public JobController(JobServiceClient client) {
        this.client = client;
    }

    @GetMapping("/api/jobs")
    public List<JobDto> getJobs(@RequestParam String keyword, @RequestParam String country) {
        ScrapeResponse response = client.fetchJobs(keyword, country);

        // Convert Proto objects to a simple DTO (Data Transfer Object) for the web
        return response.getJobsList().stream()
                .map(job -> new JobDto(
                        job.getTitle(),
                        job.getCompany(),
                        job.getLocation(),
                        job.getSponsorshipStatus(),
                        job.getSummary(),
                        job.getUrl()))
                .collect(Collectors.toList());
    }

    // A simple record for the JSON response
    public record JobDto(String title, String company, String location,
            String sponsorship, String summary, String url) {
    }
}
