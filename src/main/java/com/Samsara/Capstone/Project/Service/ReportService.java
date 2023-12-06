package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Report;
import com.Samsara.Capstone.Project.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService{
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report){
        return  reportRepository.save(report);
    }
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    public void deleteReport(Long id){
        reportRepository.deleteById(id );
    }
    public List<Report> getReportsByPostId(Long postId) {
        return reportRepository.findByPostId(postId);
    }
}
