package dm.dev.primetest.service;

import dm.dev.primetest.config.KafkaConsumerConfig;
import dm.dev.primetest.util.PrimeUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dm.dev.primetest.config.KafkaConsumerConfig.PRIME_CONSUMERS;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberConsumerService {

    private final String RANDOM_NUMBERS_TOPIC = "random-numbers";
    @Getter
    private final List<Integer> primeList = new ArrayList<>();

    @KafkaListener(topics = RANDOM_NUMBERS_TOPIC, groupId = PRIME_CONSUMERS)
    public void consume(Integer number) {
        if (PrimeUtil.isPrime(number)) {
            primeList.add(number);
            log.info("Added prime {}", number);
        }
    }

}