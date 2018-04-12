package br.com.saraiva.desafio.service;

import br.com.saraiva.desafio.model.Book;

/**
 * Interface de consulta à API da Saraiva.
 *
 * @author marco
 *
 */
public interface SaraivaService {

	Book findBySku(String sku);
}
