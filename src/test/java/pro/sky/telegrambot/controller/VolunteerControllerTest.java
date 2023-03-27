package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.telegrambot.model.Volunteer;

import java.util.HashMap;
import java.util.Map;

import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegrambot.constants.Constants.VOLUNTEER_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class VolunteerControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getVolunteer() {
        Volunteer volunteer = new Volunteer(1L, "Misha");
        ResponseEntity<Volunteer> responseCreated = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        Volunteer createdVolunteer = responseCreated.getBody();
        assert createdVolunteer != null;
        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getChatId(),
                Volunteer.class);

        
        Assertions.assertThat(response.getBody()).isEqualTo(createdVolunteer);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createVolunteer() {
        Volunteer volunteer = new Volunteer(1L, "Misha");
        ResponseEntity<Volunteer> response = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, response);
    }

    @Test
    void editVolunteer() {
        String oldName = "Misha";
        long oldChatId = 1234567809;
        String newName = "Misha Kozyrin";
        long newChatId = 1122334455;

        Volunteer volunteer = new Volunteer(1L, "Misha");
        ResponseEntity<Volunteer> responseCreated = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        Volunteer createdVolunteer = responseCreated.getBody();
        assert createdVolunteer != null;
        createdVolunteer.setUserName(newName);
        createdVolunteer.setChatId(newChatId);

        Map< String, String > params = new HashMap< String, String >();
        params.put("id", Long.toString(createdVolunteer.getChatId()));
        restTemplate.put(
                LOCALHOST_URL + port + VOLUNTEER_URL + "/{id}",
                createdVolunteer, params);

        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getChatId(),
                Volunteer.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getUserName()).isEqualTo(newName);
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(newChatId);
    }


    private ResponseEntity<Volunteer> getCreateVolunteerResponse(Volunteer volunteer) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL,
                volunteer,
                Volunteer.class);
    }

    private void assertCreatedVolunteer(Volunteer volunteer, ResponseEntity<Volunteer> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getChatId()).isNotNull();
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(volunteer.getChatId());
    }
}
