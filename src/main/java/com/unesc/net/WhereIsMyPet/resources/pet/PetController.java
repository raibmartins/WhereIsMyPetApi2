package com.unesc.net.WhereIsMyPet.resources.pet;


import com.unesc.net.WhereIsMyPet.entity.pet.Pet;
import com.unesc.net.WhereIsMyPet.entity.pet.PetRegister;
import com.unesc.net.WhereIsMyPet.entity.pet.PetRetorno;
import com.unesc.net.WhereIsMyPet.service.pet.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllPets() {
        PetRetorno[] map = this.modelMapper.map(this.petService.getPets(), PetRetorno[].class);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PetRegister petRegister) {
        Pet pet = this.petService.save(petRegister);
        PetRetorno retorno = this.modelMapper.map(pet, PetRetorno.class);
        return ResponseEntity.ok(retorno);
    }

    @PutMapping
    @RequestMapping(value = "excluir/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.petService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @RequestMapping(value = "getNumeros")
    public ResponseEntity<?> getNumeros() {
        return ResponseEntity.ok(this.petService.getNumeros());
    }
}
