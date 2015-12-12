package br.com.capelli.secretsanta.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.capelli.secretsanta.exception.ServiceException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.persistence.AmigoRepository;

@Stateless
public class AmigoService implements Serializable {

    private static final long serialVersionUID = 7932264620404610442L;

    @Inject
    private AmigoRepository amigoRepository;

    public List<Amigo> findAmigosByUsuario(Usuario usuario)
            throws ServiceException {
        try {

            return amigoRepository.findAmigoByUsuario(usuario);

        } catch (Exception e) {
            throw new ServiceException(
                    "Erro ao buscar amigos, tente depois.", e);
        }
    }

    public void saveOrUpdade(Amigo amigo) throws ServiceException {
        try {
            if (amigo.getId() == null) {
                amigoRepository.persist(amigo);
            } else {
                amigoRepository.update(amigo);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
