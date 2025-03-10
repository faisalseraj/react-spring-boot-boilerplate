package com.kitchensaver.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String jobNumber;
    private String jobName;
    private int numCabinets;
    private int numUppers;
    private int numLowers;
    
    @ManyToOne
    @JoinColumn(name = "cabinet_maker_id")
    private Users cabinetMaker;
    
    @ManyToOne
    @JoinColumn(name = "installer_id")
    private Users installer;
    
    private Date dueDate;
    private String jobColor;
    private String office;
    private String status;
    private String materialOrderStatus;
    private String materialArrivalStatus;
    private String image;

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

    public int getNumCabinets() {
        return numCabinets;
    }

    public int getNumUppers() {
        return numUppers;
    }

    public int getNumLowers() {
        return numLowers;
    }

    public Users getCabinetMaker() {
        return cabinetMaker;
    }

    public Users getInstaller() {
        return installer;
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

    public String getStatus() {
        return status;
    }

    public String getMaterialOrderStatus() {
        return materialOrderStatus;
    }

    public String getMaterialArrivalStatus() {
        return materialArrivalStatus;
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

    public void setNumCabinets(int numCabinets) {
        this.numCabinets = numCabinets;
    }

    public void setNumUppers(int numUppers) {
        this.numUppers = numUppers;
    }

    public void setNumLowers(int numLowers) {
        this.numLowers = numLowers;
    }

    public void setCabinetMaker(Users cabinetMaker) {
        this.cabinetMaker = cabinetMaker;
    }

    public void setInstaller(Users installer) {
        this.installer = installer;
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

    public void setImage(String image) {
        this.image = image;
    }
}
