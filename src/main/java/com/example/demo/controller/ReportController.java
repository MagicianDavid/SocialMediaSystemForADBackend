package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.interfacemethods.ReportInterface;
import com.example.demo.model.Report;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportInterface reportService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<Report>> getReportsByStatus(@PathVariable("status") String status) {
        List<Report> reports = reportService.findReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<Report>> getReportsByUserId(@PathVariable("userId") Integer userId) {
        List<Report> reports = reportService.findReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/findByTypeOfReportId/{typeOfReportId}")
    public ResponseEntity<List<Report>> getReportsByTypeOfReportId(@PathVariable("typeOfReportId") Integer typeOfReportId) {
        List<Report> reports = reportService.findReportsByTypeOfReportId(typeOfReportId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable("id") Integer id) {
        Report report = reportService.findReportById(id);
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.saveReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable("id") Integer id, @RequestBody Report report) {
        Report updatedReport = reportService.updateReport(id, report);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Integer id) {
        reportService.deleteReportById(id);
        return ResponseEntity.noContent().build();
    }
}
