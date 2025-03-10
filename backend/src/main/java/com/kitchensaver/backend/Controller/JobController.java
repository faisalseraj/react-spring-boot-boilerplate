package com.kitchensaver.backend.Controller;

import com.kitchensaver.backend.DTO.JobRequest;
import com.kitchensaver.backend.DTO.JobResponse;
import com.kitchensaver.backend.Service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // ADMIN ENDPOINTS
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest request) {
        try {
            JobResponse response = jobService.createJob(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JobResponse(e.getMessage()));
        }
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long jobId, @RequestBody JobRequest request) {
        try {
            JobResponse response = jobService.updateJob(jobId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        try {
            jobService.deleteJob(jobId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER_INSTALLER')")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // INSTALLER ENDPOINTS
    @PatchMapping("/{jobId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER_INSTALLER')")
    public ResponseEntity<JobResponse> updateJobStatus(
            @PathVariable Long jobId,
            @RequestParam String status,
            @RequestParam String materialStatus) {
        try {
            JobResponse response = jobService.updateJobStatus(jobId, status, materialStatus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JobResponse(e.getMessage()));
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER_INSTALLER')")
    public ResponseEntity<List<JobResponse>> filterJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long installerId,
            @RequestParam(required = false) String materialStatus,
            @RequestParam(required = false) String office) {
        return ResponseEntity.ok(jobService.filterJobs(status, installerId, materialStatus, office));
    }
}
