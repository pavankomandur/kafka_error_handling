package com.kafkaerror.controller;

import com.kafkaerror.dto.User;
import com.kafkaerror.publisher.KafkaMessagePublisher;
import com.kafkaerror.util.ReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;


    @PostMapping("/publishUser")
    public ResponseEntity<?> publishEventforUser(@RequestBody User user) {
        try {
            publisher.sendEvents(user);
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception exception) {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/publishUsers")
    public ResponseEntity<?> publishUsers() {
        try {
            List<User> users = ReaderUtils.readDataFromCsv();
            users.forEach(usr -> publisher.sendEvents(usr));
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception exception) {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
