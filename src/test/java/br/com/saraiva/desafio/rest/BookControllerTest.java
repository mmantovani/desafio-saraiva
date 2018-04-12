package br.com.saraiva.desafio.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.saraiva.desafio.DesafioSaraivaApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DesafioSaraivaApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value="/META-INF/dbtest/test-data.xml")
public class BookControllerTest {

	private final String url = BookController.URL;

	@Autowired
    private MockMvc mvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	/*
	 * Listagem sem filtros.
	 */
	@Test
	public void getAllShouldReturnOk() throws Exception {

		this.mvc.perform(get(url)
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isNotEmpty());
	}


	/*
	 * Consulta pelo SKU
	 */
	@Test
	public void getBySkuShouldReturnOk() throws Exception {

		this.mvc.perform(get(url + "/9731880")
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(4)))
				.andExpect(jsonPath("$.sku", equalTo("9731880")))
				.andExpect(jsonPath("$.name", equalTo("Origem")))
				.andExpect(jsonPath("$.brand", equalTo("Arqueiro")))
				.andExpect(jsonPath("$.price", equalTo("34.90")));
	}


	/*
	 * Deve retornar not found quando SKU não cadastrado for informado
	 */
	@Test
	public void getByInvalidSkuShouldReturnNotFound() throws Exception {

		this.mvc.perform(get(url + "/sku-invalido")
				.contentType(contentType))
				.andExpect(status().isNotFound());
	}

	/*
	 * Consulta filtrando pelo preço, deve retornar 2 livros
	 */
	@Test
	public void getByMaxPrice() throws Exception {

		this.mvc.perform(get(url + "/?price=30")
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}


	/*
	 * Consulta limitando a quantidade de itens retornados
	 */
	@Test
	public void getByMaxResults() throws Exception {

		this.mvc.perform(get(url + "/?limit=2")
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}


	/*
	 * Cadastra um novo livro.
	 */
	@Test
	public void postShouldReturnCreated() throws Exception {

		this.mvc.perform(post(url)
				.param("sku", "1574783")
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(redirectedUrl("http://localhost/book/1574783"))
				.andReturn();
	}


	/*
	 * Tentativa de cadastro duplicado
	 */
	@Test
	public void postDuplicateSkuShouldReturnBadRequest() throws Exception {

		this.mvc.perform(post(url)
				.param("sku", "9731880")
				.contentType(contentType))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Ja existe um livro identificado pelo SKU '9731880'"))
				.andReturn();
	}
}
