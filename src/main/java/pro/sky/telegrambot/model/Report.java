package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * <b>Модель направления отчета по животному</b><br>
 * Отчет должен содержать: фото животного, рацион, общее самочувствие и привыкание к новому месту,
 * изменение в поведении
 */

@Entity(name = "report")
public class Report {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] picture;
    private String diet;
    private String wellBeingAndAddiction;
    private String changeInBehavior;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, report.id) && Arrays.equals(picture, report.picture) && Objects.equals(diet, report.diet) && Objects.equals(wellBeingAndAddiction, report.wellBeingAndAddiction) && Objects.equals(changeInBehavior, report.changeInBehavior);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, diet, wellBeingAndAddiction, changeInBehavior);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", picture=" + Arrays.toString(picture) +
                ", diet='" + diet + '\'' +
                ", wellBeingAndAddiction='" + wellBeingAndAddiction + '\'' +
                ", changeInBehavior='" + changeInBehavior + '\'' +
                '}';
    }
}
