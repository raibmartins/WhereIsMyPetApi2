package com.unesc.net.WhereIsMyPet.entity.petlocation;

import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "PETS_LOCATION")
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PetLocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_PETS")
    private Pet pet;

    private LocalDateTime dataHoraPosicao;

    private String latitude;

    private String longitude;
    private String bateria;


}
