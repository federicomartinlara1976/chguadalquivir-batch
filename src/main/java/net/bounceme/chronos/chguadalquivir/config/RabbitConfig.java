package net.bounceme.chronos.chguadalquivir.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	@Value("${application.queue}")
	private String queueName;
	
	@Value("${application.topic}")
	private String topicName;
	
	@Value("${application.notification.queue}")
	private String queueNotificationName;
	
	@Bean(name = "queue")
    public Queue queue() {
        return new Queue(queueName, true);
    }
	
	@Bean(name = "queueNotifications")
    public Queue queueNotifications() {
        return new Queue(queueNotificationName, true);
    }
	
	@Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicName);
    }

    @Bean(name = "binding")
    public Binding binding(
    		@Autowired
    		@Qualifier("queue") 
    		Queue queue, 
    		TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    
    @Bean(name = "bindingNotifications")
    public Binding bindingNotifications(
    		@Autowired
    		@Qualifier("queueNotifications") 
    		Queue queue, 
    		TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueNotificationName);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
