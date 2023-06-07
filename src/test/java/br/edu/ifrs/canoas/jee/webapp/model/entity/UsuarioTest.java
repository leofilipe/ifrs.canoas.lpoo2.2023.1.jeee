package br.edu.ifrs.canoas.jee.webapp.model.entity;


import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.edu.ifrs.canoas.jee.webapp.model.entity.BaseEntity;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;

@RunWith(Arquillian.class)
public class UsuarioTest {

	@Deployment
    public static Archive<?> createTestArchive() {
	    return ShrinkWrap.create(WebArchive.class, "test.war")
	    		//Endereco.class é necessário em razão da dependência de Usuario
                .addClasses(Usuario.class, Endereco.class, BaseEntity.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                //.addPackages(true, "org.assertj")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
//                .addAsWebInfResource("test-ds.xml")
                ;
    }
	
	@Inject
	Usuario usuario;

	@Test
	public void testa_a_persistencia_do_usuario_em_branco () {		
		assertNotNull(usuario);
	}

	

}