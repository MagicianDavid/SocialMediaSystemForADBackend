package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dto.LabelDTO;
import com.example.demo.model.Label;
import com.example.demo.statusEnum.ReportStatus;
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
    public List<Report> findReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    @Override
    public List<Report> findReportsByUserId(Integer userId) {
        return reportRepository.findByReportUserId(userId);
    }

    @Override
    public LabelDTO findLabelByReportId(Integer id) {
        Label label = reportRepository.findLabelByReportId(id);
        return new LabelDTO(label);
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
        existingReport.setLabel(report.getLabel());
        existingReport.setReason(report.getReason());
        existingReport.setStatus(report.getStatus());
        existingReport.setReportDate(report.getReportDate());
        existingReport.setCaseCloseDate(report.getCaseCloseDate());
        existingReport.setRemarks(report.getRemarks());
        existingReport.setReportUser(report.getReportUser());
        existingReport.setReportedId(report.getReportedId());
        return reportRepository.save(existingReport);
    }

    @Override
    public Report updateReportStatusById(Integer id, ReportStatus status) {
        Report currentReport = findReportById(id);
        currentReport.setStatus(status);
        return reportRepository.save(currentReport);
    }

    @Override
    public Report caseCloseReport(Integer id) {
        Report report = findReportById(id);
        report.setStatus(ReportStatus.Complete);
        report.setCaseCloseDate(LocalDateTime.now());
        return reportRepository.save(report);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReportById(Integer id) {
        reportRepository.deleteById(id);
    }
}