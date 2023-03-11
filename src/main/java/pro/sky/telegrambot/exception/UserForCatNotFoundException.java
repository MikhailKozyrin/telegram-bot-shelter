package pro.sky.telegrambot.exception;

public class UserForCatNotFoundException extends RuntimeException {

    private Long chatId;

    public UserForCatNotFoundException(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }
}

