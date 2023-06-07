package br.edu.ifrs.canoas.jee.webapp.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import br.edu.ifrs.canoas.jee.webapp.model.entity.BaseEntity;

@Stateless
public abstract class BaseDAO<T extends BaseEntity<ID>, ID> implements Serializable{
	
	private static final long serialVersionUID = 4489705146664275732L;

	@Inject
    protected EntityManager em;
	
	@SuppressWarnings("rawtypes")
	private Class entidade;

	public BaseDAO() {}
	
	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		if (entidade == null) {
			entidade = (Class) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entidade;
	}
	
	protected CriteriaBuilder builder() {
		return em.getCriteriaBuilder();
	}

	@SuppressWarnings("rawtypes")
	public void setEntityClass(Class entidade) {
		this.entidade = entidade;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public T busca(ID id) {
		return (T) em.find(getEntityClass(), id);
	}

	public void exclui(ID id) {
		@SuppressWarnings("unchecked")
		Object ref = em.getReference(getEntityClass(), id);
		em.remove(ref);
	}

	public T atualiza(T t) {
		return (T) em.merge(t);
	}

	public void insere(T t) {
		em.persist(t);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<T> lista() {
		return em.createQuery(
				"Select entity FROM " + getEntityClass().getSimpleName()
						+ " entity").getResultList();
	}
}
