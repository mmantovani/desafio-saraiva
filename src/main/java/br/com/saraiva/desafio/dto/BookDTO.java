package br.com.saraiva.desafio.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.saraiva.desafio.model.Book;

/**
 * Dados de um {@link Book} trafegados nas chamadas à API REST.
 *
 * @author marco
 *
 */
public class BookDTO {

	@NotNull
	@NotEmpty
	private String sku;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String brand;

	@NotNull
	private BigDecimal price;


	// getters & setters

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@JsonSerialize(using = BigDecimalMoneySerializer.class)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
