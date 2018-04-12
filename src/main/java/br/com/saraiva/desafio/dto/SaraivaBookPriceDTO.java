package br.com.saraiva.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaraivaBookPriceDTO {

	SaraivaBookPriceBestPriceDTO bestPrice;

	public SaraivaBookPriceBestPriceDTO getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(SaraivaBookPriceBestPriceDTO bestPrice) {
		this.bestPrice = bestPrice;
	}
}
