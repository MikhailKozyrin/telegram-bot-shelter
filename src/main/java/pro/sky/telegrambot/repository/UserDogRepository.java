package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.UserDog;

public interface UserDogRepository extends JpaRepository<UserDog,Long> {

}
