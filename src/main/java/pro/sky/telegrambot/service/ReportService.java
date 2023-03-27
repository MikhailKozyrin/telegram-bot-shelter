package pro.sky.telegrambot.service;

import liquibase.pro.packaged.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report findReport(Long id) {
        return reportRepository.findReportById(id);
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public Report editReport(Report report) {
        if (reportRepository.getOne(report.getId()) == null) {
            return null;
        }
        return reportRepository.save(report);
    }

    public Report findReportByUserIdAndStatus(Long userId, String status){
        List<Report> reports = reportRepository.findAllByUserIdAndStatus(userId,status);
        return reports.get(0);
    }

    public List<Long> findIdOfReports(){
        return reportRepository.findIdOfReports();
    }

    public Report findReportByStatusAndVolunteerId(String status, Long volunteerId){
        return reportRepository.findReportByStatusAndVolunteerId(status,volunteerId);
    }
}
