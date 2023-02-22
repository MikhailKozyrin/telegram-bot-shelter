package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            processingReceivedMessage(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(String message, Long chatId) {           //метод для отправки сообщения пользователю,
        SendMessage sendMessage = new SendMessage(chatId, message);   //укажите само сообщение и id чата
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        }else {
            logger.info("Сообщение отправлено: " + message);
        }
    }

    private Long getId(Update update){
        Long chatId = update.message().chat().id();  // метод, в основном предназначен для логирования запроса id чата
        logger.trace("Получен id чата: " + chatId);
        return chatId;
    }

    private String getIncomingMessage (Update update){    // метод, в основном предназначен для логирования получения сообщения
        String incomingMessage = update.message().text();
        logger.trace("Получено сообщение: " + incomingMessage);
        return incomingMessage;
    }

    private void processingReceivedMessage(Update update){
        if (getIncomingMessage(update).equals("/start")){   //в этом методе будут обрабатываться входящие сообщения,
            responseToStart(update);                        //он будет пополняться
        }
    }

    private void responseToStart(Update update){
        String greetings = "Приветствуем вас. Этот бот поможет вам бла бла бла"; //метод, прелназначенный для ответа на стартовое сообщение
        String menu = "1 - Узнать информацию о приюте\n" +
                "2 - Как взять собаку из приюта\n" +
                "3 - Прислать отчет о питомце\n" +
                "4 - Позвать волонтера";
        sendMessage(greetings,getId(update));
        sendMessage(menu,getId(update));
        logger.info("Отправлено меню на выбор пользователю: " + getId(update));
    }
}
