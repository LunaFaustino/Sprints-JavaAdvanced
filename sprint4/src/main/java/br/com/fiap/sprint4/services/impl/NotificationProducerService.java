package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.config.RabbitMQConfig;
import br.com.fiap.sprint4.messages.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarNotificacaoClinica(String id, String acao, String detalhes) {
        EventMessage message = new EventMessage(id, "CLINICA", acao, detalhes);
        logger.info("Enviando mensagem para fila de clínicas: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_CLINICA,
                message);
    }

    public void enviarNotificacaoDentista(String id, String acao, String detalhes) {
        EventMessage message = new EventMessage(id, "DENTISTA", acao, detalhes);
        logger.info("Enviando mensagem para fila de dentistas: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_DENTISTA,
                message);
    }

    public void enviarNotificacaoPaciente(String id, String acao, String detalhes) {
        EventMessage message = new EventMessage(id, "PACIENTE", acao, detalhes);
        logger.info("Enviando mensagem para fila de pacientes: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_PACIENTE,
                message);
    }
}
