package com.nrapendra.bank.message;

import java.net.URI;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ResponseEntity<Void> putMessage(@RequestBody MessageRequest newMessageRequest, UriComponentsBuilder ucb) {
        // TODO: Modify this to prevent users creating messages for other users
        Message newMessage = Message.builder()
                .messageId(generateMessageId())
                .name(newMessageRequest.name())
                .email(newMessageRequest.email())
                .subject(newMessageRequest.subject())
                .message(newMessageRequest.message())
                .build();
        if (newMessage == null) {
            return ResponseEntity.badRequest().build();
        }

        Message savedMessage = messageRepository.save(newMessage);
        URI savedMessageLocation = ucb
                .path("/api/v1/messages/{id}")
                .buildAndExpand(savedMessage.getMessageId())
                .toUri();
        return ResponseEntity.created(savedMessageLocation).build();
    }

    private String generateMessageId() {
        Random random = new Random();
        int randomInteger = random.nextInt(999999999 - 9999) + 9999;
        return "MN" + randomInteger;
    }
}
