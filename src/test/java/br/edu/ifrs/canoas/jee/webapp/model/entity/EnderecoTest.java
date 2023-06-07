package br.edu.ifrs.canoas.jee.webapp.model.entity;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EnderecoTest {

	@Deployment
    public static Archive<?> createTestArchive() {
	    return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Endereco.class, BaseEntity.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                //.addPackages(true, "org.assertj")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
//                .addAsWebInfResource("test-ds.xml")
                ;
    }
	
	@Inject
	Endereco endereco;
	
	@Test
	public void testa_a_persistencia_do_endereco_em_branco () {
		assertNotNull(endereco);
	}

}
