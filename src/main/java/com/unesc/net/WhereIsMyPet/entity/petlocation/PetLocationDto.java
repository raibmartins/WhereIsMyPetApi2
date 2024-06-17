package com.unesc.net.WhereIsMyPet.entity.petlocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetLocationDto implements Serializable {

    private Long id;
    private String latitude;
    private String longitude;
    private String bateria;
    private LocalDateTime dataHoraPosicao;
    private PetLocationPetDto pet;


}
