package com.kitchensaver.backend.DTO;

import java.util.Date;

public class JobResponse {
    private Long id;
    private String jobNumber;
    private String jobName;
    private String status;
    private int numCabinets;
    private int numUppers;
    private int numLowers;
    private Long installerId;
    private Long cabinetMakerId;
    private String materialOrderStatus;
    private String materialArrivalStatus;
    private String cabinetMakerName;
    private String installerName;
    private Date dueDate;
    private String office;
    private String jobColor;
    private String image;

    // No-arg constructor
    public JobResponse() {
    }

    public JobResponse(String message) {
    }

    // All-arg constructor
    public JobResponse(Long id, String jobNumber, String jobName, String status, String materialOrderStatus, String materialArrivalStatus, String cabinetMakerName, String installerName, Date dueDate, String office, String jobColor, Long installerId, Long cabinetMakerId, 
     int numCabinets,
     int numUppers,
     int numLowers, String image) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.jobName = jobName;
        this.status = status;
        this.materialOrderStatus = materialOrderStatus;
        this.materialArrivalStatus = materialArrivalStatus;
        this.installerId = installerId;
        this.cabinetMakerId = cabinetMakerId;
        this.materialArrivalStatus = materialArrivalStatus;
        this.cabinetMakerName = cabinetMakerName;
        this.installerName = installerName;
        this.dueDate = dueDate;
        this.office = office;
        this.numCabinets = numCabinets;
        this.numUppers = numUppers;
        this.numLowers = numLowers;
        this.jobColor = jobColor;
        this.image = image;
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

    public String getMaterialOrderStatus() {
        return materialOrderStatus;
    }

    public String getMaterialArrivalStatus() {
        return materialArrivalStatus;
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

    public Long getInstallerId() {
        return installerId;
    }

    public Long getCabinetMakerId() {
        return cabinetMakerId;
    }
    
    public String getJobColor() {
        return jobColor;
    }
    
    public int getNumCabinets() {
        return numCabinets;
    }
    
    public int getNumUppers() {
        return numUppers;
    }
    
    public int getNumLowers() {
        return numLowers;
    }
    
    public String getImage() {
        return image;
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

    public void setMaterialOrderStatus(String materialOrderStatus) {
        this.materialOrderStatus = materialOrderStatus;
    }

    public void setMaterialArrivalStatus(String materialArrivalStatus) {
        this.materialArrivalStatus = materialArrivalStatus;
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

    public void setInstallerId(Long installerId) {
        this.installerId = installerId;
    }
    
    public void setCabinetMakerId(Long cabinetMakerId) {
        this.cabinetMakerId = cabinetMakerId;
    }

    public void setNumCabinets(int numCabinets) {
        this.numCabinets = numCabinets;
    }
    
    public void setNumUppers(int numUppers) {
        this.numUppers = numUppers;
    }
    
    public void setNumLowers(int numLowers) {
        this.numLowers = numLowers;
    }
    
    public void setImage(String image) {
        this.image = image;
    }

}
