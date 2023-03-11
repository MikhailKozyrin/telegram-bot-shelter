package pro.sky.telegrambot.exception;

public class UserNotFoundException extends RuntimeException {

    private Long chatId;

    public UserNotFoundException(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }
}
