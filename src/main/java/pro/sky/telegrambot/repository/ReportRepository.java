package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.Report;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findReportById(Long id);

    List<Report> findAllByUserIdAndStatus(Long userId, String status);
    @Query(value = "SELECT id FROM reports WHERE status is null AND volunteer_id is null",nativeQuery = true)
    List<Long> findIdOfReports();

    Report findReportByStatusAndVolunteerId(String status, Long volunteerId);



}
