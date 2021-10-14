package ru.akrtkv.rir;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import ru.akrtkv.rir.dto.Message;
import ru.akrtkv.rir.utils.MessageDeserializationSchema;

import java.sql.Timestamp;

public class Main {

    /*
     * args example: --topic topicName --bootstrap.servers kafka:9093 --zookeeper.connect localhost:2181 --group.id vimis --dbUrl jdbc:postgresql://q4-demo2.medsoft.su:6432/vimis?prepareThreshold=0 --dbUsername vimis --dbPassword vimis --batchSize 5
     * */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        DataStream<Message> messageStream = environment.addSource(
                new FlinkKafkaConsumer<>(
                        parameterTool.getRequired("topic"),
                        new MessageDeserializationSchema(),
                        parameterTool.getProperties()
                )
        ).setBufferTimeout(60000);
        messageStream.rebalance().addSink(
                JdbcSink.sink(
                        "INSERT INTO requests (request_type, mis_id, message_id, mo_oid, vimis_type, doc_type, request_time) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        (statement, message) -> {
                            statement.setString(1, message.getRequestType());
                            statement.setString(2, message.getMisId());
                            statement.setString(3, message.getMessageId());
                            statement.setString(4, message.getMoOid());
                            statement.setInt(5, message.getVimisType());
                            statement.setInt(6, message.getDocType());
                            statement.setTimestamp(7, Timestamp.valueOf(message.getDateTime()));
                        },
                        JdbcExecutionOptions.builder()
                                .withBatchSize(Integer.parseInt(parameterTool.getRequired("batchSize")))
                                .withMaxRetries(9)
                                .build(),
                        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl(parameterTool.getRequired("dbUrl"))
                                .withDriverName("org.postgresql.Driver")
                                .withUsername(parameterTool.getRequired("dbUsername"))
                                .withPassword(parameterTool.getRequired("dbPassword"))
                                .build()
                )
        );
        environment.execute();
    }
}
