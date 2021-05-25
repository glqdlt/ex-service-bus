package com.glqdlt.ex.servicebusexample.topic;

import com.microsoft.azure.servicebus.TopicClient;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author glqdlt
 */
@Configuration
public class TopicConfig {

    @Bean
    public TopicClient topicClientInput(@Value("${topic.con.input}") String connectionString) throws ServiceBusException, InterruptedException {
        return new TopicClient(new ConnectionStringBuilder(connectionString));
    }


}
