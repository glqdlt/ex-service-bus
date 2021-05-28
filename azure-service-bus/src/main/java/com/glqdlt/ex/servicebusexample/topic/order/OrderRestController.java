package com.glqdlt.ex.servicebusexample.topic.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glqdlt.ex.servicebusexample.topic.DefaultEvent;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageBody;
import com.microsoft.azure.servicebus.TopicClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author glqdlt
 */
@RestController
@RequestMapping("/order")
public class OrderRestController {

    @Autowired
    private TopicClient topicClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> postOrder(@RequestParam(required = false) String target) throws ServiceBusException, InterruptedException {

        DefaultEvent orderEntity = DefaultEvent.valueOf("user1", "user1 order");
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsString(orderEntity).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Message message
                = new Message();
        message.setReplyTo("ORDER");
        message.setLabel("1");
        message.setMessageBody(MessageBody.fromBinaryData(Collections.singletonList(bytes)));
        message.setContentType(MediaType.APPLICATION_JSON_VALUE);
        topicClient.send(message);
        return ResponseEntity.status(204).build();

    }

}
