package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.dto.LabelDTO;
import com.example.demo.model.Report;
import com.example.demo.statusEnum.ReportStatus;

public interface ReportInterface {
    List<Report> findAllReports();
    List<Report> findReportsByStatus(ReportStatus status);
    List<Report> findReportsByUserId(Integer userId);
    LabelDTO findLabelByReportId(Integer reportId);
    Report findReportById(Integer id);

    // CRUD
    Report saveReport(Report report);
    Report updateReport(Integer id, Report report);
    Report updateReportStatusById(Integer id, ReportStatus status);
    Report caseCloseReport(Integer id);
    void deleteReportById(Integer id);
}