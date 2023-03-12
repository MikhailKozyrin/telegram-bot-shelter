package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
/**
 * <b>Модель регистрации волонтера</b><br>
 * Контактная информация по клиенту: Id, имя, место регистрации
 */
@Entity
@Table(name = "volunteers")

public class Volunteer {

    @Id
    private Long chatId;
    private String name;
    private String lastCommand;

    @OneToMany(mappedBy = "volunteer")
    public Collection<Report> report;

    public Volunteer() {

    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(chatId, volunteer.chatId) && Objects.equals(name, volunteer.name) && Objects.equals(lastCommand, volunteer.lastCommand) && Objects.equals(report, volunteer.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, lastCommand, report);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", lastCommand='" + lastCommand + '\'' +
                '}';
    }
}
