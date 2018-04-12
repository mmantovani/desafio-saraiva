package br.com.saraiva.desafio.dto;

import org.modelmapper.PropertyMap;

import br.com.saraiva.desafio.model.Book;

public class SaraivaBookDTOMap extends PropertyMap <SaraivaBookDTO, Book> {

	@Override
	protected void configure() {
		// Mapeia: price/bestPrice/value -> price
		map().setPrice(source.getPrice().getBestPrice().getValue());
	}

}
