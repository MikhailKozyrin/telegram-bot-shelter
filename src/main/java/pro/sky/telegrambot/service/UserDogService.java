package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.UserDog;
import pro.sky.telegrambot.repository.UserDogRepository;

@Service
public class UserDogService {

    @Autowired
    private UserDogRepository userDogRepository;

    public UserDog findUser(Long ChatId) {
        UserDog userDog = userDogRepository.findById(ChatId).orElse(null);
        return userDog;
    }

    public UserDog createUser(UserDog userDog) {
        return userDogRepository.save(userDog);
    }

    public UserDog editUser(UserDog userDog) {
        if (userDogRepository.findById(userDog.getChatId()).orElse(null) == null) {
            return null;
        }
        return userDogRepository.save(userDog);
    }
}
