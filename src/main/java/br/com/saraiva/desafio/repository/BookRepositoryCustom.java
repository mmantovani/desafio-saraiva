package br.com.saraiva.desafio.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.saraiva.desafio.model.Book;

public interface BookRepositoryCustom {

	/**
	 * Consulta de livros com filtros de preço máximo e limite de resultados.
	 */
	List<Book> findAll(Integer maxResults, BigDecimal maxPrice);
}
