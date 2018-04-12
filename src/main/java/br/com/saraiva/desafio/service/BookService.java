package br.com.saraiva.desafio.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.saraiva.desafio.model.Book;

public interface BookService {

	Book findBySku(String sku);
	List<Book> findAll(Integer maxResults, BigDecimal maxPrice);
	Book create(String sku);
	void delete(String sku);
}
