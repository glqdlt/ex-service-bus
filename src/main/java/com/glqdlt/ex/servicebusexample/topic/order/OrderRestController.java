package com.glqdlt.ex.servicebusexample.topic.order;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageBody;
import com.microsoft.azure.servicebus.TopicClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> postOrder(@RequestBody(required = false) OrderRequest orderRequest) throws ServiceBusException, InterruptedException {

        Message message
                = new Message();
        message.setReplyTo("SHIP");
        message.setMessageBody(MessageBody.fromBinaryData(Collections.singletonList("Hello".getBytes())));
        message.setContentType(MediaType.APPLICATION_JSON_VALUE);
        topicClient.send(message);
        return ResponseEntity.status(204).build();

    }

}
