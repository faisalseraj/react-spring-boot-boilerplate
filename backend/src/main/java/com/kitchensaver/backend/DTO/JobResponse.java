package com.kitchensaver.backend.DTO;

import java.util.Date;

public class JobResponse {
    private Long id;
    private String jobNumber;
    private String jobName;
    private String status;
    private String materialStatus;
    private String cabinetMakerName;
    private String installerName;
    private Date dueDate;
    private String office;
    private String jobColor;

    // No-arg constructor
    public JobResponse() {
    }

    public JobResponse(String message) {
    }

    // All-arg constructor
    public JobResponse(Long id, String jobNumber, String jobName, String status, String materialStatus, String cabinetMakerName, String installerName, Date dueDate, String office, String jobColor) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.jobName = jobName;
        this.status = status;
        this.materialStatus = materialStatus;
        this.cabinetMakerName = cabinetMakerName;
        this.installerName = installerName;
        this.dueDate = dueDate;
        this.office = office;
        this.jobColor = jobColor;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStatus() {
        return status;
    }

    public String getMaterialStatus() {
        return materialStatus;
    }

    public String getCabinetMakerName() {
        return cabinetMakerName;
    }

    public String getInstallerName() {
        return installerName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getOffice() {
        return office;
    }

    public String getJobColor() {
        return jobColor;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }

    public void setCabinetMakerName(String cabinetMakerName) {
        this.cabinetMakerName = cabinetMakerName;
    }

    public void setInstallerName(String installerName) {
        this.installerName = installerName;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setJobColor(String jobColor) {
        this.jobColor = jobColor;
    }
}