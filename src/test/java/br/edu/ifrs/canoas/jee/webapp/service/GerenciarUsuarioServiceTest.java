package br.edu.ifrs.canoas.jee.webapp.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
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

import br.edu.ifrs.canoas.jee.webapp.model.dao.UsuarioDAO;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.util.Mensagens;

@RunWith(Arquillian.class)
public class GerenciarUsuarioServiceTest {

	@Inject
	GerenciarUsuarioService gerenciarUsuarioService;
	
	@Inject
	GerenciarEnderecoService gerenciarEnderecoService;
	
	@Inject
    Logger log;

	@Deployment
    public static Archive<?> createTestArchive() {
	    return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(
                		GerenciarUsuarioService.class, 
                			UsuarioDAO.class, 
                		org.apache.commons.lang3.StringUtils.class, Mensagens.class)
                .addPackages(true, "br.edu.ifrs.canoas.jee.webapp")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/faces-config.xml"))
                .addAsResource(new File("src/main/resources/ValidationMessages.properties"), "ValidationMessages.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }
	
	
	@Test
	public void test_busca() {
		
		List<Usuario> lista = gerenciarUsuarioService.busca(null);
		
		assertNotNull(lista);
		
		for(Usuario u : lista) {
			assertNotNull(u);
			assertNotNull(u.getId());
			
			Endereco end = u.getEndereco();
			
			assertNotNull(end);
			assertNotNull(end.getId());
			assertEquals("Canoas", end.getCidade());
		}
	}
	@Test
	public void salva_usuario() {
		
		Endereco end = criaEndereco();
		assertTrue(gerenciarEnderecoService.salvaEndereco(end));
		
		Usuario usuario = criaUsuario(end);
		assertTrue(gerenciarUsuarioService.salvaUsario(usuario));
		assertNotNull(usuario.getId());
		
		String msg = usuario.getNome() + " foi persistido com o id " + usuario.getId() + " e senha " + usuario.getSenha();
		log.info(msg);
		
		usuario = criaUsuario(end);
		assertFalse(gerenciarUsuarioService.salvaUsario(usuario));
		log.info("não permite criar usuario com mesmo email");
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

	private Usuario criaUsuario(Endereco end) {
		Usuario usuario = new Usuario();
		usuario.setEmail("email@email.com");
		usuario.setNome("Rodrigo");
		usuario.setSenha("senhaa8");
		usuario.setSobrenome("Noll");
		usuario.setEndereco(end);
		return usuario;
	}

}
