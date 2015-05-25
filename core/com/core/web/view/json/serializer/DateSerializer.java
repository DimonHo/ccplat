package com.core.web.view.json.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.cc.core.utils.DateUtils;

/**
 * 对象序列化，配置spring多视图使用
 * @author Ron
 * @createTime 2014.08.30
 */
public class DateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.DATE_FORMAT_DD);
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
    }

    @Override
    public Class<Date> handledType() {

        return Date.class;
    }

}