package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.UserCat;
import pro.sky.telegrambot.model.UserDog;

public interface UserCatRepository extends JpaRepository<UserCat,Long> {

}
