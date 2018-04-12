package br.com.saraiva.desafio.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.saraiva.desafio.model.Book;
import br.com.saraiva.desafio.model.Book_;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

	private final EntityManager entityManager;

	public BookRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Book> findAll(Integer maxResults, BigDecimal maxPrice) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> root = cq.from(Book.class);

		// Condições (cláusula where)
		List<Predicate> predicates = new ArrayList<>();

		// Aplica preço máximo caso especificado
		if (maxPrice != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get(Book_.price), maxPrice));
		}

		// Aplica cláusula
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		// Retorna ordenado pelo sku
		cq.orderBy(cb.asc(root.get(Book_.sku)));

		TypedQuery<Book> query = entityManager.createQuery(cq);

		// Aplica limite de resultados caso especificado
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		// Finalmente, executa a query e retorna os dados
		return query.getResultList();
	}
}
