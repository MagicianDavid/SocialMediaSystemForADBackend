package com.example.demo.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class TypeOfReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String reportType;

    @Column(nullable = false)
    private Integer weight;

    @OneToMany(mappedBy = "typeOfReport", cascade = CascadeType.ALL)
    private List<Report> reports;

    // Constructors, getters and setters

    public TypeOfReport() {
    }

    public TypeOfReport(String reportType, Integer weight) {
        this.reportType = reportType;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
