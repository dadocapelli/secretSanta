package br.com.capelli.secretsanta.service;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.persistence.UsuarioRepository;

@Stateless
public class UsuarioService implements Serializable {

    private static final long serialVersionUID = -4185082298392598240L;

    @Inject
    private UsuarioRepository usuarioRepository;

    public Usuario findById(String email) {
        return usuarioRepository.find(email).get();
    }

}
