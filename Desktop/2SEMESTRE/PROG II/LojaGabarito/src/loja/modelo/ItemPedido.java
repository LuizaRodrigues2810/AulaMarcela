package loja.modelo;

public class ItemPedido {

    // ASSOCIAÇÃO: o item apenas REFERENCIA um produto que já existe na loja.
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Subtotal = preço final do produto x quantidade
    public double calcularSubtotal() {
        return produto.calcularPrecoFinal() * quantidade;
    }
}
