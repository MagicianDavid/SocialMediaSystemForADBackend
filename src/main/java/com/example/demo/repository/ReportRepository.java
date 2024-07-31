package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByStatus(String status);
    List<Report> findByUserId(Integer userId);
    List<Report> findByTypeOfReportId(Integer typeOfReportId);
}