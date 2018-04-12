package br.com.saraiva.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objeto de transferência para a consulta à API da Saraiva.
 * Somente estão mapeadas as propriedades usadas pela aplicação.
 *
 * @author marco
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaraivaBookDTO {

	private String sku;
	private String name;
	private String brand;
	private SaraivaBookPriceDTO price;

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
	public SaraivaBookPriceDTO getPrice() {
		return price;
	}
	public void setPrice(SaraivaBookPriceDTO price) {
		this.price = price;
	}
}
