package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.interfacemethods.ReportInterface;
import com.example.demo.model.Report;
import com.example.demo.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService implements ReportInterface {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findReportsByStatus(String status) {
        return reportRepository.findByStatus(status);
    }

    @Override
    public List<Report> findReportsByUserId(Integer userId) {
        return reportRepository.findByUserId(userId);
    }

    @Override
    public List<Report> findReportsByTypeOfReportId(Integer typeOfReportId) {
        return reportRepository.findByTypeOfReportId(typeOfReportId);
    }

    @Override
    public Report findReportById(Integer id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = false)
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    @Transactional(readOnly = false)
    public Report updateReport(Integer id, Report report) {
        Report existingReport = findReportById(id);
        existingReport.setTypeOfReport(report.getTypeOfReport());
        existingReport.setReason(report.getReason());
        existingReport.setStatus(report.getStatus());
        existingReport.setStartDate(report.getStartDate());
        existingReport.setEndDate(report.getEndDate());
        existingReport.setRemarks(report.getRemarks());
        existingReport.setUser(report.getUser());
        return reportRepository.save(existingReport);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReportById(Integer id) {
        reportRepository.deleteById(id);
    }
}