package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public Volunteer findVolunteer(Long ChatId) {
        Volunteer volunteer = volunteerRepository.findById(ChatId).orElse(null);
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getChatId()).orElse(null) == null){
            return null;
        }
        return volunteerRepository.save(volunteer);
    }

    public List<Long> getChatIdWhereStatusIsExpectation(){
        return volunteerRepository.getChatIdWhereStatusIsExpectation();
    }

    public List<Long> getChatIdWhereStatusIsExpectationAndChatIdUserIsNull(){
        return volunteerRepository.getChatIdWhereStatusIsExpectationAndChatIdUserIsNull();
    }
}
