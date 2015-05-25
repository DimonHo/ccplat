package com.core.web.view.json.serializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.cc.core.utils.DateUtils;

/**
 * 日期格式序列化，配置spring多视图使用
 * @author Ron
 * @createTime 2014.08.30
 */
public class TimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.DATE_FORMAT_SS);
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
    }

    @Override
    public Class<Timestamp> handledType() {

        return Timestamp.class;
    }
}