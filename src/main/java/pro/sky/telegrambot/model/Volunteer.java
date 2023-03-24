package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "volunteers")
public class Volunteer {

    @Id
    private Long chatId;
    private String userName;
    private Long chatIdUser;
    private String status;

    public Volunteer(Long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
    }

    public Volunteer() {

    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getChatIdUser() {
        return chatIdUser;
    }

    public void setChatIdUser(Long chatIdUser) {
        this.chatIdUser = chatIdUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
