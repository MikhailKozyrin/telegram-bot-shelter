package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.ClientNotFoundException;
import pro.sky.telegrambot.model.ClientRegistration;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUser(Long ChatId) {
        User user = userRepository.findById(ChatId).orElse(null);
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(User user) {
        if (userRepository.findById(user.getChatId()).orElse(null) == null) {
            return null;
        }
        return userRepository.save(user);
    }
}
