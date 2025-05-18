package br.com.fiap.sprint4.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_CLINICA = "clinica_queue";
    public static final String QUEUE_DENTISTA = "dentista_queue";
    public static final String QUEUE_PACIENTE = "paciente_queue";

    public static final String EXCHANGE_NAME = "odontoprev_exchange";

    public static final String ROUTING_KEY_CLINICA = "clinica.created";
    public static final String ROUTING_KEY_DENTISTA = "dentista.created";
    public static final String ROUTING_KEY_PACIENTE = "paciente.created";

    @Bean
    public Queue clinicaQueue() {
        return new Queue(QUEUE_CLINICA, true);
    }

    @Bean
    public Queue dentistaQueue() {
        return new Queue(QUEUE_DENTISTA, true);
    }

    @Bean
    public Queue pacienteQueue() {
        return new Queue(QUEUE_PACIENTE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding clinicaBinding(Queue clinicaQueue, TopicExchange exchange) {
        return BindingBuilder.bind(clinicaQueue).to(exchange).with(ROUTING_KEY_CLINICA);
    }

    @Bean
    public Binding dentistaBinding(Queue dentistaQueue, TopicExchange exchange) {
        return BindingBuilder.bind(dentistaQueue).to(exchange).with(ROUTING_KEY_DENTISTA);
    }

    @Bean
    public Binding pacienteBinding(Queue pacienteQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pacienteQueue).to(exchange).with(ROUTING_KEY_PACIENTE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
