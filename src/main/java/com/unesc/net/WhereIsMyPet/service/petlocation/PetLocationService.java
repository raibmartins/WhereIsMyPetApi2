package com.unesc.net.WhereIsMyPet.service.petlocation;

import com.google.firebase.messaging.*;
import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import com.unesc.net.WhereIsMyPet.entity.petlocation.PetLocation;
import com.unesc.net.WhereIsMyPet.entity.petlocation.SavePetLocationDTO;
import com.unesc.net.WhereIsMyPet.entity.petlocationrequest.PetLocationRequests;
import com.unesc.net.WhereIsMyPet.repository.petlocation.PetLocationRepository;
import com.unesc.net.WhereIsMyPet.repository.petlocationrequest.PetLocationRequestRepository;
import com.unesc.net.WhereIsMyPet.entity.petlocation.PetLocationDto;
import com.unesc.net.WhereIsMyPet.service.pet.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetLocationService {

    private final ModelMapper modelMapper;
    private final PetService petService;
    private final PetLocationRepository petLocationRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final PetLocationRequestRepository petLocationRequestRepository;

    @Autowired
    public PetLocationService(ModelMapper modelMapper, PetService petService, PetLocationRepository petLocationRepository, FirebaseMessaging firebaseMessaging, PetLocationRequestRepository petLocationRequestRepository) {
        this.modelMapper = modelMapper;
        this.petService = petService;
        this.petLocationRepository = petLocationRepository;
        this.firebaseMessaging = firebaseMessaging;
        this.petLocationRequestRepository = petLocationRequestRepository;
    }

    @Value("${google.firebase.token}")
    private String firebaseToken;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLocation(SavePetLocationDTO savePetLocationDTO) {
        Pet pet = this.petService.findByTelefone(savePetLocationDTO.numero().replace("+55", ""));
        if (pet != null) {

            PetLocation petLocation = PetLocation.builder()
                    .pet(pet)
                    .dataHoraPosicao(LocalDateTime.now())
                    .latitude(savePetLocationDTO.latitude())
                    .longitude(savePetLocationDTO.longitude())
                    .bateria(savePetLocationDTO.bateria())
                    .build();

            this.petLocationRepository.save(petLocation);
        }
    }

    public PetLocationDto[] getLocations() {
        List<PetLocation> locations = new ArrayList<>();
        for (Pet pet : this.petService.getPets()) {
            PetLocation petLocation = this.petLocationRepository.findPetLocationByPetId(pet.getId());
            if (petLocation != null) {
                locations.add(petLocation);
            }
        }
        if (!locations.isEmpty()) {
            return this.modelMapper.map(locations, PetLocationDto[].class);
        }
        return null;
    }

    public void sendSmsGetLocation(Long petId) {
        Pet pet = this.petService.getPet(petId);
        if (pet != null) {
            PetLocationRequests hasPetLocationRequest = this.petLocationRequestRepository.findHasPetLocationRequest(pet.getId());
            if (hasPetLocationRequest == null) {
                try {
                    this.firebaseMessaging.send(this.buildMessage(pet));
                    this.petLocationRequestRepository.save(
                            PetLocationRequests.builder()
                                    .pet(pet)
                                    .dataHoraSolicitacao(LocalDateTime.now())
                                    .build()
                    );
                } catch (Exception e) {
                    throw new ValidationException("Erro ao solicitar a localização do animal");
                }
            } else {
                LocalDateTime localDateTime = hasPetLocationRequest.getDataHoraSolicitacao().plusMinutes(5);
                throw new ValidationException(String.format("Você deve esperar até %s para realizar isto novamente.", localDateTime.toLocalTime().getHour() + ":" + localDateTime.toLocalTime().getMinute()));
            }
        }
    }

    private Message buildMessage(Pet pet) {
        return Message.builder()
                .setToken(firebaseToken)
                .setNotification(
                    Notification.builder()
                            .setBody(pet.getTelefone())
                            .build())
                .build();
    }

}
