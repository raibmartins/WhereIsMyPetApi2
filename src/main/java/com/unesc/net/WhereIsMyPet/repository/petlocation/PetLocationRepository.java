package com.unesc.net.WhereIsMyPet.repository.petlocation;

import com.unesc.net.WhereIsMyPet.entity.petlocation.PetLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetLocationRepository extends JpaRepository<PetLocation, Long> {


    @Query(value = "select * from pets_location pl where pl.i_pets = :petId order by pl.data_hora_posicao desc limit 1 ", nativeQuery = true)
    PetLocation findPetLocationByPetId(@Param("petId") Long petId);
}
