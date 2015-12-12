package br.com.capelli.secretsanta.service;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.exception.ServiceException;
import br.com.capelli.secretsanta.modelo.Participante;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.persistence.ResultadoRepository;
import br.com.capelli.secretsanta.persistence.SorteioRepository;
import br.com.capelli.secretsanta.persistence.UsuarioRepository;

@Stateless
public class SorteioService implements Serializable {

    private static final long serialVersionUID = 8759643812928603313L;

    private static Logger logger = Logger.getLogger(SorteioService.class);

    @Inject
    private SorteioRepository sorteioRepository;
    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private ResultadoRepository resultadoRepository;
    @Inject
    private Event<Sorteio> eventEmail;

    public Resultado obtemResultado(String codigoPessoal)
            throws ServiceException {
        try {

            return resultadoRepository.findByCodigoPessoal(codigoPessoal);

        } catch (Exception e) {
            throw new ServiceException(
                    "Erro ao buscar resultado, tente depois.", e);
        }
    }

    public void configurarSorteio(Usuario usuario) throws ServiceException {

        logger.info("[BEGIN] configurarSorteio");

        try {

            usuario = usuarioRepository.findByLogin(usuario.getEmail());

            Sorteio sorteio = new Sorteio();
            sorteio.setUsuario(usuario);
            sorteio.setNome("Natal");
            sorteio.setCodigo("SECRETO");
            sorteio.setDescricao("Amigo Secreto");

            usuario.getAmigos().stream().filter(a -> a.getAtivo()).forEach(
                    a -> sorteio.getParticipantes().add(Participante.from(a)));

            sorteioRepository.persist(sorteio);

        } catch (DAOException e) {
            throw new ServiceException("Erro ao buscar usuario");
        } finally {
            logger.info("[END] configurarSorteio");
        }
    }

    public void sorteando(Usuario usuario, Sorteio sorteio)
            throws ServiceException {

        try {
            logger.info("[BEGIN] sorteando");

            usuario = usuarioRepository.findByLogin(usuario.getEmail());

            // temporario
            sorteio = usuario.getSorteios().get(0);

            sorteio.setResultados(new ArrayList<>());
            SorteioHelper.sortear(sorteio);

            sorteioRepository.update(sorteio);
            usuario.getSorteios().add(sorteio);
            usuarioRepository.update(usuario);

            // eventEmail.fire(sorteio);

        } catch (Exception e) {
            throw new ServiceException("Erro ao sortear");
        } finally {
            logger.info("[END] sorteando");
        }
    }

    public void enviarEmail(Usuario usuario, Sorteio sorteio)
            throws ServiceException {

        try {
            logger.info("[BEGIN] enviarEmail");

            usuario = usuarioRepository.findByLogin(usuario.getEmail());
            sorteioRepository.findByLogin(usuario.getEmail());

            // temporario
            sorteio = usuario.getSorteios().get(0);
            eventEmail.fire(sorteio);

        } catch (Exception e) {
            throw new ServiceException("Erro ao sortear");
        } finally {
            logger.info("[END] enviarEmail");
        }

    }

}
