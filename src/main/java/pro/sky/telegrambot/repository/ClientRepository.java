package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.ClientRegistration;

public interface ClientRepository extends JpaRepository<ClientRegistration,Long> {
}