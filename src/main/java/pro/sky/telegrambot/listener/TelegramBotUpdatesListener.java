package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.UserCat;
import pro.sky.telegrambot.model.UserDog;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.UserCatService;
import pro.sky.telegrambot.service.UserDogService;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    @Autowired
    private UserDogService userDogService;

    @Autowired
    private UserCatService userCatService;

    @Autowired
    private ReportService reportService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * <b>Обработка входящих сообщений</b><br>
     * Используется интерфейс UpdateListener
     *
     * @param updates available updates, не может быть null
     * @return Успешная обработка
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            newUserCheck(update);
            Object user = getUser(update);
            String lastCommand = null;
            if (user != null) {
                lastCommand = getLastCommand(user);
            }
            if (lastCommand == null) {
                processingReceivedMessage(update, user);
            } else {
                commandProcessing(lastCommand, update, user);
            }
            logger.info("Processing update: {}", update);

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод для получения последней команды
     *
     * @param user пользователь
     * @return последняя команда пользователя
     */
    private String getLastCommand(Object user) {
        String lastCommand;
        if (userTypeDefinition(user)) {
            UserDog userDog = (UserDog) user;
            lastCommand = userDog.getLastCommand();
        } else {
            UserCat userCat = (UserCat) user;
            lastCommand = userCat.getLastCommand();
        }
        return lastCommand;
    }

    /**
     * Используется для определения типа пользователя
     * @param user пользователь
     * @return true - собака, false - кот
     */
    private boolean userTypeDefinition(Object user) {
        return user.getClass().getName().equals("pro.sky.telegrambot.model.UserDog");
    }

    /**
     * Метод вовращается объект пользователя
     * @return пользователь
     */
    private Object getUser(Update update) {
        Object user = userDogService.findUser(getChatId(update));
        if (user == null) {
            user = userCatService.findUser(getChatId(update));
        }
        return user;
    }

    /**
     * С помощью этого метода идет обработка команды /start и выюор приюта
     */
    private void newUserCheck(Update update) {
        if ("/start".equals(getIncomingMessage(update))) {
            choiceOfShelter(update);
        } else if (DOG_SHELTER.equals(getIncomingMessage(update))) {
            findUserDogOrCreate(update);
            mainMenu(update);
        } else if (CAT_SHELTER.equals(getIncomingMessage(update))) {
            findUserCatOrCreate(update);
            mainMenu(update);
        }
    }

    /**
     * Клавиатура для выбора приюта
     */
    private void choiceOfShelter(Update update) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                DOG_SHELTER, CAT_SHELTER)
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, CHOICE_OF_SHELTER, replyKeyboardMarkup);


    }

    /**
     * <b>Метод для логирования запроса id чата</b>
     *
     * @param update не можеть быть null
     * @return Полученный Id чата
     */
    private Long getChatId(Update update) {
        Long chatId = update.message().chat().id();
        logger.trace("Получен id чата: " + chatId);
        return chatId;
    }

    private String getUserName(Update update) {
        String username = update.message().chat().username();
        logger.trace("Получен username пользователя " + username);
        return username;
    }

    /**
     * <b>Метод для логирования получения сообщения</b>
     *
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
     */
    private void processingReceivedMessage(Update update, Object user) {
        if ("/menu".equals(getIncomingMessage(update))) {
            mainMenu(update);
        } else if (INFORMATION_ABOUT_SHELTER.equals(getIncomingMessage(update))) {
            newUserConsultation(update);
        } else if (TAKE_ON_THE_ANIMAL.equals(getIncomingMessage(update))) {
            consultationWithAPotentialGuardian(update, user);
        } else if (HOME_IMPROVEMENT.equals(getIncomingMessage(update))) {
            homeFurnishingForDogs(update);
        } else if (LEAVE_CONTACTS.equals(getIncomingMessage(update))) {
            recordingContactInformation(update, user);
        } else if (SEND_A_REPORT.equals(getIncomingMessage(update))) {
            reportRecord(update, user);

        }
    }

    /**
     * Метод, который вызывается, если user желает отправить отчет
     */
    private void reportRecord(Update update, Object user) {
        if (userTypeDefinition(user)) {
            UserDog userDog = (UserDog) user;
            if (userDog.isVolunteerTrigger()) {
                setLastCommand(userDog, "Ожидается фото");
                sendMessage(update, "Отправьте фото питомца");
            } else {
                sendMessage(update, "Отправка отчета недоступна, свяжитесь с волонтером");
            }
        } else {
            UserCat userCat = (UserCat) user;
            if (userCat.isVolunteerTrigger()) {
                setLastCommand(userCat, "Ожидается фото");
                sendMessage(update, "Отправьте фото питомца");
            } else {
                sendMessage(update, "Отправка отчета недоступна, свяжитесь с волонтером");
            }
        }
    }

    /**
     * Метод для установки последней команды
     * @param user объект пользователя
     * @param lastCommand последняя команда, которую установит метод
     */
    private void setLastCommand(Object user, String lastCommand) {
        if (userTypeDefinition(user)) {
            UserDog userDog = (UserDog) user;
            userDog.setLastCommand(lastCommand);
            userDogService.editUser(userDog);
        } else {
            UserCat userCat = (UserCat) user;
            userCat.setLastCommand(lastCommand);
            userCatService.editUser(userCat);
        }
    }

    /**
     * <b>Метод для ответа на стартовое сообщение</b><br>
     */
    private void mainMenu(Update update) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{INFORMATION_ABOUT_SHELTER},
                new String[]{TAKE_ON_THE_ANIMAL},
                new String[]{SEND_A_REPORT},
                new String[]{CALL_VOLUNTEER})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, WELCOME_TEXT, replyKeyboardMarkup);
    }

    /**
     * <b>Метод для отправки сообщения пользователю</b><br>
     * Указать само сообщение и id чата
     *
     * @param message должно быть отправлено
     */
    private void sendMessage(Update update, String message, Keyboard replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(getChatId(update), message).replyMarkup(replyKeyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
    }

    /**
     * Метод, который вызывает при отправке сообщения
     *
     * @param update  используем, чтобы получить id чата
     * @param message само сообщение
     */
    private void sendMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage(getChatId(update), message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.warn("Сообщение не отправлено: {}, error code: {}", message, response.errorCode());
        } else {
            logger.info("Сообщение отправлено: " + message);
        }
    }

    /**
     * Метод, который прикрепляет клавиатуру к сообщению, и отправляет его
     *
     * @param update используем для передачи в метод sendMessage
     */
    private void consultationWithAPotentialGuardian(Update update, Object user) { //метод, необходимый для Этап 2(ТЗ)
        String message = WELCOME_TEXT_TAKE_ON_THE_DOG;
        Keyboard replyKeyboardMarkup;
        if (userTypeDefinition(user)) {
            replyKeyboardMarkup = new ReplyKeyboardMarkup(
                    new String[]{DATING_RULES},
                    new String[]{LIST_OF_DOCUMENTS},
                    new String[]{TRANSPORTATION},
                    new String[]{HOME_IMPROVEMENT},
                    new String[]{TIPS_FROM_A_DOG_HANDLER},
                    new String[]{RECOMMENDATION_DOG_HANDLER},
                    new String[]{REASONS_FOR_REFUSAL},
                    new String[]{LEAVE_CONTACTS},
                    new String[]{CALL_VOLUNTEER})
                    .oneTimeKeyboard(false)   // optional
                    .resizeKeyboard(true);    // optional
            sendMessage(update, message, replyKeyboardMarkup);
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup(
                    new String[]{DATING_RULES},
                    new String[]{LIST_OF_DOCUMENTS},
                    new String[]{TRANSPORTATION},
                    new String[]{HOME_IMPROVEMENT},
                    new String[]{REASONS_FOR_REFUSAL},
                    new String[]{LEAVE_CONTACTS},
                    new String[]{CALL_VOLUNTEER})
                    .oneTimeKeyboard(true)   // optional
                    .resizeKeyboard(true)    // optional
                    .selective(true);
            sendMessage(update, message, replyKeyboardMarkup);

        }
        sendMessage(update, message, replyKeyboardMarkup);
    }

    /**
     * Метод, который прикрепляет клавиатуру к сообщению, и отправляет его
     *
     * @param update используем для передачи в метод sendMessage
     */
    private void newUserConsultation(Update update) { //метод, необходимый для этапа 1 ТЗ
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{INFORMATION_ABOUT_SHELTER},
                new String[]{ADDRESS},
                new String[]{SAFETY_PRECAUTIONS},
                new String[]{LEAVE_CONTACTS},
                new String[]{CALL_VOLUNTEER})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, WELCOME_TEXT_INFORMATION_ABOUT_SHELTER, replyKeyboardMarkup);
    }

    /**
     * Метод, который прикрепляет клавиатуру к сообщению, и отправляет его
     *
     * @param update используем для передачи в метод sendMessage
     */
    private void homeFurnishingForDogs(Update update) { //метод, который выводит кнопки с рекомендациями по обустройству собак, щенков, и т.д.
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{FOR_A_PUPPY},
                new String[]{FOR_AN_ADULT_DOG},
                new String[]{FOR_A_SICK_DOG})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        sendMessage(update, HOME_IMPROVEMENT_ANSWER, replyKeyboardMarkup);
    }

    /**
     * Метод, который используется, если пользователь желает оставить контактные данные
     *
     * @param update используем для получения id чата
     */
    private void recordingContactInformation(Update update, Object user) {
        if (userTypeDefinition(user)) {
            UserDog userDog = userDogService.findUser(getChatId(update));
            userDog.setLastCommand("Ожидается username");
            userDogService.editUser(userDog);
        } else {
            UserCat userCat = userCatService.findUser(getChatId(update));
            userCat.setLastCommand("Ожидается username");
            userCatService.editUser(userCat);
        }
        sendMessage(update, "Как к вам обращаться?");
    }

    /**
     * Метод, который используется для обработки команд, подразумевающих работу с БД
     *
     * @param lastCommand
     * @param update
     */
    private void commandProcessing(String lastCommand, Update update, Object user) {
        if (EXPECTED_USERNAME.equals(lastCommand)) {
            usernameEntry(update, user);
        } else if (EXPECTED_PHONE_NUMBER.equals(lastCommand)) {
            phoneNumberEntry(update, user);
        } else if (EXPECTED_PHOTO.equals(lastCommand)){
            photoRecord(update, user);
        } else if (EXPECTED_RATION.equals(lastCommand)) {
            dietRecord(update, user);
        } else if (EXPECTED_WELL_BEING.equals(lastCommand)) {
            wellBeingRecord(update, user);
        } else if (EXPECTED_CHANGE_IN_BEHAVIOR.equals(lastCommand)) {
            changeInBehaviorRecord(update,user);

        }
    }

    /**
     * Метод, который вызывается для записи изменений в поведении в отчет
     * @param user пользователь
     */
    private void changeInBehaviorRecord(Update update, Object user) {
        Report report = reportService.findReportByUserIdAndStatus(getChatId(update),EXPECTED_CHANGE_IN_BEHAVIOR);
        report.setChangeInBehavior(getIncomingMessage(update));
        setStatus(report, null);
        reportService.editReport(report);
        setLastCommand(user, null);
        sendMessage(update, "Переводим вас в главное меню");
        mainMenu(update);
    }

    /**
     * Метод, который вызывается для записи общего самочувствия в отчет
     * @param update
     * @param user пользователь
     */
    private void wellBeingRecord(Update update, Object user) {
        Report report = reportService.findReportByUserIdAndStatus(getChatId(update),EXPECTED_WELL_BEING);
        report.setWellBeingAndAddiction(getIncomingMessage(update));
        setStatus(report, EXPECTED_CHANGE_IN_BEHAVIOR);
        reportService.editReport(report);
        setLastCommand(user, EXPECTED_CHANGE_IN_BEHAVIOR);
        sendMessage(update, EXPECTED_CHANGE_IN_BEHAVIOR);
    }

    /**
     * Метод, который вызывается для записи рациона в отчет
     * @param user пользователь
     */
    private void dietRecord(Update update, Object user) {
        Report report = reportService.findReportByUserIdAndStatus(getChatId(update),EXPECTED_RATION);
        report.setDiet(getIncomingMessage(update));
        setStatus(report, EXPECTED_WELL_BEING);
        reportService.editReport(report);
        setLastCommand(user, EXPECTED_WELL_BEING);
        sendMessage(update, EXPECTED_WELL_BEING);
    }

    /**
     * Метод, который вызывается для записи фото в отчет
     */
    private void photoRecord(Update update, Object user) {
        PhotoSize photoSize = update.message().photo()[3];
        if (photoSize != null){
            GetFile getFile = new GetFile(photoSize.fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFile);
            if (getFileResponse.isOk()){
                File file = getFileResponse.file();
                try {
                    byte[] image = telegramBot.getFileContent(file);
                    Report report = new Report();
                    report.setPicture(image);
                    report.setUserId(getChatId(update));
                    setStatus(report, EXPECTED_RATION);
                    reportService.createReport(report);
                    setLastCommand(user, EXPECTED_RATION);
                    sendMessage(update, EXPECTED_RATION);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Метод для установки статуса в отчет
     * @param report отчет
     * @param status статус
     */
    private void setStatus(Report report, String status){
        if (report != null){
            report.setStatus(status);
        }
    }

    /**
     * Метод, который используется для записи в БД номера телефона пользователя
     *
     * @param update
     */
    private void phoneNumberEntry(Update update, Object user) {
        if (userTypeDefinition(user)) {
            UserDog userDog = findUserDogOrCreate(update);
            userDog.setMobileNumber(getIncomingMessage(update));
            setLastCommand(userDog, null);
        } else {
            UserCat userCat = findUserCatOrCreate(update);
            userCat.setMobileNumber(getIncomingMessage(update));
            setLastCommand(userCat, null);
        }
        sendMessage(update, "Благодарю");
        sendMessage(update, "Переводим вас в главное меню");
        mainMenu(update);
    }

    /**
     * Метод, который возвращает пользователя, а если его нет, то создает его
     *
     * @param update используется для получения id чата
     * @return пользователь
     */
    private UserDog findUserDogOrCreate(Update update) {
        UserDog userDog = userDogService.findUser(getChatId(update));
        if (userDog == null) {
            return userDogService.createUser(new UserDog(getChatId(update), getUserName(update)));
        }
        return userDog;
    }

    private UserCat findUserCatOrCreate(Update update) {
        UserCat userCat = userCatService.findUser(getChatId(update));
        if (userCat == null) {
            return userCatService.createUser(new UserCat(getChatId(update), getUserName(update)));
        }
        return userCat;
    }

    /**
     * Метод, который используется для записи в БД имени пользователя
     *
     * @param update используется для поиска пользователя и получения его сообщения
     */
    private void usernameEntry(Update update, Object user) {
        if (userTypeDefinition(user)) {
            UserDog userDog = findUserDogOrCreate(update);
            userDog.setUserName(getIncomingMessage(update));
            setLastCommand(userDog, "Ожидается номер телефона");
        } else {
            UserCat userCat = findUserCatOrCreate(update);
            userCat.setUserName(getIncomingMessage(update));
            setLastCommand(userCat,"Ожидается номер телефона");
        }
        sendMessage(update, "Укажите номер вашего телефона");
    }
}
