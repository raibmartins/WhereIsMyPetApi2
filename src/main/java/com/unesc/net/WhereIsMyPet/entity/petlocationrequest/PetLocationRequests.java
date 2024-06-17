package com.unesc.net.WhereIsMyPet.entity.petlocationrequest;

import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "PETS_LOCATION_REQUEST")
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PetLocationRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_PETS")
    private Pet pet;

    private LocalDateTime dataHoraSolicitacao;

}
