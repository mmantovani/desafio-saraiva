package br.com.saraiva.desafio.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.saraiva.desafio.dto.BookDTO;
import br.com.saraiva.desafio.model.Book;
import br.com.saraiva.desafio.service.BookService;

@RestController
@RequestMapping(BookController.URL)
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	public static final String URL = "/book";

	private final BookService service;
	private final ModelMapper mapper;

	public BookController(BookService service, ModelMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	/**
	 * Consulta de um livro pelo seu SKU.
	 *
	 * @param sku
	 * @return
	 */
	@RequestMapping(value="/{sku}", method = RequestMethod.GET)
	public ResponseEntity<BookDTO> get(@PathVariable String sku) {
		logger.debug("{}.get({})", this.getClass().getSimpleName(), sku);
		Book entity = service.findBySku(sku);
		return ResponseEntity.ok(mapper.map(entity, BookDTO.class));
	}


	/**
	 * Consulta todos os livros. Podem ser fornecidos os seguintes parâmetros opcionais:
	 * <p>limit: quantidade máxima de resultados
	 * <p>price: preço máximo dos livros
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BookDTO>> getAll(@RequestParam Map<String, String> params) {
		logger.debug("{}.getAll()", this.getClass().getSimpleName());
		String limit = params.get("limit");
		String price = params.get("price");
		Integer maxResults = StringUtils.isEmpty(limit) ? null : Integer.valueOf(limit);
		BigDecimal maxPrice = StringUtils.isEmpty(price) ? null : new BigDecimal(price);
		List<BookDTO> payload = service.findAll(maxResults, maxPrice).stream()
				.map(o -> mapper.map(o, BookDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(payload);
	}


	/**
	 * Cria/insere um novo livro na base de dados.
	 * De acordo com a especificação, o post enviará o SKU como um parâmetro de formulário.
	 * @param sku
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestParam("sku") String sku) {
		logger.debug("{}.create({})", this.getClass().getSimpleName(), sku);
		Book entity = service.create(sku);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{sku}")
				.buildAndExpand(entity.getSku()).toUri();
		return ResponseEntity.created(location).build();
	}


	/**
	 * Exclui um livro identificado pelo seu SKU.
	 *
	 * @param sku
	 * @return
	 */
	@RequestMapping(value="/{sku}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String sku) {
		logger.debug("{}.delete({})", this.getClass().getSimpleName(), sku);
		service.delete(sku);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
