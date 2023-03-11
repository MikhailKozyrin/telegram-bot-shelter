package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer findVolunteer(Long chatId) {
        Volunteer volunteer = volunteerRepository.findById(chatId).orElse(null);
        if (volunteer == null) {
            throw new VolunteerNotFoundException(chatId);
        }
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getChatId()).orElse(null) == null) {
            return null;
        }
        return volunteerRepository.save(volunteer);
    }
}
