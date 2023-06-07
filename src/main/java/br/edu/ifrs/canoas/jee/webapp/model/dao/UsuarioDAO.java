package br.edu.ifrs.canoas.jee.webapp.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifrs.canoas.jee.webapp.model.entity.Usuario;
import br.edu.ifrs.canoas.jee.webapp.model.entity.Endereco;


@Stateless
public class UsuarioDAO extends BaseDAO<Usuario, Long> {

	private static final long serialVersionUID = -6896321074436211850L;

	@SuppressWarnings("unchecked")
	public List<Usuario> buscaPorEmail(String email) {
		return em.createQuery("SELECT u " + "FROM Usuario u " + "WHERE lower(u.email) = :email ")
				.setParameter("email", email).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> busca(Usuario usuario) {

		if (usuario == null)
			return new ArrayList<Usuario>();

		CriteriaQuery<Usuario> query = builder().createQuery(Usuario.class);
		Root<Usuario> from = query.from(Usuario.class);

		Predicate predicate = builder().and();

		if (!StringUtils.isBlank(usuario.getNome()))
			predicate = builder().and(predicate, builder().like(
					from.get("nome"), usuario.getNome().trim().toLowerCase()));
		if (!StringUtils.isBlank(usuario.getSobrenome()))
			predicate = builder().and(predicate,
					builder().like(from.get("sobrenome"), usuario.getSobrenome().trim().toLowerCase()));
		if (!StringUtils.isBlank(usuario.getEmail()))
			predicate = builder().and(predicate,
					builder().like(from.get("email"), usuario.getEmail().trim().toLowerCase()));
		if (!StringUtils.isBlank(usuario.getSenha()))
			predicate = builder().and(predicate,
					builder().like(from.get("senha"), usuario.getSenha().trim().toLowerCase()));

		return em.createQuery(query
				.select(from)
				.where(predicate)
				.orderBy(builder().asc(from.get("nome"))))
				.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscaPorCriterio(String criterio){
		return em.createQuery(
		         "SELECT u "
		         + "FROM Usuario u "
		         + "WHERE lower(u.email) = :email "
		         + " or lower (u.nome) = :nome "
		         + " or lower (u.sobrenome) =  :sobrenome ")
		         .setParameter("email", criterio.trim().toLowerCase())
		         .setParameter("nome", criterio.trim().toLowerCase())
		         .setParameter("sobrenome", criterio.trim().toLowerCase())
		         .getResultList();
	}
}