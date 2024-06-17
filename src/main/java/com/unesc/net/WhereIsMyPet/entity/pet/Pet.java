package com.unesc.net.WhereIsMyPet.entity.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Table(name = "PETS")
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_USUARIOS")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @NotNull(message = "Deve ser enviado o nome do seu animal.")
    private String nome;

    @NotNull(message = "Deve ser enviado o telefone do seu animal.")
    private String telefone;

    private Boolean excluido = Boolean.FALSE;

//    @NotNull(message = "Deve ser enviado uma imagem para o seu animal.")
    private byte[] imagem;

}

