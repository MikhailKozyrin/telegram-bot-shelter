package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * <b>Модель регистрации клиента</b><br>
 * Контактная информация по клиенту: Id, ФИО, мобильный телефон
 */
@Entity
public class ClientRegistration {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private long userId;
    private String name;
    private String pleaseOfResidence;
    private String mobileNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPleaseOfResidence() {
        return pleaseOfResidence;
    }

    public void setPleaseOfResidence(String pleaseOfResidence) {
        this.pleaseOfResidence = pleaseOfResidence;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRegistration that = (ClientRegistration) o;
        return userId == that.userId && Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(pleaseOfResidence, that.pleaseOfResidence)
                && Objects.equals(mobileNumber, that.mobileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, pleaseOfResidence, mobileNumber);
    }
}
