package dm.dev.primetest.service;

import dm.dev.primetest.dto.GeneratedStatusDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberService {

    private static final String TOPIC_NAME = "random-numbers";
    private final KafkaTemplate<String, Integer> kafkaTemplate;

    private static final int GENERATE_FREQUENCY = 1000;
    private static final int GENERATE_LIMIT = 100;
    private static final int GENERATE_RATE_PER_SECOND = 5;
    private int generatedCount = 0;
    @Getter
    private List<Integer> generatedInts = new ArrayList<>(GENERATE_LIMIT);    // only for csv

    private final Random random = new Random();

    @Scheduled(fixedRate = GENERATE_FREQUENCY)
    public void generateNumbers() {
        int generatePerSec = random.nextInt(GENERATE_RATE_PER_SECOND) + 1;

        for (int i = 0; i < generatePerSec && generatedCount < GENERATE_LIMIT; i++) {
            int randomInt = random.nextInt(Integer.MAX_VALUE) + 1; // positive num
            kafkaTemplate.send(TOPIC_NAME, randomInt);
            generatedInts.add(randomInt);
            generatedCount++;
        }
    }

    public GeneratedStatusDto getStatus() {
        return new GeneratedStatusDto(generatedCount, GENERATE_LIMIT);
    }

}