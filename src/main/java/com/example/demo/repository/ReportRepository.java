package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Label;
import com.example.demo.statusEnum.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Report;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByReportUserId(Integer userId);

    @Query("SELECT l FROM Report r JOIN r.label l WHERE r.id = :reportId")
    Label findLabelByReportId(Integer reportId);
}