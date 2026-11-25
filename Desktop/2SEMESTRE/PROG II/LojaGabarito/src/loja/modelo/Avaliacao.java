package loja.modelo;

public class Avaliacao {

    private String cliente;
    private int nota;
    private String comentario;

    public Avaliacao(String cliente, int nota, String comentario) {
        validarCliente(cliente);
        validarNota(nota);

        this.cliente = cliente;
        this.nota = nota;
        this.comentario = comentario;
    }

    private void validarCliente(String cliente) {
        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("O cliente não pode ser nulo nem vazio.");
        }
    }

    private void validarNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota inválida! Informe um valor de 1 a 5.");
        }
    }

    public String getCliente() {
        return cliente;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente +
                "\nNota: " + nota +
                "\nComentário: " + comentario;
    }
}
