package com.fatheroctober.ppsim.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatheroctober.ppsim.common.model.CustomerMessage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class ConsumerFileLogService implements ILogService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerFileLogService.class);
    private final ObjectMapper mapper;
    private Path file;

    public ConsumerFileLogService(@Lazy Path logFile) {
        this.file = logFile;
        this.mapper = new ObjectMapper();
    }

    @SneakyThrows(JsonProcessingException.class)
    public void log(long transaction, CustomerMessage msg) {
        ObjectNode customerData = customerDataNode(transaction, msg);
        String consumedData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerData);
        logger.info("Consumed data: {}", consumedData);
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file, StandardOpenOption.WRITE,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {
            final ByteBuffer buffer = ByteBuffer.wrap(consumedData.getBytes());
            channel.write(buffer, 0);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file", e);
        }
    }

    private ObjectNode customerDataNode(long transactionId, CustomerMessage customerMessage) {
        ObjectNode customerMessageNode = mapper.createObjectNode();
        customerMessageNode.put("cardNumber", customerMessage.getPan());
        customerMessageNode.put("expirationDate", customerMessage.getExDate());
        customerMessageNode.put("cvc2", customerMessage.getCvc2());

        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.putPOJO(String.valueOf(transactionId), customerMessageNode);
        return transactionNode;
    }
}
