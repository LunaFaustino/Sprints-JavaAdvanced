package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.models.PerguntaResposta;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistenteIAService {

    private final AzureOpenAiChatModel chatModel;

    private static final String TEMPLATE_SISTEMA = """
            Você é um assistente virtual para uma clínica odontológica chamada OdontoPrev.
            Você deve responder perguntas dos pacientes de forma clara, concisa e amigável.
            
            Informações sobre a clínica:
            - Horário de funcionamento: Segunda a Sexta, das 8h às 18h
            - Número para contato: (11) 5555-1234
            - Especialidades: Ortodontia, Implantodontia, Endodontia, Odontopediatria, Prótese Dentária
            - Convênios: Amil, Bradesco Saúde, SulAmérica, Unimed
            
            Responda apenas sobre assuntos relacionados a serviços odontológicos, procedimentos dentários,
            cuidados bucais, e informações da clínica.
            
            Se o paciente perguntar sobre algo que você não tem certeza, sugira que ele entre em contato
            com a clínica diretamente.
            """;

    @Autowired
    public AssistenteIAService(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public PerguntaResposta obterResposta(String pergunta) {
        try {
            SystemMessage systemMessage = new SystemMessage(TEMPLATE_SISTEMA);
            UserMessage userMessage = new UserMessage(pergunta);

            Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

            String fullResponse = chatModel.call(prompt).toString();

            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("textContent=([^,]+)");
            java.util.regex.Matcher matcher = pattern.matcher(fullResponse);

            String resposta;
            if (matcher.find()) {
                resposta = matcher.group(1);
                resposta = resposta.replaceAll("^[\"']|[\"']$", "");
            } else {
                resposta = "Não foi possível processar sua pergunta.";
            }

            return new PerguntaResposta(pergunta, resposta);
        } catch (Exception e) {
            e.printStackTrace();
            return new PerguntaResposta(pergunta,
                    "Desculpe, estou enfrentando dificuldades técnicas no momento. Por favor, entre em contato com a clínica pelo telefone (11) 5555-1234.");
        }
    }
}