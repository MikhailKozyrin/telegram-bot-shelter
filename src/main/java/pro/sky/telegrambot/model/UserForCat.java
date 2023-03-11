package pro.sky.telegrambot.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Модель для работы с пользователями приюта для котов и волонтерами
 */
@Entity(name = "users_for_cat")
public class UserForCat {

    @Id
    private Long chatId;
    private String userName;
    private String mobileNumber;
    private String lastCommand;
    private boolean volunteerTriggerCat;

    public UserForCat() {

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public boolean isVolunteerTriggerCat() {
        return volunteerTriggerCat;
    }

    public void setVolunteerTriggerCat(boolean volunteerTriggerCat) {
        this.volunteerTriggerCat = volunteerTriggerCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForCat that = (UserForCat) o;
        return volunteerTriggerCat == that.volunteerTriggerCat && Objects.equals(chatId, that.chatId) && Objects.equals(userName, that.userName) && Objects.equals(mobileNumber, that.mobileNumber) && Objects.equals(lastCommand, that.lastCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userName, mobileNumber, lastCommand, volunteerTriggerCat);
    }

    @Override
    public String toString() {
        return "UserForCat{" +
                "chatId=" + chatId +
                ", userName='" + userName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", lastCommand='" + lastCommand + '\'' +
                ", volunteerTrigger=" + volunteerTriggerCat +
                '}';
    }
}

