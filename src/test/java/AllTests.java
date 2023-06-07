

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.edu.ifrs.canoas.jee.webapp.model.dao.EnderecoDAOTest;
import br.edu.ifrs.canoas.jee.webapp.model.dao.UsuarioDAOTest;
import br.edu.ifrs.canoas.jee.webapp.model.entity.EnderecoTest;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.model.entity.UsuarioTest;
import br.edu.ifrs.canoas.jee.webapp.service.GerenciarEnderecoServiceTest;
import br.edu.ifrs.canoas.jee.webapp.service.GerenciarUsuarioServiceTest;

@RunWith(Suite.class)
@SuiteClasses({	UsuarioTest.class, 
				EnderecoTest.class, 
				UsuarioDAOTest.class, 
				EnderecoDAOTest.class,
				GerenciarUsuarioServiceTest.class,
				GerenciarEnderecoServiceTest.class})
public class AllTests {

}
