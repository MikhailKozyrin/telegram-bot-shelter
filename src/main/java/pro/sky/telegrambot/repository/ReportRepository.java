package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.Report;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByUserIdAndStatus(Long userId, String status);
    @Query(value = "SELECT id FROM reports WHERE status = null AND volunteer_id = null",nativeQuery = true)
    List<Long> findIdOfReports();
}
