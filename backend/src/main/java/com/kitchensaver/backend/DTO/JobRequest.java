package com.kitchensaver.backend.DTO;

import java.util.Date;

public class JobRequest {
    private String jobNumber;
    private String jobName;
    private int numCabinets;
    private int numUppers;
    private int numLowers;
    private Long cabinetMakerId;
    private Long installerId;
    private Date dueDate;
    private String jobColor;
    private String office;
    private String status;
    private String materialOrderStatus;
    private String materialArrivalStatus;

    // Getters
    public String getJobNumber() {
        return jobNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getJobName() {
        return jobName;
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

    public Long getCabinetMakerId() {
        return cabinetMakerId;
    }

    public Long getInstallerId() {
        return installerId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getJobColor() {
        return jobColor;
    }

    public String getOffice() {
        return office;
    }

    public String getMaterialOrderStatus() {
        return materialOrderStatus;
    }

    public String getMaterialArrivalStatus() {
        return materialArrivalStatus;
    }

    // Setters
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public void setCabinetMakerId(Long cabinetMakerId) {
        this.cabinetMakerId = cabinetMakerId;
    }

    public void setInstallerId(Long installerId) {
        this.installerId = installerId;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setJobColor(String jobColor) {
        this.jobColor = jobColor;
    }

    public void setOffice(String office) {
        this.office = office;
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

}
