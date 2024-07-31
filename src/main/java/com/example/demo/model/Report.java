package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type_of_report_id")
    private TypeOfReport typeOfReport;

    @NotNull
    private String reason;

    @NotNull
    private String status; // Complete, Pending, Appeal

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, getters and setters

    public Report() {
    }

    public Report(TypeOfReport typeOfReport, String reason, String status, LocalDateTime startDate, String remarks, LocalDateTime endDate, User user) {
        this.typeOfReport = typeOfReport;
        this.reason = reason;
        this.status = status;
        this.startDate = startDate;
        this.remarks = remarks;
        this.endDate = endDate;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfReport getTypeOfReport() {
        return typeOfReport;
    }

    public void setTypeOfReport(TypeOfReport typeOfReport) {
        this.typeOfReport = typeOfReport;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}