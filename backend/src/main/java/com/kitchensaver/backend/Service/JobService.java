package com.kitchensaver.backend.Service;

import com.kitchensaver.backend.DTO.JobRequest;
import com.kitchensaver.backend.DTO.JobResponse;
import com.kitchensaver.backend.Exceptions.NotFoundException;
import com.kitchensaver.backend.Repo.JobRepo;
import com.kitchensaver.backend.Repo.UserRepo;
import com.kitchensaver.backend.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    public JobService(JobRepo jobRepo, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    public JobResponse createJob(JobRequest request) throws Exception {
        Job job = new Job();
        mapRequestToEntity(request, job);
        return mapEntityToResponse(jobRepo.save(job));
    }

    public JobResponse updateJob(Long jobId, JobRequest request) throws Exception {
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        mapRequestToEntity(request, job);
        return mapEntityToResponse(jobRepo.save(job));
    }

    public JobResponse updateJobStatus(Long jobId, String status, String materialStatus) throws Exception {
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        job.setStatus(status);
        job.setMaterialStatus(materialStatus);
        return mapEntityToResponse(jobRepo.save(job));
    }

    public void deleteJob(Long jobId) throws Exception {
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        jobRepo.delete(job);
    }

    private void mapRequestToEntity(JobRequest request, Job job) throws Exception {
        // Map all fields from request to entity
        job.setJobNumber(request.getJobNumber());
        job.setJobName(request.getJobName());
        job.setNumCabinets(request.getNumCabinets());
        job.setNumUppers(request.getNumUppers());
        job.setNumLowers(request.getNumLowers());
        job.setCabinetMaker(userRepo.findById(request.getCabinetMakerId())
                .orElseThrow(() -> new NotFoundException("Cabinet maker not found")));
        job.setInstaller(userRepo.findById(request.getInstallerId())
                .orElseThrow(() -> new NotFoundException("Installer not found")));
        job.setDueDate(request.getDueDate());
        job.setJobColor(request.getJobColor());
        job.setOffice(request.getOffice());
    }

    private JobResponse mapEntityToResponse(Job job) {
        JobResponse response = new JobResponse();
        // Map all fields from entity to response
        response.setId(job.getId());
        response.setJobNumber(job.getJobNumber());
        response.setJobName(job.getJobName());
        response.setStatus(job.getStatus());
        response.setMaterialStatus(job.getMaterialStatus());
        response.setCabinetMakerName(job.getCabinetMaker().getFirstName() + " " + job.getCabinetMaker().getLastName());
        response.setInstallerName(job.getInstaller().getFirstName() + " " + job.getInstaller().getLastName());
        response.setDueDate(job.getDueDate());
        response.setOffice(job.getOffice());
        response.setJobColor(job.getJobColor());
        return response;
    }

    public List<JobResponse> getAllJobs() {
        return jobRepo.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<JobResponse> filterJobs(String status, Long installerId, String materialStatus, String office) {
        // Implement filtering logic using repository methods
        return jobRepo.findByFilters(status, installerId, materialStatus, office).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }
}
