package br.com.saraiva.desafio.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Formata campos JSON monetários como "34.90" ao invés de "34.9".
 *
 * @author marco
 *
 */
public class BigDecimalMoneySerializer extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
	    nf.setGroupingUsed(false);
	    nf.setMinimumFractionDigits(2);
	    gen.writeString(nf.format(value));
	}

}
