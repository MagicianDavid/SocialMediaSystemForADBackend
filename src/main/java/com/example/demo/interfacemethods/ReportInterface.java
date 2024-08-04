package com.example.demo.interfacemethods;

import java.util.List;
import com.example.demo.model.Report;

public interface ReportInterface {
	
	Integer CountReports();
	List<Report> findTop5ByOrderByDateDesc();
    List<Report> findAllReports();
    List<Report> findReportsByStatus(String status);
    List<Report> findReportsByUserId(Integer userId);
    List<Report> findReportsByTypeOfReportId(Integer typeOfReportId);
    Report findReportById(Integer id);
    Report saveReport(Report report);
    Report updateReport(Integer id, Report report);
    void deleteReportById(Integer id);
}