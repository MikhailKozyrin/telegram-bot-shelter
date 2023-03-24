package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "reports")
public class Report {

    /**
     * <b>Модель направления отчета по животному</b><br>
     * Отчет должен содержать: фото животного, рацион, общее самочувствие и привыкание к новому месту, изменение в поведении
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] picture;
    private String diet;
    private String wellBeingAndAddiction;
    private String changeInBehavior;
    private Long userId;
    private Long volunteerId;
    private String status;

    public Report(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeingAndAddiction() {
        return wellBeingAndAddiction;
    }

    public void setWellBeingAndAddiction(String wellBeingAndAddiction) {
        this.wellBeingAndAddiction = wellBeingAndAddiction;
    }

    public String getChangeInBehavior() {
        return changeInBehavior;
    }

    public void setChangeInBehavior(String changeInBehavior) {
        this.changeInBehavior = changeInBehavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Arrays.equals(picture, report.picture) && Objects.equals(diet, report.diet) && Objects.equals(wellBeingAndAddiction, report.wellBeingAndAddiction) && Objects.equals(changeInBehavior, report.changeInBehavior);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(diet, wellBeingAndAddiction, changeInBehavior);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "Отчет\n" +
                "Номер отчета: " + id +
                "\nID пользователя: " + userId +
                "\nID ответственного волонтера: " + volunteerId +
                "\nСтатус: " + status +
                "\nРацион питомца:\n" +
                diet +
                "\nОбщее самочувствие и привыкание к новому месту:\n" +
                wellBeingAndAddiction +
                "\nИзменения в поведении\n" +
                changeInBehavior;
    }
}
