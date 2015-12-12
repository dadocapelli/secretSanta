package br.com.capelli.secretsanta.sorteio;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Participante;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.service.SorteioHelper;

public class SorteioTest {

    Usuario usuario;

    @Test
    @Ignore
    public void sorteioTest() {

        System.out.println("### INICIO ");

        try {
            BasicConfigurator.configure();

            Sorteio sorteio = new Sorteio();
            sorteio.setNome("Teste");
            sorteio.setCodigo("TESTE_NATAL");

            usuario.getAmigos().stream().filter(a -> a.getAtivo()).forEach(
                    a -> sorteio.getParticipantes().add(Participante.from(a)));

            boolean sucesso = false;
            int tentativas = 0;
            while (!sucesso && tentativas < 5) {
                tentativas++;
                sucesso = SorteioHelper.sortear(sorteio);

                System.out.println("#### RESULTADO tentativa " + tentativas
                        + " - " + sucesso);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("### FIM ");
    }

    @Before
    public void beforTest() {
        System.out.println("### before");

        usuario = new Usuario();

        Amigo amigo = new Amigo();
        amigo.setNome("Bernardo");
        amigo.setId(1l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Dani");
        amigo.setId(2l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Belisa");
        amigo.setId(3l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Dudu");
        amigo.setId(4l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Graca");
        amigo.setId(5l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Capelli");
        amigo.setId(6l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Fran");
        amigo.setId(7l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Joao");
        amigo.setId(8l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Ze");
        amigo.setId(9l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

        amigo = new Amigo();
        amigo.setNome("Silvia");
        amigo.setId(10l);
        amigo.setEmail("dadocapelli@gmail.com");
        usuario.getAmigos().add(amigo);

    }

}
