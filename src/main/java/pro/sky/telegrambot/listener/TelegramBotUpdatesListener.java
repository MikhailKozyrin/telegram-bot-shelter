package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;

/**
 * <b>Методы и обработка всех действий в Телеграм-боте</b>
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * <b>Обработка входящих сообщений</b><br>
     * Используется интерфейс UpdateListener
     * @param updates available updates, не может быть null
     * @return Успешная обработка
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            processingReceivedMessage(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * <b>Метод для логирования запроса id чата</b>
     * @param update не можеть быть null
     * @return Полученный Id чата
     */
    private Long getId(Update update) {
        Long chatId = update.message().chat().id();
        logger.trace("Получен id чата: " + chatId);
        return chatId;
    }

    /**
     * <b>Метод для логирования получения сообщения</b>
     * @param update не можеть быть null
     * @return Полученное сообщение
     */
    private String getIncomingMessage(Update update) {
        String incomingMessage = update.message().text();
        logger.trace("Получено сообщение: " + incomingMessage);
        return incomingMessage;
    }

    /**
     * <b>Метод для обработки входящих сообщений</b><br>
     * Постоянно обновляется
     * @param update
     */
    private void processingReceivedMessage(Update update) {
        if (getIncomingMessage(update).equals("/start")) {
            responseToStart(update);
        } else if (getIncomingMessage(update).equals(INFORMATION_ABOUT_SHELTER)) {
            newUserConsultation(update);
        } else if (getIncomingMessage(update).equals(TAKE_ON_THE_DOG)) {
            consultationWithAPotentialGuardian(update);
        } else if (getIncomingMessage(update).equals(HOME_IMPROVEMENT)) {
            homeFurnishingForDogs(update);

        } else if (getIncomingMessage(update).equals(CALL_VOLUNTEER)){

        }
    }

    /**
     * <b>Метод для ответа на стартовое сообщение</b><br>
     * @param update
     */
    private void responseToStart(Update update) {  // переделал этот метод, ранее он обращался к методу sendMessage, но пришлось переделать, потому что так я не могу прикрепить клавиатуру к сообщению
        String message = WELCOME_TEXT;
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{INFORMATION_ABOUT_SHELTER},
                new String[]{TAKE_ON_THE_DOG},
                new String[]{SEND_A_REPORT},
                new String[]{CALL_VOLUNTEER})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, message, replyKeyboardMarkup);
    }
    /**
     * <b>Метод для отправки сообщения пользователю</b><br>
     * Указать само сообщение и id чата
     * @param message должно быть отправлено
     */
    private void sendMessage(Update update, String message, Keyboard replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(getId(update),message).replyMarkup(replyKeyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
    }

    private void consultationWithAPotentialGuardian(Update update) { //метод, необходимый для Этап 2(ТЗ)
        String message = WELCOME_TEXT_TAKE_ON_THE_DOG;
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{DATING_RULES},
                new String[]{LIST_OF_DOCUMENTS},
                new String[]{TRANSPORTATION},
                new String[]{HOME_IMPROVEMENT},
                new String[]{TIPS_FROM_A_DOG_HANDLER},
                new String[]{RECOMMENDATION_DOG_HANDLER},
                new String[]{REASONS_FOR_REFUSAL},
                new String[]{LEAVE_CONTACTS},
                new String[]{CALL_VOLUNTEER})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, message, replyKeyboardMarkup);
    }

    private void newUserConsultation(Update update){ //метод, необходимый для этапа 1 ТЗ
        String message = WELCOME_TEXT_INFORMATION_ABOUT_SHELTER;
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{INFORMATION_ABOUT_SHELTER},
                new String[]{ADDRESS},
                new String[]{SAFETY_PRECAUTIONS},
                new String[]{LEAVE_CONTACTS},
                new String[]{CALL_VOLUNTEER})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, message, replyKeyboardMarkup);
    }

    private void homeFurnishingForDogs(Update update){ //метод, который выводит кнопки с рекомендациями по обустройству собак, щенков, и т.д.
        String message = HOME_IMPROVEMENT_ANSWER;
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{FOR_A_PUPPY},
                new String[]{FOR_AN_ADULT_DOG},
                new String[]{FOR_A_SICK_DOG})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, message, replyKeyboardMarkup);

    }
}
