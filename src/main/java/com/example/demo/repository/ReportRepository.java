package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	@Query("SELECT COUNT(r) FROM Report r")
	Integer countReport();
	
    List<Report> findTop5ByOrderByStartDateDesc();
    List<Report> findByStatus(String status);
    List<Report> findByUserId(Integer userId);
    List<Report> findByTypeOfReportId(Integer typeOfReportId);
}