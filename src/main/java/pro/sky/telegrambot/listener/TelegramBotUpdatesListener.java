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
        } else if (getIncomingMessage(update).equals("Узнать информацию о приюте")) {
            newUserConsultation(update);
        } else if (getIncomingMessage(update).equals("Как взять собаку из приюта")) {
            consultationWithAPotentialGuardian(update);

        } else if (getIncomingMessage(update).equals("Прислать отчет о питомце")) {

        } else if (getIncomingMessage(update).equals("Позвать волонтера")){

        }
    }

    /**
     * <b>Метод для ответа на стартовое сообщение</b><br>
     * @param update
     */
    private void responseToStart(Update update) {  // переделал этот метод, ранее он обращался к методу sendMessage, но пришлось переделать, потому что так я не могу прикрепить клавиатуру к сообщению
        String message = "Приветствуем вас! Этот бот поможет вам выбрать собаку из приюта. Благодарим вас за обращение и за помощь маленьким питомцам";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Узнать информацию о приюте"},
                new String[]{"Как взять собаку из приюта"},
                new String[]{"Прислать отчет о питомце"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendMessage sendMessage = new SendMessage(getId(update),message).replyMarkup(replyKeyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
        logger.info("Отправлено меню на выбор пользователю: " + getId(update));
    }

    private void consultationWithAPotentialGuardian(Update update) { //метод, необходимый для Этап 2(ТЗ)
        String message = "Приветствуем вас! бла бла бла. Этот раздел нужен, если вы реально заинтересованы в том, чтобы взять у нас собаку";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Правила знакомства с собакой"},
                new String[]{"Список необходимых документов"},
                new String[]{"Список рекомендаций по транспортировке животного"},
                new String[]{"Список рекомендаций по обустройству дома для собак"},
                new String[]{"Советы кинолога"},
                new String[]{"Рекомендации по проверенным кинологам"},
                new String[]{"Возможные причины отказа"},
                new String[]{"Оставить контактный данные"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendMessage sendMessage = new SendMessage(getId(update),message).replyMarkup(replyKeyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
        logger.info("Отправлено меню на выбор пользователю: " + getId(update));

    }

    /**
     * <b>Метод для отправки сообщения пользователю</b><br>
     * Указать само сообщение и id чата
     * @param message должно быть отправлено
     * @param chatId не может быть null
     */
    private void sendMessage(String message, Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId,message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }

    }

    private void newUserConsultation(Update update){ //метод, необходимый для этап 1 ТЗ
        String message = "Бот приветствует пользователя. (бла бла бла)";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Информация о нашем приюте"},
                new String[]{"Расписание работы и адрес"},
                new String[]{"Техника безопасности"},
                new String[]{"Оставить контактный данные"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendMessage sendMessage = new SendMessage(getId(update),message).replyMarkup(replyKeyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
        logger.info("Отправлено меню на выбор пользователю: " + getId(update));
    }
}
