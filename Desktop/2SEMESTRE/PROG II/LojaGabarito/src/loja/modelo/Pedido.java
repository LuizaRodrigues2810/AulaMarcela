package loja.modelo;

import loja.excecoes.EstoqueInsuficienteException;
import java.util.ArrayList;

public class Pedido {

    private String cliente;

    // COMPOSIÇÃO: os itens são criados DENTRO do pedido (no adicionarItem).
    private ArrayList<ItemPedido> itens = new ArrayList<>();

    public Pedido(String cliente) {
        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("O cliente não pode ser nulo nem vazio.");
        }
        this.cliente = cliente;
    }

    // Ordem importante: PRIMEIRO retira do estoque (pode lançar exceção).
    // Se lançar, as linhas de baixo NÃO executam e o pedido não é alterado.
    public void adicionarItem(Produto produto, int quantidade) throws EstoqueInsuficienteException {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        produto.retirarEstoque(quantidade);

        ItemPedido item = new ItemPedido(produto, quantidade);   // composição
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public int contarUnidades() {
        int unidades = 0;
        for (ItemPedido item : itens) {
            unidades += item.getQuantidade();
        }
        return unidades;
    }

    // Média = total / UNIDADES (e não total / número de itens!)
    public double calcularPrecoMedioPorUnidade() {
        if (itens.isEmpty()) {
            return 0;
        }
        return calcularTotal() / contarUnidades();
    }

    public String getCliente() {
        return cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }
}
