package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.ClientNotFoundException;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.ClientRegistration;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer findVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer == null) {
            throw new VolunteerNotFoundException(id);
        }
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getId()).orElse(null) == null) {
            return null;
        }
        return volunteerRepository.save(volunteer);
    }
}
