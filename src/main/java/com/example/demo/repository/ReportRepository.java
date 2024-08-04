package com.example.demo.repository;

import java.util.List;

import com.example.demo.statusEnum.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByReportUserId(Integer userId);
    List<Report> findByTypeOfReportId(Integer typeOfReportId);
}