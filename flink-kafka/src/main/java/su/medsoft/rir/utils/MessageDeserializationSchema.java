package ru.akrtkv.rir.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import ru.akrtkv.rir.dto.Message;

import java.io.IOException;

public class MessageDeserializationSchema implements DeserializationSchema<Message> {

    private static final JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    @Override
    public Message deserialize(byte[] bytes) throws IOException {
        return jsonMapper.readValue(bytes, Message.class);
    }

    @Override
    public boolean isEndOfStream(Message inputMessage) {
        return false;
    }

    @Override
    public TypeInformation<Message> getProducedType() {
        return TypeInformation.of(Message.class);
    }
}
