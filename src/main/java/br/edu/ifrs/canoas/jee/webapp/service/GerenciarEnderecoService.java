package br.edu.ifrs.canoas.jee.webapp.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import br.edu.ifrs.canoas.jee.webapp.model.dao.EnderecoDAO;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;
import br.edu.ifrs.canoas.jee.webapp.util.Mensagens;

@Stateless
public class GerenciarEnderecoService {

	@Inject
	EnderecoDAO enderecoDAO;
	
	@Inject
	private Logger log;
	
	public boolean salvaEndereco(Endereco endereco) {
		
		log.info("Salvando " + endereco.getLogradouro());
		
	
		if (endereco.getId() != null) {
			enderecoDAO.atualiza(endereco);
			//Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.atualizado.sucesso",endereco.getLogradouro());
			return true;
		}
		
		try {
			enderecoDAO.insere(endereco);
			
			//O usuário e o endereço são cadastrados juntos. Não há necessidade de msg de sucesso aqui
			//dira apenas poluir a tela do usuario
			//Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.cadastro.sucesso", endereco.getId());
			log.info("Salvo " + endereco.getLogradouro() + " com id " + endereco.getId());
			return true;
		}catch(Exception e) {
			e.printStackTrace(System.out);
			log.info("Problema ao salvar endereco " + endereco.getLogradouro());
			Mensagens.define(FacesMessage.SEVERITY_ERROR, "Usuario.email.erro.cadastrado",endereco.getLogradouro());
			return false;
		}
		
	}
	
	public void exclui(Endereco endereco) {
		enderecoDAO.exclui(endereco.getId());
		//desnecessario, ira apenas poluir a tela do usuario
		//Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.excluido.sucesso", endereco.getLogradouro());
		log.info("Excluido " + endereco.getLogradouro() + " com id " + endereco.getId());
	}
}
