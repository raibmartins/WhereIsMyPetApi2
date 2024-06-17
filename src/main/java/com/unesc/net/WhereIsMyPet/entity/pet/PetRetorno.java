package com.unesc.net.WhereIsMyPet.entity.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetRetorno {

    public Long id;
    public String nome;
    public String telefone;
    public Boolean excluido;
    public byte[] imagem;

}
