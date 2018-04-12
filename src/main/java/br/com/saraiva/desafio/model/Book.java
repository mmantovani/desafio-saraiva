package br.com.saraiva.desafio.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Código do livro. Campo chave, não pode ser atualizado. */
	@NotNull
	@Column(nullable = false, unique = true, updatable = false, length = 20)
	private String sku;

	@NotNull
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull
	@Column(nullable = false, length = 100)
	private String brand;

	@NotNull
	@Column(nullable = false)
	private BigDecimal price;


	// getters & setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
