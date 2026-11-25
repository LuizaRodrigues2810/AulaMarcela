package loja.excecoes;

// Exceção VERIFICADA: herda de Exception.
// Quem chamar um metodo que lança esta exceção é OBRIGADO a tratar (try/catch) ou repassar (throws).
public class EstoqueInsuficienteException extends Exception {

    private final int quantidadeSolicitada;
    private final int quantidadeDisponivel;

    public EstoqueInsuficienteException(int quantidadeSolicitada, int quantidadeDisponivel) {
        // super(...) deve ser a PRIMEIRA linha. Ele guarda a mensagem para o getMessage() funcionar.
        super("Estoque insuficiente: solicitado " + quantidadeSolicitada
                + ", disponível " + quantidadeDisponivel);
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
}
