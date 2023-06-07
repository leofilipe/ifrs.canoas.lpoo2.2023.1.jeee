package br.edu.ifrs.canoas.jee.webapp.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

//import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.service.GerenciarEnderecoService;
import br.edu.ifrs.canoas.jee.webapp.service.GerenciarUsuarioService;
import lombok.Data;

@Named
@RequestScoped
@Data
public class GerenciarUsuarioMB {

	@Inject
	private GerenciarUsuarioService gerenciarUsuarioService;
	
	@Inject
	private GerenciarEnderecoService gerenciarEnderecoService;
	
	@Inject
	private Usuario usuario;
	
	private List<Usuario> usuarios;

	@PostConstruct
	public void init() {
		
		usuarios = gerenciarUsuarioService.busca(null);
		
	}

	public String salva() {
		
		//salve 1o o endereco para gerar uma chave primaria
		gerenciarEnderecoService.salvaEndereco(usuario.getEndereco());
		
		//usuario se associa a endereco a partir da chave primaria dele
		gerenciarUsuarioService.salvaUsario(usuario);
		
		this.init();
		
		return limpa();
	}

	public void edita(Usuario u) {

		this.usuario = u;		
	}

	public void exclui() {
		
		gerenciarUsuarioService.exclui(usuario);
		gerenciarEnderecoService.exclui(usuario.getEndereco());
		
		this.init();
	}

	public String limpa() {
		usuario = new Usuario();
		
		return "/public/usuario.jsf?facesRedirect=true";
	}

}
