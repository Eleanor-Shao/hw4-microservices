package com.sa.web;

import com.sa.web.dto.SentenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class SentimentController {

    @Autowired
    private KafkaTemplate<String, SentenceDto> kafkaTemplate;

    private static final String TOPIC = "webapp_to_logic";

    @PostMapping("/sentiment")
    public String sentimentAnalysis(@RequestBody SentenceDto sentenceDto) {
        kafkaTemplate.send(TOPIC, sentenceDto);  // Send the request to Kafka
        return "Sentiment request sent to Kafka for processing: " + sentenceDto.getSentence();
    }

    @GetMapping("/testHealth")
    public void testHealth() {
    }
}


