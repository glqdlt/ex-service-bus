package com.glqdlt.ex.servicebusexample.queue;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageBody;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author glqdlt
 */
@RestController
@RequestMapping("/submit")
public class SimpleMessageSubmitController {
    private QueueClient receiveClient;

    @Autowired
    public void init(@Value("${recice.con}") String connectionString) {
        try {
            receiveClient = new QueueClient(new ConnectionStringBuilder(connectionString, "basictopic2"), ReceiveMode.PEEKLOCK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping()
    public void getSubmitMessage(String message) {
        Message m
                = new Message();
        m.setContentType("application/json");
        m.setLabel("label");
        List<byte[]> binaryData = new ArrayList<>();
        binaryData.add(message.getBytes());
        MessageBody payload = MessageBody.fromBinaryData(binaryData);
        m.setMessageBody(payload);
        try {
            receiveClient.send(m);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
