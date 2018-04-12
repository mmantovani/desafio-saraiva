package br.com.saraiva.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saraiva.desafio.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

	Book findBySku(String sku);
}
