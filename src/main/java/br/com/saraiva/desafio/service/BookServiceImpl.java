package br.com.saraiva.desafio.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.saraiva.desafio.model.Book;
import br.com.saraiva.desafio.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository repository;
	private final SaraivaService saraivaService;

	public BookServiceImpl(BookRepository repository, SaraivaService saraivaService) {
		this.repository = repository;
		this.saraivaService = saraivaService;
	}

	@Override
	public Book findBySku(String sku) {
		Book entity = repository.findBySku(sku);
		if (entity == null) {
			throw new EntityNotFoundException(
					String.format("Livro identificado pelo SKU '%s' nao encontrado.", sku));
		}
		return entity;
	}

	@Override
	public List<Book> findAll(Integer maxResults, BigDecimal maxPrice) {
		return repository.findAll(maxResults, maxPrice);
	}

	@Override
	public Book create(String sku) {

		// Antes de criar, verifica se já existe livro com o mesmo SKU
		if (repository.findBySku(sku) != null) {
			throw new IllegalArgumentException(
					String.format("Ja existe um livro identificado pelo SKU '%s'", sku));
		}
		// Obtém os dados usando a API da Saraiva e salva
		Book entity = saraivaService.findBySku(sku);
		if (entity == null) {
			throw new EntityNotFoundException(
					String.format("Dados nao encontrados para o SKU '%s'.", sku));
		}
		return repository.save(entity);
	}

	@Override
	public void delete(String sku) {
		Book entity = findBySku(sku);
		repository.delete(entity);
	}

}
