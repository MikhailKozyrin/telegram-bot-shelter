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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String name;
    private String lastCommand;


    public Volunteer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, volunteer.id) && Objects.equals(chatId, volunteer.chatId) && Objects.equals(name, volunteer.name) && Objects.equals(lastCommand, volunteer.lastCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, lastCommand);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", lastCommand='" + lastCommand + '\'' +
                '}';
    }
}
