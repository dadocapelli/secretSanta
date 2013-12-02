package br.com.capelli.secretsanta.manager;

import java.util.List;

import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;

public interface AmigoManager extends Manager<Amigo, Long> {

	List<Amigo> findAmigosByUsuario(Usuario usuario) throws ManagementException;

	void saveOrUpdade(Amigo amigo) throws ManagementException;
}
