package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.UserCat;
import pro.sky.telegrambot.repository.UserCatRepository;

@Service
public class UserCatService {

    @Autowired
    private UserCatRepository userCatRepository;

    public UserCat findUser(Long ChatId) {
        UserCat userCat = userCatRepository.findById(ChatId).orElse(null);
        return userCat;
    }

    public UserCat createUser(UserCat userCat) {
        return userCatRepository.save(userCat);
    }

    public UserCat editUser(UserCat userDog) {
        if (userCatRepository.findById(userDog.getChatId()).orElse(null) == null) {
            return null;
        }
        return userCatRepository.save(userDog);
    }
}
