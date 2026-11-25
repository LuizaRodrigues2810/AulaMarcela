package loja.excecoes;

// Exceção VERIFICADA: herda de Exception.
public class ProdutoNaoEncontradoException extends Exception {

    private final String codigo;

    public ProdutoNaoEncontradoException(String codigo) {
        super("Produto não encontrado: " + codigo);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
