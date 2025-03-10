package com.kitchensaver.backend.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kitchensaver.backend.DTO.JobImageRequest;
import com.kitchensaver.backend.DTO.JobRequest;
import com.kitchensaver.backend.DTO.JobResponse;
import com.kitchensaver.backend.Service.JobService;
import com.kitchensaver.backend.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER', 'INSTALLER')")
    public ResponseEntity<List<JobResponse>> getAllJobs(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
        String role = decodedJWT.getClaim("role").asString();
        Long userId = decodedJWT.getClaim("id").asLong();
        logger.info("fine here :: 111" );
        List<JobResponse> jobs;
        if ("ADMIN".equals(role)) {
            jobs = jobService.getAllJobs();
        } else if ("CABINET_MAKER".equals(role)) {
            jobs = jobService.getJobsByCabinetMakerId(userId);
        } else if ("INSTALLER".equals(role)) {
            jobs = jobService.getJobsByInstallerId(userId);
        } else {
            logger.info("not fine :: 2");

            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jobs);
    }

    // INSTALLER ENDPOINTS
    @PatchMapping("/{jobId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER', 'INSTALLER')")
    public ResponseEntity<JobResponse> updateJobStatus(
            @PathVariable Long jobId,
            @RequestParam String status,
            @RequestParam String materialOrderStatus,
            @RequestParam String materialArrivalStatus) {
        try {
            JobResponse response = jobService.updateJobStatus(jobId, status, materialOrderStatus,
                    materialArrivalStatus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JobResponse(e.getMessage()));
        }
    }

    @PostMapping("/{jobId}/uploadImage")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER', 'INSTALLER')")
    public ResponseEntity<JobResponse> uploadJobImage(@PathVariable Long jobId, @RequestBody JobImageRequest request) {
        try {
            JobResponse response = jobService.updateJobImage(jobId, request.getImageUrl());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JobResponse(e.getMessage()));
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER', 'INSTALLER')")
    public ResponseEntity<List<JobResponse>> filterJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long installerId,
            @RequestParam(required = false) String materialOrderStatus,
            @RequestParam(required = false) String materialArrivalStatus,
            @RequestParam(required = false) String office) {
        return ResponseEntity
                .ok(jobService.filterJobs(status, installerId, materialOrderStatus, materialArrivalStatus, office));
    }
}
