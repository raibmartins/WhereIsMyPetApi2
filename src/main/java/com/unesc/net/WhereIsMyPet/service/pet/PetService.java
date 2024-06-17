package com.unesc.net.WhereIsMyPet.service.pet;

import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import com.unesc.net.WhereIsMyPet.entity.pet.PetRegister;
import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import com.unesc.net.WhereIsMyPet.repository.pet.PetRepository;
import com.unesc.net.WhereIsMyPet.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@Transactional
public class PetService extends AbstractService {

    @Autowired
    private PetRepository petRepository;

    private Pet save(Pet pet) {
        return this.petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        return this.petRepository.findById(id).orElseThrow(() -> new ValidationException("Animal não encontrado."));
    }

    public List<Pet> getPets() {
        return this.petRepository.findAllByUsuario(this.getUsuario().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pet save(PetRegister petRegister) {
        if (this.findByTelefone(petRegister.telefone()) == null) {
            return this.save(
                    Pet.builder()
                            .id(petRegister.id())
                            .usuario(this.getUsuario())
                            .excluido(petRegister.excluido())
                            .imagem(petRegister.imagem())
                            .nome(petRegister.nome())
                            .telefone(petRegister.telefone())
                            .build()
            );
        }
        throw new ValidationException("Já existe um animal registrado com esse telefone");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pet excluir(Long id) {
        Pet pet = this.getPet(id);
        if (pet.getUsuario().getId() != this.getUsuario().getId()) {
            throw new ValidationException("Você não pode alterar este animal.");
        }
        pet.setExcluido(true);
        return this.save(pet);
    }

    public List<String> getNumeros() {
        return this.petRepository.findAllTelefones();
    }

    public Pet findByTelefone(String numero) {
        return this.petRepository.findByTelefone(numero);
    }

}
