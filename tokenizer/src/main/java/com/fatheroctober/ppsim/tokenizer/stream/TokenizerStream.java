package com.fatheroctober.ppsim.tokenizer.stream;

import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Token;
import com.fatheroctober.ppsim.common.model.Transaction;
import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;
import com.fatheroctober.ppsim.common.persistence.operation.PushKeyToStorage;
import com.fatheroctober.ppsim.tokenizer.ITokenizerService;
import com.fatheroctober.ppsim.tokenizer.TokenizerRunner;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TokenizerStream implements Lifecycle {
    private static final Logger logger = LoggerFactory.getLogger(TokenizerRunner.class);

    @Autowired
    private ITokenizerService tokenizer;

    @Autowired
    private PushKeyToStorage storeKeyOperation;

    @Autowired
    @Qualifier("tokenizer-inputTopic")
    private String inputTopic;

    @Autowired
    @Qualifier("tokenizer-outputTopic")
    private String outputTopic;

    @Autowired
    private StreamsConfig streamsConfig;

    private KafkaStreams streams;


    private Topology topology() {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<Long, String> customerLogRecord = builder.stream(inputTopic);

        customerLogRecord.map((transId, json) -> {
            Pair<KeyInfo, Token> tokenizationBlock = tokenizer.generateToken(json);

            KeyInfo keyInfo = tokenizationBlock.getKey();
            Transaction targetTransaction = new Transaction(transId);
            storeKeyOperation.push(new TransactionKeyRecord(keyInfo, targetTransaction));

            return KeyValue.pair(transId, tokenizationBlock.getValue());
        }).to(outputTopic);

        return builder.build();
    }

    @Override
    public void start() {
        streams = new KafkaStreams(topology(), streamsConfig);
        streams.cleanUp();
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    @Override
    @SneakyThrows(InterruptedException.class)
    public void stop() {
        logger.info("Stop signal was taken, waiting for stopping tokenization stream...");
        streams.close();
        logger.info("Tokenization stream was stopped");
    }

}
