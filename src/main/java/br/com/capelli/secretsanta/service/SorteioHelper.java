package br.com.capelli.secretsanta.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.exception.RestricaoSorteioException;
import br.com.capelli.secretsanta.exception.ServiceException;
import br.com.capelli.secretsanta.modelo.Participante;
import br.com.capelli.secretsanta.modelo.RestricaoSorteio;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.TipoRestricao;

public class SorteioHelper {

    private static Logger logger = Logger.getLogger("SorteioHelper");

    public static void sortear(Sorteio sorteio) throws ServiceException {
        try {

            logger.info("[BEGIN] sortear");
            Resultado resultado;

            List<Participante> amigosOriginal = new ArrayList<Participante>();
            amigosOriginal.addAll(sorteio.getParticipantes());

            List<Participante> amigosParaSortear = new ArrayList<Participante>();
            amigosParaSortear.addAll(sorteio.getParticipantes());

            Collections.shuffle(amigosOriginal);

            int sizeOriginal = amigosOriginal.size();
            int tentativas = 0;

            Participante amigo = amigosOriginal.get(0);
            Participante amigoInicial = amigosOriginal.get(0);
            amigosParaSortear.remove(amigo);

            List<RestricaoSorteio> restricoesOnline = new ArrayList<>();

            while (!amigosOriginal.isEmpty()
                    && tentativas <= (sizeOriginal * 3)) {
                tentativas++;
                logger.info("Tamanho da lista: " + amigosOriginal.size());

                logger.debug("Sortear amigo de: " + amigo.getNome());

                Participante amigoSorteado = null;
                if (amigosParaSortear.size() == 0) {
                    amigoSorteado = amigoInicial;
                } else {
                    amigoSorteado = amigoSorteado(amigosParaSortear);
                }
                logger.debug("Nome sorteado: " + amigoSorteado.getNome());

                try {
                    validandoResultado(sorteio, restricoesOnline, amigo,
                            amigoSorteado);

                    logger.info("SORTEADO");
                    resultado = new Resultado();
                    resultado.setEu(amigo);
                    resultado.setMeuAmigoSecreto(amigoSorteado);
                    resultado.setSorteio(sorteio);

                    sorteio.getResultados().add(resultado);

                    amigosParaSortear.remove(amigoSorteado);
                    amigosOriginal.remove(amigo);

                    // O amigo Sorteado é o proximo a escolher
                    amigo = amigoSorteado;

                    restricoesOnline
                            .add(geraRestricaoOnline(amigo, amigoSorteado));
                    logger.info("### ");

                } catch (RestricaoSorteioException e) {
                    logger.error("Restricao encontrada: " + e.getMessage());
                }
            }

            if (!amigosOriginal.isEmpty()) {
                throw new ServiceException("Nao foram todos sorteados");
            }

        } catch (Exception e) {
            logger.error("Erro ao processar sorteio", e);
            throw new ServiceException("Erro", e);
        } finally {
            logger.info("[END] sortear");
        }
    }

    private static Participante amigoSorteado(
            List<Participante> amigosParaSortear) {
        double r = Math.random();
        int s1 = ((int) (r * 1000) % amigosParaSortear.size());
        return amigosParaSortear.get(s1);
    }

    private static RestricaoSorteio geraRestricaoOnline(Participante amigo,
            Participante amigoSorteado) {
        RestricaoSorteio rs = new RestricaoSorteio();
        rs.setParticipanteFrom(amigoSorteado);
        rs.setParticipanteTo(amigo);
        rs.setTipoRestricao(TipoRestricao.NAO_PODE_TIRAR);
        return rs;
    }

    private static boolean validandoResultado(Sorteio sorteio,
            List<RestricaoSorteio> restricoesOnline, Participante amigo,
            Participante amigoSorteado) throws RestricaoSorteioException {

        if (amigo.equals(amigoSorteado)) {
            throw new RestricaoSorteioException("ele mesmo");
        }

        validandoRestricao(restricoesOnline, amigo, amigoSorteado);
        validandoRestricao(sorteio.getRestricoes(), amigo, amigoSorteado);

        return true;
    }

    private static void validandoRestricao(List<RestricaoSorteio> restricoes,
            Participante amigo, Participante amigoSorteado)
                    throws RestricaoSorteioException {

        boolean blockPodeTirarSomente = Boolean.FALSE;

        for (RestricaoSorteio r : restricoes) {

            if (TipoRestricao.NAO_PODE_TIRAR.equals(r.getTipoRestricao())) {
                if (amigo.equals(r.getParticipanteFrom())
                        && amigoSorteado.equals(r.getParticipanteTo())) {
                    throw new RestricaoSorteioException("nao pode tirar");
                }
            } else if (TipoRestricao.PODE_SOMENTE_TIRAR
                    .equals(r.getTipoRestricao())) {
                if (amigo.equals(r.getParticipanteFrom())
                        && !amigoSorteado.equals(r.getParticipanteTo())) {
                    // continua pesquisando por este enum
                    blockPodeTirarSomente = Boolean.TRUE;
                    continue;
                } else if (amigo.equals(r.getParticipanteFrom())
                        && amigoSorteado.equals(r.getParticipanteTo())) {
                    // valido
                    blockPodeTirarSomente = Boolean.FALSE;
                    return;
                }
            } else {
                if ((amigo.equals(r.getParticipanteFrom())
                        || amigo.equals(r.getParticipanteTo()))
                        && (amigoSorteado.equals(r.getParticipanteFrom())
                                || amigoSorteado
                                        .equals(r.getParticipanteTo()))) {
                    throw new RestricaoSorteioException(
                            "ambos nao podem se tirar");
                }
            }
        }

        if (blockPodeTirarSomente) {
            throw new RestricaoSorteioException("pode tirar somente");
        }

    }

}
