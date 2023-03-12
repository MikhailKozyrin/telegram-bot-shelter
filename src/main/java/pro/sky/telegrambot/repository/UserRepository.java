package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.User;

public interface UserRepository extends JpaRepository <User, Long> {
    static User findByChatId(Long chatId) {
        return new User();
    }


}


