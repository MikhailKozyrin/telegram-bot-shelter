package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.UserForCat;


public interface UserForCatRepository extends JpaRepository<UserForCat,Long> {
}
