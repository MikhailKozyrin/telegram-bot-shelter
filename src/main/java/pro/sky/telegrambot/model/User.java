package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Модель для работы с пользователями и волонтерами
 */
@Entity(name = "users")
public class User {

    @Id
    private Long chatId;
    private String userName;
    private String mobileNumber;
    private String lastCommand;
    private boolean volunteerTrigger;

    @OneToMany(mappedBy = "user")
    public Collection<Report> report;

    public User() {
    }

    public User(Long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        this.volunteerTrigger = false;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public boolean isVolunteerTrigger() {
        return volunteerTrigger;
    }

    public void setVolunteerTrigger(boolean volunteerTrigger) {
        this.volunteerTrigger = volunteerTrigger;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Collection<Report> getReport() {
        return report;
    }
}
