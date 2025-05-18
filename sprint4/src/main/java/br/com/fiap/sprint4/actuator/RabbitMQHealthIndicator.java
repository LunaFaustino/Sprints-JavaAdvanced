package br.com.fiap.sprint4.actuator;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQHealthIndicator implements HealthIndicator {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQHealthIndicator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Health health() {
        try {
            rabbitTemplate.getConnectionFactory().createConnection().close();
            return Health.up()
                    .withDetail("status", "Conexão com RabbitMQ estabelecida com sucesso")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("status", "Falha na conexão com RabbitMQ")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
