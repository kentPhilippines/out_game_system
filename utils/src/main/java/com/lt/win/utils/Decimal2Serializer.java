package com.lt.win.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 格式化
 *
 * @author fangzs
 * @date 2022/10/18 09:49
 */
public class Decimal2Serializer extends JsonSerializer<Object> {

    /**
     * 将返回的BigDecimal保留两位小数，再返回给前端
     *
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
            jsonGenerator.writeString(bigDecimal.toString());
        }
    }
}
