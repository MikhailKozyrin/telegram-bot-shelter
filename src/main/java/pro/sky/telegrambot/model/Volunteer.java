package pro.sky.telegrambot.model;

import liquibase.pro.packaged.C;

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
    private String name;
    private String pleaseOfResidence;

    @OneToMany(mappedBy = "volunteer")
    public Collection<ClientRegistration> clientRegistrations;
    public Volunteer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(name, volunteer.name) && Objects.equals(pleaseOfResidence, volunteer.pleaseOfResidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pleaseOfResidence);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pleaseOfResidence='" + pleaseOfResidence + '\'' +
                '}';
    }
}
