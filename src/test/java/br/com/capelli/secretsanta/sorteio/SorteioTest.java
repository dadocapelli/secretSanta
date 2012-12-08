package br.com.capelli.secretsanta.sorteio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Sorteio;

public class SorteioTest {

	List<Amigo> amigos = new ArrayList<Amigo>();

	@Test
	@Ignore
	public void sorteioTest() {
		
		System.out.println("### INICIO ");
		
		try{

		Sorteio sorteio = new Sorteio();
		sorteio.setNome("Teste");
		sorteio.setCodigo("TESTE_NATAL");

		boolean sucesso = false;
		int tentativas = 0;
		while(!sucesso && tentativas<5){
			tentativas++;
			sucesso = sorter();
			System.out.println("#### RESULTADO tentativa " + tentativas + " - " + sucesso);
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("### FIM ");
	}
		
		private boolean sorter(){
			try{
				
				List<Amigo> amigosOriginal = new ArrayList<Amigo>();
				amigosOriginal.addAll(amigos);
				
				List<Amigo> amigosParaSortear = new ArrayList<Amigo>();
				amigosParaSortear.addAll(amigos);

		Collections.shuffle(amigosOriginal);

		int sizeOriginal = amigosOriginal.size();
		int tentativas = 0;
		
		while (!amigosOriginal.isEmpty() && tentativas <= (sizeOriginal*3)) {
			tentativas++;
			System.out.println("while tentativas: " + tentativas + " amigosOriginal.size: " + amigosOriginal.size());
			
			for (int i = 0; i <= amigosOriginal.size(); i++) {
				System.out.println("while tentativas: " + tentativas + " amigosOriginal.size: " + amigosOriginal.size() + " i= " + i);
				
				Amigo amigo = amigosOriginal.get(i);
				System.out.println("tentativa " + tentativas);
			
				System.out.println("Sortear amigo de: " + amigo.getNome());
				
				double r = Math.random();
				int s1 = ((int) (r * 1000) % amigosParaSortear.size());
				
				Amigo amigoSorteado = amigosParaSortear.get(s1);
				System.out.println("Nome sorteado: " + amigoSorteado.getNome());
	//			System.out.println("hashCode: " + amigo.hashCode() + " === " + amigoSorteado.hashCode());
	//			System.out.println("equals: " + amigo.equals(amigoSorteado));
	//			System.out.println("isEmpty: " + amigo.getAmigosProibidos().isEmpty());
	//			System.out.println("Contains: " + amigo.getAmigosProibidos().contains(amigoSorteado));
	//			System.out.println("Contains contrario : " + amigoSorteado.getAmigosProibidos().contains(amigo));
				
					if (!amigo.equals(amigoSorteado)
							&& !amigo.getAmigosProibidos().contains(amigoSorteado)
							&& !amigoSorteado.getAmigosProibidos().contains(amigo)) {
					
						System.out.println("SORTEADO");
						// amigo.setAmigoMeu(amigoSorteado);
						amigosParaSortear.remove(amigoSorteado);
						amigosOriginal.remove(i);
					}
	
				// sair do for e recomeçar
				if(amigosOriginal.size() == i+1){
					i++;
				}
					
					
				System.out.println("### ");
				
			}
		}
		
		System.out.println("### FIM ");
		
		return amigosOriginal.isEmpty();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}

	@Before
	public void beforTest() {

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
		amigo.getAmigosProibidos().add(amigos.get(0));
//		amigo.getAmigosProibidos().add(amigos.get(2));
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Capelli");
		amigo.setId(6l);
		amigo.getAmigosProibidos().add(amigos.get(0));
//		amigo.getAmigosProibidos().add(amigos.get(2));
		amigo.getAmigosProibidos().add(amigos.get(4));
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Fran");
		amigo.setId(7l);
//		amigo.getAmigosProibidos().add(amigos.get(0));
//		amigo.getAmigosProibidos().add(amigos.get(2));
//		amigo.getAmigosProibidos().add(amigos.get(4));
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Joao");
		amigo.setId(8l);
//		amigo.getAmigosProibidos().add(amigos.get(0));
//		amigo.getAmigosProibidos().add(amigos.get(2));
//		amigo.getAmigosProibidos().add(amigos.get(4));
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Ze");
		amigo.setId(9l);
		amigos.add(amigo);

		amigo = new Amigo();
		amigo.setNome("Silvia");
		amigo.setId(10l);
		amigo.getAmigosProibidos().add(amigos.get(2));
		amigo.getAmigosProibidos().add(amigos.get(3));
		amigo.getAmigosProibidos().add(amigos.get(4));
//		amigo.getAmigosProibidos().add(amigos.get(5));
//		amigo.getAmigosProibidos().add(amigos.get(6));
//		amigo.getAmigosProibidos().add(amigos.get(7));
		amigos.add(amigo);
		
	}
	
}
