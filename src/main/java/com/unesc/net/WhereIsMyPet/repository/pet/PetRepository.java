package com.unesc.net.WhereIsMyPet.repository.pet;

import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {


    @Query("select p from Pet p where p.usuario.id = ?1 and p.excluido is false")
    List<Pet> findAllByUsuario(Long usuarioId);

    @Query("select p from Pet p where p.telefone = ?1 and p.excluido is false")
    Pet findByTelefone(String telefone);

    @Query("select p.telefone from Pet p where p.excluido is false")
    List<String> findAllTelefones();

}
