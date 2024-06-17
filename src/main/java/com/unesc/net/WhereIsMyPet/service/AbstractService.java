package com.unesc.net.WhereIsMyPet.service;

import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractService {

    protected Usuario getUsuario() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication();
    }

}
