package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.Volunteer;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    @Query(value = "SELECT chat_id FROM volunteers WHERE status = 'Готов к работе'",nativeQuery = true)
    List<Long> getChatIdWhereStatusIsExpectation();

    @Query(value = "SELECT chat_id FROM volunteers WHERE status = 'Готов к работе' AND chat_id_user = null",nativeQuery = true)
    List<Long> getChatIdWhereStatusIsExpectationAndChatIdUserIsNull();


}
