package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>
{
    List<Report> findByPostId(Long postId);
}