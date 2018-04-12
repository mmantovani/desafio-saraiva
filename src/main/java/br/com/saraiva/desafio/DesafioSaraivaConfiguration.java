package br.com.saraiva.desafio;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.saraiva.desafio.dto.SaraivaBookDTOMap;

@Configuration
public class DesafioSaraivaConfiguration {

	/*
	 * Utilizado para trabalhar com objetos de transferência de dados (DTO),
	 * evitando assim expor entidades do modelo na camada REST.
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new SaraivaBookDTOMap());
		return modelMapper;
	}
}
