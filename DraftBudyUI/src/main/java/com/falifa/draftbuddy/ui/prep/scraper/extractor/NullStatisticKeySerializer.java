package com.falifa.draftbuddy.ui.prep.scraper.extractor;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class NullStatisticKeySerializer extends StdSerializer<Object> {
	private static final long serialVersionUID = 1L;

	public NullStatisticKeySerializer() {
        this(null);
    }
 
    public NullStatisticKeySerializer(Class<Object> t) {
        super(t);
    }
     
    @Override
    public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName("ALL");
    }
}