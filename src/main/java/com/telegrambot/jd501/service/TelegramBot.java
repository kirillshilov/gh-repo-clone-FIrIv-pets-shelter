package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import com.telegrambot.jd501.configuration.TelegramBotSetButtons;
import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    final TelegramBotConfiguration config;
    private final TelegramBotSetButtons buttons = new TelegramBotSetButtons();

    private final UserService userService;
    private final PetReportService petReportService;

    private final InformationMessageRepository infoRepository;

    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    /**
     * String with start command in chat
     */
    private final String START_FIRST_COMMAND = "/start";
    private final String CHOOSE_MENU_ITEM_STRING = "*** Выберите интересующий пункт меню ***";


    /**
     * ArrayList with button's names
     */

    private final List<String> BUTTONS_NAMES = new ArrayList<>(List.of(
            "Информация о приюте", "Как приютить питомца?",
            "Прислать отчет", "Оставить контакт для связи",
            "Позвать волонтера",

            "Общая информация", "Расписание работы, адрес",
            "Техника безопасности", "вернуться в главное меню",

            "Знакомство с питомцем", "Необходимые документы", "Как перевозить",
            "Дом для щенка", "Дом для взрослой собаки", "Дом для собаки с огр.возможностями",
            "Советы кинолога", "Контакты кинологов", "Причины, по которым могут отказать",
            "вернуться в главное меню"

    ));

    //              *******  Меню 1 *******
    //        1 - Информация о приюте       (0)     2 - Как приютить питомца?     (1)
    //        3 - Прислать отчет            (2)     4 - Оставить данные для связи (3)
    //        5 - Позвать волонтера         (4)
    //             *******  Меню 2 *******
    //        11 - Общая информация         (5)     12 - Расписание работы, адрес (6)
    //        13 - Техника безопасности     (7)     0  - вернуться в главное меню (8)
    //             *******  Меню 3 *******
    //        21 - Знакомство с питомцем  (9)       22 - Необходимые документы (10)   23 - Как перевозить (11)
    //        24 - Дом для щенка (12)               25 - Дом для взрослой собаки (13) 26 - Дом для собаки с огр.возможностями (14)
    //        27 - Советы кинолога (15)             28 - Контакты кинологов (16)      29 - Причины, по которым могут отказать (17)
    //        0  - вернуться в главное меню (18)


    public TelegramBot(TelegramBotConfiguration config, InformationMessageRepository infoRepository, UserService userService, PetReportService petReportService) {
        this.config = config;
        this.userService = userService;
        this.petReportService = petReportService;
        this.infoRepository = infoRepository;
    }

    /**
     * Get name of bot
     *
     * @return string with BotName
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Get bot's token
     *
     * @return string with token's data
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Get incoming updates
     *
     * @param update list of incoming updates, must be not Null
     */
    @Override

    public void onUpdateReceived(Update update) { // ********* здесь ошибка (при вводе произвольной строки)
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // --- send incoming message (or pressed key) for checking -----
                checkInputMessage(update);
            } else if (update.getMessage().hasContact()) {
                // --- if pressed key get contact call method getContact() -----
                getContact(update);
            }
        } catch (Exception e) {
            logger.error("Error occured in method onUpdateReceived: " + e.getMessage());
        }
    }

    /**
     * Check message from user (or what key is pressed)
     *
     * @param update list of incoming updates, must be not Null
     */
    private void checkInputMessage(Update update) {

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage messageToSend = new SendMessage();

        int idMessage = -1;
        for (int i = 0; i < BUTTONS_NAMES.size(); i++) {
            if (BUTTONS_NAMES.get(i).equals(messageText)) {
                idMessage = i;
                break;
            }
        }
        switch (idMessage) {
            // --- 1 - "Информация о приюте" button is pressed  (0)---
            case 0:
                messageToSend = informAboutShelter(chatId);
                break;
            // --- 2 - "Как приютить питомца?" button is pressed (1)---
            case 1:
                messageToSend = informToPotentialAdopter(chatId);
                break;
            // --- 3 - "Прислать отчет" button is pressed (2)---
            case 2:
                messageToSend = waitForReport(chatId);
                break;
//            // --- 4 - "Оставить данные для связи" button is pressed (3)---
//            case 3:
//                //   messageToSend = getContact(update);
//                break;
            // --- 5 - "Позвать волонтера" (4) button is pressed ---
            case 4:
                messageToSend = callToVolunteer(chatId);
                break;
            // --- 0 - "вернуться в главное меню" (8) button is pressed ---
            case 8:
            case 18:
                // call main menu ---
                messageToSend = buttons.setButtons(chatId, BUTTONS_NAMES, 0, 5);
                messageToSend.setText(CHOOSE_MENU_ITEM_STRING);
                break;
            // --- 11 - "Общая информация" (5) button is pressed ---
            case 5:
                messageToSend = getInfo(chatId, 11);
                break;
            // --- 12 - "Расписание работы, адрес" (6) button is pressed ---
            case 6:
                messageToSend = getInfo(chatId, 12);
                break;
            // --- 13 - "Техника безопасности" (7) button is pressed ---
            case 7:
                messageToSend = getInfo(chatId, 13);
                break;

            //
            //        0  - вернуться в главное меню (18)
            // --- 21 - Знакомство с питомцем  (9) button is pressed ---
            case 9:
                messageToSend = getInfo(chatId, 21);
                break;
            // --- 22 - Необходимые документы (10) button is pressed ---
            case 10:
                messageToSend = getInfo(chatId, 22);
                break;
            // --- 23 - Как перевозить (11) button is pressed ---
            case 11:
                messageToSend = getInfo(chatId, 23);
                break;
            // --- 24 - Дом для щенка (12) button is pressed ---
            case 12:
                messageToSend = getInfo(chatId, 24);
                break;
            // --- 25 - Дом для взрослой собаки (13) button is pressed ---
            case 13:
                messageToSend = getInfo(chatId, 25);
                break;
            // --- 26 - Дом для собаки с огр.возможностями (14) button is pressed ---
            case 14:
                messageToSend = getInfo(chatId, 26);
                break;
            // --- 27 - Советы кинолога (15) button is pressed ---
            case 15:
                messageToSend = getInfo(chatId, 27);
                break;
            // --- 28 - Контакты кинологов (16) button is pressed ---
            case 16:
                messageToSend = getInfo(chatId, 28);
                break;
            // --- 29 - Причины, по которым могут отказать (17) button is pressed ---
            case 17:
                messageToSend = getInfo(chatId, 29);
                break;

            // -------- any other command / string -----
            default:
                messageToSend = buttons.setupSendMessage(chatId, CHOOSE_MENU_ITEM_STRING);
                break;
        }
        // --- /start is pressed
        if (messageText.equals(START_FIRST_COMMAND)) {
            messageToSend = startCommandReceived(chatId);
        }
        sendMessageToUser(messageToSend);
    }

    /**
     * Greetings to user on start.
     * We say about our shelter and offer to take a choice of menu item .
     *
     * @param chatId identificator of user
     */
    private SendMessage startCommandReceived(long chatId) {
        String greeting = "Здравствуйте! Это официальный телеграмм-бот приюта животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. " +
                "Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = buttons.setButtons(chatId, BUTTONS_NAMES, 0, 5);
        message.setText(greeting);
        return message;
    }

    /**
     * Information menu about shelter
     *
     * @param chatId identificator of chat
     */
    private SendMessage informAboutShelter(long chatId) {
        logger.info("Change keyboard to menu informAboutShelter");
        String chooseItem = "Здесь Вы можете получить информацию о нашем приюте.\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = buttons.setButtons(chatId, BUTTONS_NAMES, 5, 4);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Information menu for Potential Adopter
     *
     * @param chatId identificator of chat
     */
    private SendMessage informToPotentialAdopter(long chatId) {
        logger.info("Change keyboard to menu informToPotentialAdopter");
        String chooseItem = "Здесь мы собрали полезную информацию, которая поможет вам подготовиться ко встрече с новым членом семьи.\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = buttons.setButtons(chatId, BUTTONS_NAMES, 9, 10);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Pet's report menu
     *
     * @param chatId identificator of chat
     */
    private SendMessage waitForReport(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(2) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Contact getting menu
     *
     * @param update list of incoming updates, must be not Null
     */
    private void getContact(Update update) {
        logger.info("getContact processing...");
        SendMessage message = new SendMessage();
        String example = "Телефонный номер отсутствует";
            Contact contact = update.getMessage().getContact();
            String phoneNumber = contact.getPhoneNumber();
            example = "Ваш номер телефона: " + phoneNumber;
        message.setText(example);
        message.setChatId(update.getMessage().getChatId());
        message.setParseMode(ParseMode.MARKDOWN);
        sendMessageToUser(message);
    }

    /**
     * Menu of volunteer's calling
     *
     * @param chatId identificator of chat
     */
    private SendMessage callToVolunteer(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(4) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Method of getting information from repository {@link InformationMessageRepository#findById(Object)} of general information about shelter
     *
     * @param chatId         identificator of chat
     * @param menuItemNumber id number of menu item
     */
    private SendMessage getInfo(long chatId, long menuItemNumber) {
        String info = infoRepository.findById(menuItemNumber).orElseThrow().getText();
        return buttons.setupSendMessage(chatId, info);
    }


    // =========================================================================

    /**
     * Send message to user.
     *
     * @param message message to User
     */
    private void sendMessageToUser(SendMessage message) {
        try {
            if (!message.getText().isEmpty()) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            logger.error("Error occured in method sendMessageToUser: " + e.getMessage());
        }
    }

    // =========================================================================
    /**
     * Every day test DB in 20:00 to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "0 20 * * * *")
    public void runTestForReports () {
        // get all users with trial period
        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();

        // test, users have sent reports yesterday and two days ago
        for (User petsMaster : toTestWithTrialPeriod) {
            PetReport petReportYesterday = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(1L));
            PetReport petReportTwoDaysAgo = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(2L));
            if (petReportYesterday==null && petReportTwoDaysAgo==null) {
                /* позвать волонтера!!!
                 * если пользователь не присылал 2 дня никакой информации (текст или фото), отправлять запрос волонтеру на связь с усыновителем.
                 * Текст: "Усыновитель petsMaster не отправляет информацию уже 2 дня".*/
                break;
            }
            if (petReportYesterday==null) {
                /* если пользователь не присылал вчера информацию (текст и фото),
                напоминаем: "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет и информациюю о питомце".*/
                break;
            }
            if (petReportYesterday.getTextOfReport()==null && petReportYesterday.getPhotoLink()!=null) {
                /* если пользователь не присылал вчера текст,
                напоминаем: "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите сегодня информацию о питомце". */
                break;
            }
            if (petReportYesterday.getTextOfReport()!=null && petReportYesterday.getPhotoLink()==null) {
                /* если пользователь не присылал вчера фотоотчет,
                напоминаем: "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет о питомце". */
                break;
            }
        }
    }

    // =========================================================================
    /**
     * Every day test DB in 12:00 to check trial period has expired.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "00 12 * * * *")
    public void runTestTrialPeriodHasExpired () {
        // get all users with trial period
        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();

        // test, users have the last day of trial period
        for (User petsMaster : toTestWithTrialPeriod) {
            if (petsMaster.getFinishDate().isBefore(LocalDate.now()) && petsMaster.getAdopted()) {
                /* если день>N и усыновитель в статусе "на проверке", бот отправляет запрос волонтеру.
                Текст: "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок".
                 */
                break;
            }
            if (petsMaster.getFinishDate().equals(LocalDate.now()) && petsMaster.getAdopted()) {
                /* если день=N и хозяин еще на испытательном сроке, бот отправляет запрос волонтеру.
                Текст: "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок". */
                break;
            }
        }
    }
}
