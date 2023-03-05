package pro.sky.telegrambot.exception;

public class VolunteerNotFoundException extends RuntimeException {

    private Long id;

    public VolunteerNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
