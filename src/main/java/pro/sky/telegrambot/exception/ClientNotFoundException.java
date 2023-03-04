package pro.sky.telegrambot.exception;

public class ClientNotFoundException extends RuntimeException {
    private final Long id;

    public ClientNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
