package loja.modelo;

import loja.excecoes.ProdutoNaoEncontradoException;
import java.util.ArrayList;

public class Loja {

    private String nome;

    // AGREGAÇÃO: os produtos são criados FORA da loja e ela só guarda a referência.
    // Se a loja fechar, os produtos continuam existindo.
    private ArrayList<Produto> produtos = new ArrayList<>();

    public Loja(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void cadastrarProduto(Produto p) {
        if (p == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        for (Produto existente : produtos) {
            if (existente.getCodigo().equals(p.getCodigo())) {
                throw new IllegalArgumentException("Já existe um produto com o código " + p.getCodigo());
            }
        }
        produtos.add(p);   // agregação: guarda o produto que veio de fora
    }

    // O throw fica DEPOIS do for: só chega aqui se nenhum produto bateu.
    public Produto buscarPorCodigo(String codigo) throws ProdutoNaoEncontradoException {
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        throw new ProdutoNaoEncontradoException(codigo);
    }

    // Média dos preços finais de todos os produtos
    public double calcularPrecoMedioCatalogo() {
        if (produtos.isEmpty()) {
            return 0;
        }
        double soma = 0;
        for (Produto p : produtos) {
            soma += p.calcularPrecoFinal();   // POLIMORFISMO
        }
        return soma / produtos.size();
    }

    // Média GERAL: soma de TODAS as notas de TODOS os produtos / total de avaliações
    public double calcularMediaGeralAvaliacoes() {
        double somaNotas = 0;
        int totalAvaliacoes = 0;

        for (Produto p : produtos) {
            for (Avaliacao a : p.getAvaliacoes()) {
                somaNotas += a.getNota();
                totalAvaliacoes++;
            }
        }

        if (totalAvaliacoes == 0) {
            return 0;
        }
        return somaNotas / totalAvaliacoes;
    }

    public Produto buscarMelhorAvaliado() {
        if (produtos.isEmpty()) {
            return null;
        }
        Produto melhor = produtos.get(0);   // começa com o primeiro
        for (Produto p : produtos) {
            if (p.calcularMediaAvaliacoes() > melhor.calcularMediaAvaliacoes()) {
                melhor = p;
            }
        }
        return melhor;
    }

    public ArrayList<Produto> listarSemEstoque() {
        ArrayList<Produto> semEstoque = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getEstoque() == 0) {
                semEstoque.add(p);
            }
        }
        return semEstoque;
    }

    // Soma de (preço final x estoque) de cada produto
    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.calcularPrecoFinal() * p.getEstoque();
        }
        return total;
    }
}
