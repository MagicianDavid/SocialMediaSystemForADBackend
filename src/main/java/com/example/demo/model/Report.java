package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.statusEnum.ReportStatus;
import jakarta.persistence.*;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type_of_report_id")
    private TypeOfReport typeOfReport;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status; // Complete, Pending, Appeal

    @Column(name = "report_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime reportDate;

    @Column(name = "case_close_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime caseCloseDate;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reportUser;

    // this is the id we are reporting (it could be pcmsg_id or user_id)
    private Integer reportedId;

    // Constructors, getters and setters

    public Report() {
    }

    public Report(TypeOfReport typeOfReport, String reason, ReportStatus status, LocalDateTime reportDate, LocalDateTime caseCloseDate,String remarks, User user, Integer reportedId) {
        this.typeOfReport = typeOfReport;
        this.reason = reason;
        this.status = status;
        this.reportDate = reportDate;
        this.caseCloseDate = caseCloseDate;
        this.remarks = remarks;
        this.reportUser = user;
        this.reportedId = reportedId;
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

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDateTime getCaseCloseDate() {return caseCloseDate;}

    public void setCaseCloseDate(LocalDateTime caseCloseDate) {this.caseCloseDate = caseCloseDate;}

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getUser() {
        return reportUser;
    }

    public void setUser(User user) {
        this.reportUser = user;
    }

    public Integer getReportedId() {
        return reportedId;
    }

    public void setReportedId(Integer reportedId) {this.reportedId = reportedId;}
}