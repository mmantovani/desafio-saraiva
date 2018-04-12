package br.com.saraiva.desafio.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;

/*
 * Classe para tratamento dos preços retornados pela API Saraiva,
 * que utilizam vírgula como separador de decimais.
 */
public class LocalizedDeserializationContext extends DefaultDeserializationContext {

    private final NumberFormat format;

    public LocalizedDeserializationContext(Locale locale) {
        // Passing `BeanDeserializerFactory.instance` because this is what happens at
        // 'jackson-databind-2.8.1-sources.jar!/com/fasterxml/jackson/databind/ObjectMapper.java:562'.
        this(BeanDeserializerFactory.instance, DecimalFormat.getNumberInstance(locale));
    }

    private LocalizedDeserializationContext(DeserializerFactory factory, NumberFormat format) {
        super(factory, null);
        this.format = format;
    }

    private LocalizedDeserializationContext(DefaultDeserializationContext src, DeserializationConfig config, JsonParser parser, InjectableValues values, NumberFormat format) {
        super(src, config, parser, values);
        this.format = format;
    }

    @Override
    public DefaultDeserializationContext with(DeserializerFactory factory) {
        return new LocalizedDeserializationContext(factory, format);
    }

    @Override
    public DefaultDeserializationContext createInstance(DeserializationConfig config, JsonParser parser, InjectableValues values) {
        return new LocalizedDeserializationContext(this, config, parser, values, format);
    }

    @Override
    public Object handleWeirdStringValue(Class<?> targetClass, String value, String msg, Object... msgArgs) throws IOException {
        // This method is called when default deserialization fails.
        if (targetClass == float.class || targetClass == Float.class) {
            return parseNumber(value).floatValue();
        }
        if (targetClass == double.class || targetClass == Double.class) {
            return parseNumber(value).doubleValue();
        }
        if (targetClass == BigDecimal.class) {
        	String str = parseNumber(value).toString();
        	return new BigDecimal(str);
        }
        return super.handleWeirdStringValue(targetClass, value, msg, msgArgs);
    }

    // Is synchronized because `NumberFormat` isn't thread-safe.
    private synchronized Number parseNumber(String value) throws IOException {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
