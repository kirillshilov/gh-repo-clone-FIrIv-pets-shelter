package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;

import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.repository.Dog.DogReportRepository;
import com.telegrambot.jd501.repository.Dog.DogUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class DogReportService {
    private final DogReportRepository dogReportRepository;
    private final DogUserRepository dogUserRepository;

    public DogReportService(DogReportRepository dogReportRepository, DogUserRepository dogUserRepository) {
        this.dogReportRepository = dogReportRepository;
        this.dogUserRepository = dogUserRepository;
    }

    /**
     * get All DogReport from DataBase
     * Use method of DogReport repository {@link DogReportRepository#findAll()} ()} (Collection< DogReport >)}
     *
     * @return collection of DogReport
     */
    public Collection<DogReport> getAllDogReport() {
        return dogReportRepository.findAll();
    }

    /**
     * add new DogReport in DataBase
     *
     * @param dogReport Use  method DogReport repository {@link DogReportRepository#save(Object)} (DogReport)}
     * @return DogReport
     */
    public DogReport createDogReport(DogReport dogReport) {
        return dogReportRepository.save(dogReport);
    }

    /**
     * change DogReport in DataBase
     * Use  method DogReport repository {@link DogReportRepository#save(Object)} (DogReport)}
     *
     * @param dogReport
     * @return DogReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if DogReport with id not found
     */
    public DogReport updateDogReport(DogReport dogReport) {
        dogReportRepository.findById(dogReport.getId()).orElseThrow(() -> new PetReportNotFoundException("Report not found"));
        return dogReportRepository.save(dogReport);
    }

    /**
     * delete DogReport from DataBase by id
     * Use  method DogReport repository {@link DogReportRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted DogReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if PetReport with id not found
     */
    public DogReport deleteDogReport(Long id) {
        DogReport temp = dogReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("Report not found"));
        dogReportRepository.deleteById(id);
        return temp;
    }

    /**
     * get one DogReport from DataBase by User And DateOfReport
     * Use method of DogReport repository {@link DogReportRepository#findDogReportByDogUserAndDateOfReport(DogUser, LocalDate)} (Dog, LocalDate)} ()} (<DogReport>)}
     *
     * @param user
     * @param dateOfReport
     * @return DogReport With chatId And dateOfReportogUser
     */
    public DogReport getDogReportByUserAndDateOfReport(DogUser user, LocalDate dateOfReport) {
        return dogReportRepository.findDogReportByDogUserAndDateOfReport(user, dateOfReport);
    }

 /**
     * get All DogReport from DataBase by Dog
     * Use method of DogReport repository {@link DogReportRepository#findDogReportsByDogOrderByDateOfReport(Dog)} ()} (List<DogReport>)}
     *
     * @return collection of DogReport With Pet
     */
//    public Collection<DogReport> getAllDogReportByChatId(long chatId) {
//        return dogReportRepository.findDogReportsByDogOrderByDateOfReport(dog);
//    }

    /**
     * Find all DogReport By Chat Id
     * Use method DogReport repository {@link DogReportRepository # findAllByDogUser}
     *
     * @param chatId
     * @return Collection <DogReport>
     */
    public List <DogReport> getAllReportsByChatId(Long chatId) {
        DogUser tempDogUser = dogUserRepository.findDogUserByChatId(chatId);
        return dogReportRepository.findAllByDogUser(tempDogUser);
    }
}
