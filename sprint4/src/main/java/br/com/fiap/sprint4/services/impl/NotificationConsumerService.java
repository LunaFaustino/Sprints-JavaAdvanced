package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.config.RabbitMQConfig;
import br.com.fiap.sprint4.messages.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class NotificationConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumerService.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CLINICA)
    public void receberNotificacaoClinica(EventMessage message) {
        logger.info("Recebida mensagem da fila de clínicas: {}", message);
        processarMensagem(message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DENTISTA)
    public void receberNotificacaoDentista(EventMessage message) {
        logger.info("Recebida mensagem da fila de dentistas: {}", message);
        processarMensagem(message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PACIENTE)
    public void receberNotificacaoPaciente(EventMessage message) {
        logger.info("Recebida mensagem da fila de pacientes: {}", message);
        processarMensagem(message);
    }

    private void processarMensagem(EventMessage message) {
        logger.info("Processando mensagem: Tipo={}, ID={}, Ação={}, Detalhes={}",
                message.getTipo(), message.getId(), message.getAcao(), message.getDetalhes());
    }
}
