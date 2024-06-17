package com.unesc.net.WhereIsMyPet.entity.pet;

public record PetRegister(Long id, String nome, String telefone, Boolean excluido, byte[] imagem) {
}
