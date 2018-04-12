package br.com.saraiva.desafio.service;

import java.util.Collections;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.saraiva.desafio.dto.LocalizedDeserializationContext;
import br.com.saraiva.desafio.dto.SaraivaBookDTO;
import br.com.saraiva.desafio.model.Book;

@Service
public class SaraivaServiceImpl implements SaraivaService {

	private final ModelMapper mapper;
	private final RestTemplate restTemplate;

	public SaraivaServiceImpl(ModelMapper mapper) {

		this.mapper = mapper;

		// A API da Saraiva retorna os preços usando vírgula, então é preciso fazer o tratamento do locale
		Locale locale = Locale.forLanguageTag("pt-BR");
		ObjectMapper objectMapper = new ObjectMapper(null, null, new LocalizedDeserializationContext(locale));
		restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
	}


	@Override
	public Book findBySku(String sku) {

		// Tenta obter o livro. Se não encontrar, retorna null.
		SaraivaBookDTO dto = restTemplate.getForObject(formatUrl(sku), SaraivaBookDTO.class);
		if (dto.getSku() == null) {
			return null;
		}

		// Converte os objetos e retorna o livro
		Book book = mapper.map(dto, Book.class);
		return book;
	}


	public String formatUrl(String sku) {
		return String.format("https://api.saraiva.com.br/sc/produto/pdp/%s/0/0/1/", sku);
	}
}
