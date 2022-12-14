package com.telegrambot.jd501.service.dog_service;

import com.telegrambot.jd501.exceptions.PetNotFoundException;
import com.telegrambot.jd501.exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogUser;


import com.telegrambot.jd501.repository.dog.DogRepository;
import com.telegrambot.jd501.repository.dog.DogUserRepository;
import com.telegrambot.jd501.service.MailingListService;
import com.telegrambot.jd501.service.MessageTextService;
import com.telegrambot.jd501.service.TelegramBot;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class DogUserService {
    private final DogUserRepository dogUserRepository;
    private final DogRepository dogRepository;
    private final MailingListService mailingListService;

    private final MessageTextService messageTextService;


    public DogUserService(DogUserRepository dogUserRepository, DogRepository dogRepository, MailingListService mailingListService, MessageTextService messageTextService)  {
        this.dogUserRepository = dogUserRepository;
        this.dogRepository = dogRepository;
        this.mailingListService = mailingListService;
        this.messageTextService = messageTextService;
    }


    /**
     * get All DogUser from DataBase
     * Use method of DogUser repository {@link DogUserRepository#findAll()} ()} (Collection< DogUser >)}
     *
     * @return collection of DogUser
     */
    public Collection<DogUser> getAllUsers() {
        return dogUserRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param dogUser Use  method InformationMessage repository {@link DogUserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public DogUser createUser(DogUser dogUser) {
        dogUser.setStartDate(null);
        dogUser.setFinishDate(null);
        dogUser.setPet(null);
        dogUser.setAdopted(false);
        return dogUserRepository.save(dogUser);
    }

    /**
     * change DogUser in DataBase
     * Use  method User repository {@link DogUserRepository#save(Object)} (DogUser)}
     *
     * @param dogUser
     * @return DogUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if DogUser with id not found
     */
    public DogUser updateUser(DogUser dogUser) {
        dogUserRepository.findById(dogUser.getId()).orElseThrow(() -> new UserNotFoundException("DogUser not found"));
        return dogUserRepository.save(dogUser);
    }

    /**
     * delete DogUser from DataBase by chatId
     * Use  method DogUser repository {@link DogUserRepository#deleteById(Object)} } (Long id)}
     * Use  method DogUser repository {@link DogUserRepository#findDogUserByChatId(long)}
     *
     * @param chatId
     * @return Deleted DogUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if DogUser with id not found
     */
    public DogUser deleteUser(Long chatId) {
        DogUser temp = dogUserRepository.findDogUserByChatId(chatId);
        if (temp == null ){
            throw new UserNotFoundException("User NOt Found");
        }
        dogUserRepository.deleteById(temp.getId());
        return temp;
    }

    /**
     * find DogUser by ChatId and change amount of probation period, and sent message to user about change probation period
     * Use method DogUser repository {@link DogUserRepository#findDogUserByChatId(long)}
     * Use  method DogUser repository {@link DogUserRepository#save(Object)}
     *
     * @param userChatId   - DogUser id for find DogUser in repository,
     * @param days - number of days to increase the term of the transfer
     * @return DogUser
     */
    public DogUser probationPeriodExtension(Long userChatId, Integer days) {
        DogUser temp = dogUserRepository.findDogUserByChatId(userChatId);
        if(temp == null){
            throw new UserNotFoundException("User not Found");
        }
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        dogUserRepository.save(temp);
        sendMessageToUserWithChatId(temp.getChatId(),
                messageTextService.get("probation.period.extension", days));
        return temp;
    }

    /**
     * find user by ChatId and change  status of The Adopter, add adopted Pet, Date of adoption, and set test day by 30
     * and sent message to user about change him status.
     *
     * Use method dogRepository {@link DogRepository#findById(Object)}
     * Use method dogUser repository {@link DogUserRepository#findDogUserByChatId(long)}
     * Use  method dogUser repository {@link DogUserRepository#save(Object)}
     * Use method DogUserService {@link DogUserService#sendMessageToUserWithChatId(Long, String)}
     *
     * @param userChatId - user id for find user in repository,
     * @param dogId     - pet id for find user in repository,
     * @return Changed User
     */
    public DogUser changeStatusOfTheAdopter(Long userChatId, Long dogId) {
        DogUser userTemp = dogUserRepository.findDogUserByChatId(userChatId);
        if (userTemp == null){
            throw new UserNotFoundException("User not found");
        }
        Dog petTemp = dogRepository.findById(dogId).orElseThrow(() -> new PetNotFoundException("Dog not found"));
        userTemp.setAdopted(true);
        userTemp.setPet(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        sendMessageToUserWithChatId(userTemp.getChatId(), messageTextService.get("congrat.u.are.new.adopter"));
        return dogUserRepository.save(userTemp);
    }

    /**
     * find DogUser with status "adopted = true"
     * Use method DogUser repository {@link DogUserRepository#findDogUsersByIsAdoptedIsTrue()} (long)} ()}
     *
     * @return List<DogUser>
     */
    public List<DogUser> findUsersByAdoptedIsTrue() {
        return dogUserRepository.findDogUsersByIsAdoptedIsTrue();
    }

    /**
     * find DogUser if he exists by his ID
     * Use method DogUser repository {@link DogUserRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsUser(long dogUserChatId) {
        return dogUserRepository.existsByChatId(dogUserChatId);
    }

    /**
     * find user with by ID
     * Use method User repository {@link DogUserRepository#findById(Object)}
     *
     * @return User
     */
    public DogUser findUserByChatId(long userChatId) {
        return dogUserRepository.findDogUserByChatId(userChatId);
    }

    /**
     * Use TelegramBot to Sent custom message to Dog User with chat ID.
     * <p>
     * Use method DogUserService {@link DogUserService#findUserByChatId(long)}
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     *
     * @param chatId
     * @param message
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    public String sendMessageToUserWithChatId(Long chatId, String message) {
        DogUser temp = findUserByChatId(chatId);
        if (temp == null) {
            throw new UserNotFoundException("User with chatId " + chatId + " not found");
        }
        mailingListService.sendMessageToUserByChatId(chatId, message);
        return "Message: " + message + "sent to User with chat Id: " + chatId;
    }

    /**
     * finds a user by chat id. changes him status. and sends him a message stating that he has passed the trial period
     * <p>
     * Use method DogUserService {@link DogUserService#findUserByChatId(long)}
     * Use method DogUserRepository {@link DogUserRepository#save(Object)}
     *
     * @param chatId
     * @return DogUser
     * @throws UserNotFoundException when user with chatId not found
     */
    public DogUser changeStatusUserPassedProbationPeriod(Long chatId) {
        DogUser temp = changeStatusUserPassedProbationUtilityMethod(chatId);
        sendMessageToUserWithChatId(temp.getChatId(), messageTextService.get("passed.probation.period"));
        return dogUserRepository.save(temp);
    }

    /**
     * finds a user by chat id. changes him status. and sends him a message stating that he has not passed the trial period
     * <p>
     * Use method DogUserService {@link DogUserService#findUserByChatId(long)}
     * Use method DogUserRepository {@link DogUserRepository#save(Object)}
     *
     * @param chatId
     * @return DogUser
     * @throws UserNotFoundException when user with chatId not found
     */
    public DogUser changeStatusUserNotPassedProbationPeriod(Long chatId) {
        DogUser temp = changeStatusUserPassedProbationUtilityMethod(chatId);
        sendMessageToUserWithChatId(temp.getChatId(), messageTextService.get("not.passed.probation.period"));
        return dogUserRepository.save(temp);
    }

    /**
     * Utility method for changeStatusUserPassedProbationPeriod
     *
     * @param chatId
     * @return DogUser
     */
    private DogUser changeStatusUserPassedProbationUtilityMethod(Long chatId) {
        DogUser temp = findUserByChatId(chatId);
        if (temp == null) {
            throw new UserNotFoundException("User with chat Id not found");
        }
        temp.setFinishDate(null);
        temp.setAdopted(false);
        temp.setPet(null);
        temp.setStartDate(null);
        return temp;
    }
}
