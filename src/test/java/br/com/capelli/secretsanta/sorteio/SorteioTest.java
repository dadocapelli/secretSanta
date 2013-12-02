package br.com.capelli.secretsanta.sorteio;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import br.com.capelli.secretsanta.manager.impl.SorteioHelper;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;

public class SorteioTest {

	List<Amigo> amigos = new ArrayList<Amigo>();
	Usuario usuario;

	@Test
	// @Ignore
	public void sorteioTest() {

		System.out.println("### INICIO ");

		try {
			BasicConfigurator.configure();

			Sorteio sorteio = new Sorteio();
			sorteio.setNome("Teste");
			sorteio.setCodigo("TESTE_NATAL");

			boolean sucesso = false;
			int tentativas = 0;
			while (!sucesso && tentativas < 5) {
				tentativas++;
				sucesso = SorteioHelper.sortear(sorteio, usuario);

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
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Dani");
		amigo.setId(2l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Belisa");
		amigo.setId(3l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Dudu");
		amigo.setId(4l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Graca");
		amigo.setId(5l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Capelli");
		amigo.setId(6l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Fran");
		amigo.setId(7l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Joao");
		amigo.setId(8l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Ze");
		amigo.setId(9l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Silvia");
		amigo.setId(10l);
		amigos.add(amigo);

		usuario.getAmigos().addAll(amigos);
	}

}
