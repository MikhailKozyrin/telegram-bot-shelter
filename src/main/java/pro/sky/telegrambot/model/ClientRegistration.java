package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * <b>Модель регистрации клиента</b><br>
 * Контактная информация по клиенту: Id, имя, место регистрации, мобильный телефон
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
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
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
//    public Volunteer getVolunteer() {
//        return volunteer;
//    }

    @Override
    public String toString() {
        return "Клиент" +
                "id " + id +
                ", userId " + userId +
                ", Имя " + name + '\'' +
                ", Место регистрации " + pleaseOfResidence + '\'' +
                ", Мобильный номер телефона " + mobileNumber + '\'' +
                '}';
    }
}
