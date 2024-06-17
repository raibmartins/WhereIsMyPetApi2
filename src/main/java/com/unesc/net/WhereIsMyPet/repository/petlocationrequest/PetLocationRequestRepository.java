package com.unesc.net.WhereIsMyPet.repository.petlocationrequest;

import com.unesc.net.WhereIsMyPet.entity.petlocation.PetLocation;
import com.unesc.net.WhereIsMyPet.entity.petlocationrequest.PetLocationRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetLocationRequestRepository extends JpaRepository<PetLocationRequests, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM PETS_LOCATION_REQUEST WHERE data_hora_solicitacao >= CURRENT_TIMESTAMP - INTERVAL '5 minutes' and i_pets = ?1 limit 1")
    PetLocationRequests findHasPetLocationRequest(Long petId);

}
