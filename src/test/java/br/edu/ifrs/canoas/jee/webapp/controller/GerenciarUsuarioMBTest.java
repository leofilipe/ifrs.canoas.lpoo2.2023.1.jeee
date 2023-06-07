package br.edu.ifrs.canoas.jee.webapp.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.util.Mensagens;

@RunWith(Arquillian.class)
public class GerenciarUsuarioMBTest {

	@Inject
	GerenciarUsuarioMB gerenciarUsuarioMB;
	
	@Inject
    Logger log;
	
	Endereco end;
	
	Usuario usuario;
	
	@Deployment
    public static Archive<?> createTestArchive() {
	    return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(
                		GerenciarUsuarioMB.class,
                		org.apache.commons.lang3.StringUtils.class, Mensagens.class)
                .addPackages(true, "br.edu.ifrs.canoas.jee.webapp")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/faces-config.xml"))
                .addAsResource(new File("src/main/resources/ValidationMessages.properties"), "ValidationMessages.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }
	
	@Test
	public void salva_usuario() {
		
		end = criaEndereco();
		usuario = criaUsuario(end);
		
		//atribui o obj usuario criado ao usuario do controlador
		gerenciarUsuarioMB.edita(usuario);//, end);
		
		assertNotNull(gerenciarUsuarioMB.getUsuario());
		assertNotNull(gerenciarUsuarioMB.getUsuario().getEndereco());
		
		assertEquals("Rodrigo", gerenciarUsuarioMB.getUsuario().getNome());
		assertEquals("Canoas", gerenciarUsuarioMB.getUsuario().getEndereco().getCidade());
		
		gerenciarUsuarioMB.salva();//ao final salva reinstancia usuario e endereco
		
		//como limpa reinstancia usuario e endereco em gerenciarUsuarioMB ao final
		//de salva, validamos a partir dos objetos locais que são a mesma posicao de memoria
		assertNotNull(usuario.getId());
		assertNotNull(end.getId());

		//validada a execução correta pelo log. Nao foi possivel vincular um assert
		//gerenciarUsuarioMB.edita(usuario, end);
		//gerenciarUsuarioMB.exclui();
		
	}
	
	private Usuario criaUsuario(Endereco end) {
		Usuario usuario = new Usuario();
		usuario.setEmail("email@email.com");
		usuario.setNome("Rodrigo");
		usuario.setSenha("senhaa8");
		usuario.setSobrenome("Noll");
		usuario.setEndereco(end);
		return usuario;
	}
	
	private Endereco criaEndereco() {
		
		Endereco end = new Endereco();
		end.setLogradouro("R. Dra. Maria Zélia Carneiro de Figueiredo");
		end.setNumero(870);
		end.setCidade("Canoas");
		end.setEstado("RS");
		end.setCep("92412-240");
		
		return end;
	}

}
