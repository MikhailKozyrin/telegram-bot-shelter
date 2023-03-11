package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.UserForCat;
import pro.sky.telegrambot.repository.UserForCatRepository;

@Service
public class UserForCatService {
    @Autowired
    private UserForCatRepository userForCatRepository;

    public UserForCat findUserForCat(Long ChatId) {
        UserForCat userForCat = userForCatRepository.findById(ChatId).orElse(null);
               return userForCat;
    }

    public UserForCat createUserForCat(UserForCat userForCat) {
        return userForCatRepository.save(userForCat);
    }

    public UserForCat editUserForCat(UserForCat userForCat) {
        if (userForCatRepository.findById(userForCat.getChatId()).orElse(null) == null) {
            return null;
        }
        return userForCatRepository.save(userForCat);
    }
}


