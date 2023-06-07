package br.edu.ifrs.canoas.jee.webapp.service;

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

import br.edu.ifrs.canoas.jee.webapp.model.dao.EnderecoDAO;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;
import br.edu.ifrs.canoas.jee.webapp.util.Mensagens;

@RunWith(Arquillian.class)
public class GerenciarEnderecoServiceTest {

	@Inject
	GerenciarEnderecoService gerenciarEndereco;
	@Inject
    Logger log;

	@Deployment
    public static Archive<?> createTestArchive() {
	    return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(
                		GerenciarEnderecoService.class, 
                			EnderecoDAO.class, 
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
		Endereco end = criaEndereco();
		
		assertNotNull(end);
		
		//assertNotNull(gerenciarEndereco);
		
		
		assertTrue(gerenciarEndereco.salvaEndereco(end));
		assertNotNull(end.getId());
		log.info(end.getId() + " foi persistido ");
		
	}

	
	private Endereco criaEndereco() {
		
		Endereco end = new Endereco();
		
		return end;
		
	}

}
