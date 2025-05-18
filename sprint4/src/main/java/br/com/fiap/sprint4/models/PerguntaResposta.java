package br.com.fiap.sprint4.models;

public class PerguntaResposta {

    private String pergunta;
    private String resposta;

    public PerguntaResposta() {
    }

    public PerguntaResposta(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
