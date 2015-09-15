package cogito.online.application;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import cogito.online.messaging.AlertProcessingTopicListener;
import cogito.online.messaging.OrderProcessingMessageListener;
import cogito.online.messaging.SingleOrderProcessingMessageListener;

@Configuration
@EnableAutoConfiguration
@ComponentScan ("cogito.online")
@ImportResource("classpath:spring.xml")
public class MagicSuppliesConfiguration {
	
    @Bean
    ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }
    
    @Bean
    MessageListenerAdapter ordersMessageListener() throws Exception {
        return new MessageListenerAdapter(new OrderProcessingMessageListener()) {{
            setDefaultListenerMethod("onMessage");
        }};
    }

    @Bean
    SimpleMessageListenerContainer queueListenerContainer
    	(final MessageListenerAdapter ordersMessageListener, 
    			final ConnectionFactory connectionFactory) {
        return new SimpleMessageListenerContainer() {{
            setMessageListener(ordersMessageListener);
            setConnectionFactory(connectionFactory);
            setDestinationName("magic.orders");
        }};
    } 
    
    @Bean
    MessageListenerAdapter alertsMessageListener() {
        return new MessageListenerAdapter(new AlertProcessingTopicListener()) {{
            setDefaultListenerMethod("onMessage");
        }};
    }
    
    //normally topics warrent their own connection factory...
    @Bean
    SimpleMessageListenerContainer topicListenerContainer
    	(final MessageListenerAdapter alertsMessageListener, 
    			final ConnectionFactory connectionFactory) {
        return new SimpleMessageListenerContainer() {{
            setMessageListener(alertsMessageListener);
            setConnectionFactory(connectionFactory);
            setDestinationName("magic.alerts");
            setPubSubDomain(true);
        }};
    }  
    
    @Bean
    MessageListenerAdapter singleOrderMessageListener() throws Exception {
        return new MessageListenerAdapter(new SingleOrderProcessingMessageListener()) {{
            setDefaultListenerMethod("onMessage");
        }};
    }    

    @Bean
    SimpleMessageListenerContainer anotherQueueListenerContainer
    	(final MessageListenerAdapter singleOrderMessageListener, 
    			final ConnectionFactory connectionFactory) {
        return new SimpleMessageListenerContainer() {{
            setMessageListener(singleOrderMessageListener);
            setConnectionFactory(connectionFactory);
            setDestinationName("magic.order");
        }};
    }

}
