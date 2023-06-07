package br.edu.ifrs.canoas.jee.webapp.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifrs.canoas.jee.webapp.model.dao.UsuarioDAO;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.util.Mensagens;


@Stateless
public class GerenciarUsuarioService {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private Logger log;

	public boolean salvaUsario(Usuario usuario) {

		log.info("Salvando " + usuario.getNome());
		
		if (usuario.getId() != null) {
			usuarioDAO.atualiza(usuario);
			Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.atualizado.sucesso",usuario.getEmail());
			return true;
		}
		
		int qtdEmailCadastrado = this.validaEmail(usuario);
		
		if (qtdEmailCadastrado == 0) {
			if (validaSenha(usuario)){
				
				usuarioDAO.insere(usuario);
				Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.cadastro.sucesso",usuario.getEmail());
				log.info("Salvo " + usuario.getNome() + " com id " + usuario.getId());
				return true;
			}
		} 
		
		log.info("Problema com email duplicado do usuário " + usuario.getNome() + " - email " + usuario.getEmail());
		Mensagens.define(FacesMessage.SEVERITY_ERROR, "Usuario.email.erro.cadastrado",usuario.getEmail());
		return false;
	}

	
	/**
	 * Valida a senha do usuário. Testa o algoritmo de criptografia
	 * @param usuario
	 * @return
	 */
	private boolean validaSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		
		System.out.println("Senha atual " + senha);
		//String senha = this.getSenha(usuario.getSenha());
		
		//se a senha for menor que 6 ou maior que 8 caracteres
		if (senha.length() < 6 || senha.length() > 8){
			return false;
		}
		
		//se a senha nao contiver apenas letras e numeros
		String regex = "^[a-zA-Z0-9]+$";
		 
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(senha);
		
		if(matcher.matches()) {
			
			//gera senha criptografada
			String senhaC = this.getSenha(senha);
			
			//se a criptografia funcionou
			if (senhaC.length() > 0){
				//atualizar senha criptografada
				usuario.setSenha(senhaC);	
				return true;
			}
		}
		
		
		return false;
	}


	/**
	 * retorna a quantidade de e-mails cadastrados no banco iguais ao informado.
	 * @param usuario
	 * @return int
	 */
	private int validaEmail(Usuario usuario) {
		if (usuario == null || StringUtils.isBlank(usuario.getEmail()))
			return -1;

		return usuarioDAO
				.buscaPorEmail(usuario.getEmail().trim().toLowerCase())
				.size();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> busca(String criterio) {
		if (criterio != null && criterio.length() > 0) 
			return usuarioDAO.buscaPorCriterio(criterio);
		else {
			return usuarioDAO.lista();
		}
	}
	
	private String getSenha(String str) {
		String result = "";
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(), 0, str.length());
			result = new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.senha.erro.criptografia", e.getMessage());
		}
		return result;
	}


	public void exclui(Usuario usuario) {
		usuarioDAO.exclui(usuario.getId());
		Mensagens.define(FacesMessage.SEVERITY_INFO, "Usuario.excluido.sucesso",usuario.getNome(), usuario.getEndereco().getLogradouro());
		log.info("Excluido " + usuario.getNome() + " com id " + usuario.getId());
	}


	public Usuario get(Long id) {
		return usuarioDAO.busca(id);
	}

}