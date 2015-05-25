package com.core.web.view.json.serializer;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * JSON序列化，配置spring多视图使用
 * @author Ron
 * @createTime 2014.08.30
 */
public class ThrowableSerializer extends JsonSerializer<Throwable> {
    private static final Log LOG = LogFactory.getLog(ThrowableSerializer.class);

    @Override
    public void serialize(Throwable e, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        LOG.error(e.getMessage(), e);
        jgen.writeString(e.getMessage());
    }

    @Override
    public Class<Throwable> handledType() {

        return Throwable.class;
    }
}