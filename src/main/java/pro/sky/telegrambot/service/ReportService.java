package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report findReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        return report;
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public Report editReport(Report report) {
        if (reportRepository.findById(report.getId()).orElse(null) == null) {
            return null;
        }
        return reportRepository.save(report);
    }
}

